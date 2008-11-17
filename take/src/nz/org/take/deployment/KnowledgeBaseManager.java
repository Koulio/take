/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.deployment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeSource;
import nz.org.take.TakeException;
import nz.org.take.compiler.Location;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.Logging;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.deployment.ant.ANTCompilerAdapter;


/**
 * Utility class used to instantiate the generated interface with an instance of the
 * generated class.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class KnowledgeBaseManager<I> implements Logging {
	
	// data format used in generated class names
	private DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
	// the folder where files are stored
	private String workingDirRoot = null;
	// the name of the kb class
	private String className = "KBImpl";
	// whether to check bindings 
	private boolean checkBindingsForCompleteness = true;
	// the parent of the classloader that will be used to load 
	// generated classes
	private ClassLoader baseClassLoader = this.getClass().getClassLoader();
	// the Java compiler is responsible for compiling the generated source code classes
	private CompilerAdapter javaCompiler = new ANTCompilerAdapter();
	// folders for src and bin
	private String srcFolder = null;
	private String binFolder = null;

	public KnowledgeBaseManager() {
		super();
		this.setWorkingDirRoot("takeWorkingDir/");
		
		// init compiler adapter
		String compilerAdapterName = this.getCompilerAdapterClassName();
		try {
			LOGGER.info("Trying to install compiler adapter " + compilerAdapterName);
			javaCompiler = (CompilerAdapter)Class.forName(compilerAdapterName).newInstance();
			LOGGER.debug("Installed Java compiler adapter " + compilerAdapterName);
		}
		catch (Exception x) {
			LOGGER.error("Error installing Java compiler adapter " + compilerAdapterName,x);
		}
	}

	protected String getCompilerAdapterClassName() {
		String javaVersion = System.getProperty("java.version");
		if (javaVersion.startsWith("1.5")) {
			return "nz.org.take.deployment.ant.ANTCompilerAdapter";
		}
		return "nz.org.take.deployment.jsr199.JSR199CompilerAdapter";
	}
	/**
	 * Get an instance of the compiled knowledge base.
	 * @param spec the interface to be implemented by the compiled knowledge base
	 * @param ksource the knowledge source (a kb, a script, xml file or similar)
	 * @return an instance of the kb
	 * @throws TakeException
	 */
	
	public I getKnowledgeBase(Class spec,KnowledgeSource ksource) throws TakeException {
		return this.getKnowledgeBase(spec, ksource,new HashMap<String,Object> (),new HashMap<String,Object> ());
	}
	
	/**
	 * Get an instance of the compiled knowledge base.
	 * @param spec the interface to be implemented by the compiled knowledge base
	 * @param ksource the knowledge source (a kb, a script, xml file or similar)
	 * @param constants bindings for the objects referenced in the kb
	 * @return an instance of the kb
	 * @throws TakeException
	 */
	public I getKnowledgeBase(Class spec,KnowledgeSource ksource,Map<String,Object> constants) throws TakeException {
		return this.getKnowledgeBase(spec, ksource,constants,new HashMap<String,Object> ());
	}
	

	/**
	 * Get an instance of the compiled knowledge base.
	 * @param spec the interface to be implemented by the compiled knowledge base
	 * @param ksource the knowledge source (a kb, a script, xml file or similar)
	 * @param constants bindings for the objects referenced in the kb
	 * @param factStores bindings for the fact stores referenced in the kb
	 * @return an instance of the kb
	 * @throws TakeException
	 */
	public I getKnowledgeBase(Class spec,KnowledgeSource ksource,Map<String,Object> constants, Map<String,Object> factStores) throws TakeException {
	
		KnowledgeBase kb = ksource.getKnowledgeBase();
		assert(spec.isInterface());
		checkFolder(workingDirRoot);
		checkFolder(srcFolder);
		checkFolder(binFolder);
		
		Location location = new DefaultLocation(srcFolder);
		String version = "impl_v"+dateFormat.format(new Date());
		
		String packageName = spec.getPackage().getName() +  '.'+version;
		nz.org.take.compiler.Compiler kbCompiler = new DefaultCompiler();
		kbCompiler.setLocation(location);
		kbCompiler.add(new JalopyCodeFormatter());
		kbCompiler.setGenerateDataClassesForQueryPredicates(false); // part of interface !
		kbCompiler.setPackageName(packageName);
		kbCompiler.setClassName(className);
		kbCompiler.setInterfaceNames(spec.getName());
		kbCompiler.setNameGenerator(new DefaultNameGenerator());
		String interfacePackageName = spec.getPackage().getName();
		kbCompiler.setImportStatements(interfacePackageName+".*"); // the interface
		kbCompiler.compile(kb);
		
	    javaCompiler.compile(packageName,srcFolder,binFolder);
	    
	    // copy resource from src to bin folder
	    copyResources(new File(srcFolder),binFolder);
	    
		// load class
	    String fullClassName = packageName+'.'+className;
	    try {
	    	URL classLoc = new File(binFolder).toURL();	  
	    	ClassLoader classloader = new URLClassLoader(new URL[]{classLoc},this.baseClassLoader);
	    	// handle bindings, i.e. bind names to objects that are referenced in rules as constant terms
		    String constantClassName = packageName+'.'+kbCompiler.getNameGenerator().getConstantRegistryClassName();
		    setupBindings(constants,constantClassName,classloader);	
		    String factStoreClassName = packageName+'.'+kbCompiler.getNameGenerator().getFactStoreRegistryClassName();
		    setupBindings(factStores,factStoreClassName,classloader);		    
	    	// load class
		    Class clazz = classloader.loadClass(fullClassName);
	    	return (I)clazz.newInstance();
	    }
	    catch (Exception x) {
	    	throw new DeploymentException ("Cannot load generated class "+fullClassName,x);
	    }
		
	}

	private void copyResources(File from,String context) {
		File[] files = from.listFiles();		
		if (files!=null) {
			FileInputStream in;
			for (File f:files) {
			    if (!f.getName().endsWith(".java")) {
			    	if (f.isDirectory()) {
			    		copyResources(f,context+'/'+f.getName());
			    	}
			    	else {
						try {
							String to = context+'/'+f.getName();
							in = new FileInputStream(f);
						    FileOutputStream out = new FileOutputStream(to);
						    byte[] buffer = new byte[4096];
						    int bytesRead;
						    while ((bytesRead = in.read(buffer)) != -1)
						    	out.write(buffer, 0, bytesRead);
						    out.close();
						    in.close();
						} 
						catch (IOException e) {
							// e.printStackTrace(); TODO logging
						}
			    	}
			    }
			}
		}
	}

	private void setupBindings(Map<String,Object> bindings, String constantClassName,ClassLoader classloader) throws TakeException{
		Class constantClass = null;
		if (bindings.size()>0) {			
			try {
				constantClass = classloader.loadClass(constantClassName);
			}
			catch (Exception x) {
				throw new DeploymentException("Cannot load generated constant class " + constantClassName,x);
			}
			for (Entry<String,Object> binding:bindings.entrySet()) {
				try {
					Field field = constantClass.getField(binding.getKey());
					field.set(null,binding.getValue()); // fields are static 
				} catch (Exception x) {
					throw new DeploymentException("Cannot set field " + binding.getKey() + " to value " + binding.getValue()+ " in generated constant class " + constantClassName,x);
				}
				
			}
		}
		
		if (this.checkBindingsForCompleteness && constantClass!=null) {
			for (Field field:constantClass.getFields()) {
				if (!bindings.containsKey(field.getName())) {
					throw new DeploymentException("There is no binding for field " + field.getName() + " in class " + constantClass);
				}
			}
		}

	}

	public String getWorkingDirRoot() {
		return workingDirRoot;
	}

	public void setWorkingDirRoot(String workingDirRoot) {
		this.workingDirRoot = workingDirRoot;
		this.setSrcFolder(workingDirRoot+"src/");
		this.setBinFolder(workingDirRoot+"bin/");
	}
	private void checkFolder(String folder) {
		File f = new File(folder);
		if (!f.exists())
			f.mkdir();
	}

	public boolean isCheckBindingsForCompleteness() {
		return checkBindingsForCompleteness;
	}

	public void setCheckBindingsForCompleteness(boolean checkBindingsForCompleteness) {
		this.checkBindingsForCompleteness = checkBindingsForCompleteness;
	}

	public ClassLoader getBaseClassLoader() {
		return baseClassLoader;
	}

	public void setBaseClassLoader(ClassLoader baseClassLoader) {
		this.baseClassLoader = baseClassLoader;
	}

	public CompilerAdapter getJavaCompiler() {
		return javaCompiler;
	}

	public void setJavaCompiler(CompilerAdapter javaCompiler) {
		this.javaCompiler = javaCompiler;
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

	public String getBinFolder() {
		return binFolder;
	}

	public void setBinFolder(String binFolder) {
		this.binFolder = binFolder;
	}
}
