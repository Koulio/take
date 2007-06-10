/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nz.org.take.deployment;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.SimpleBindings;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeSource;
import nz.org.take.TakeException;
import nz.org.take.compiler.Location;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;


/**
 * Utility class used to instantiate the generated interface with an instance of the
 * generated class.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class KnowledgeBaseManager<I> {
	
	// data format used in generated class names
	private DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
	// the folder where files are stored
	private String workingDirRoot = "takeWorkingDir/";
	// the name of the kb class
	private String className = "KBImpl";
	// whether to ccheck 
	private boolean checkBindingsForCompleteness = true;
	// the parent of the classloader that will be used to load 
	// generated classes
	private ClassLoader baseClassLoader = this.getClass().getClassLoader();

	/**
	 * Get an instance of the compiled knowledge base.
	 * @param spec the interface to be implemented by the compiled knowledge base
	 * @param ksource the knowledge source (a kb, a script, xml file or similar)
	 * @return an instance of the kb
	 * @throws TakeException
	 */
	
	public I getKnowledgeBase(Class spec,KnowledgeSource ksource) throws TakeException {
		return this.getKnowledgeBase(spec, ksource,new SimpleBindings(),new SimpleBindings());
	}
	
	/**
	 * Get an instance of the compiled knowledge base.
	 * @param spec the interface to be implemented by the compiled knowledge base
	 * @param ksource the knowledge source (a kb, a script, xml file or similar)
	 * @param constants bindings for the objects referenced in the kb
	 * @return an instance of the kb
	 * @throws TakeException
	 */
	public I getKnowledgeBase(Class spec,KnowledgeSource ksource,Bindings constants) throws TakeException {
		return this.getKnowledgeBase(spec, ksource,constants,new SimpleBindings());
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
	public I getKnowledgeBase(Class spec,KnowledgeSource ksource,Bindings constants,Bindings factStores) throws TakeException {
	
		KnowledgeBase kb = ksource.getKnowledgeBase();
		assert(spec.isInterface());
		NameGenerator nameGenerator = new DefaultNameGenerator();
		checkFolder(workingDirRoot);
		String srcFolder = workingDirRoot+"src/";
		String binFolder = workingDirRoot+"bin/";
		checkFolder(srcFolder);
		checkFolder(binFolder);
		
		Location location = new DefaultLocation(srcFolder,binFolder);
		String version = "impl_v"+dateFormat.format(new Date());
		
		String packageName = spec.getPackage().getName() +  '.'+version;
		nz.org.take.compiler.Compiler kbCompiler = new DefaultCompiler();
		kbCompiler.setLocation(location);
		kbCompiler.setGenerateDataClassesForQueryPredicates(false); // part of interface !
		kbCompiler.setPackageName(packageName);
		kbCompiler.setClassName(className);
		kbCompiler.setInterfaceNames(spec.getName());
		String interfacePackageName = spec.getPackage().getName();
		kbCompiler.setImportStatements(interfacePackageName+".*"); // the interface
		kbCompiler.compile(kb);
		
		// java compilation
		String javaFolder = srcFolder + packageName.replace('.','/');
		
		File[] files = new File(javaFolder).listFiles();
		List<File> sources = new ArrayList<File>();
		for (File f:files) {
			if (f.getAbsolutePath().endsWith(".java"))
				sources.add(f);
		}
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits1 =
        	fileManager.getJavaFileObjectsFromFiles(sources);
        String[] options = new String[]{"-d",binFolder};
	    compiler.getTask(null, fileManager, null, Arrays.asList(options), null, compilationUnits1).call();
	    

	    
	    
		// load class
	    String fullClassName = packageName+'.'+className;
	    try {
	    	URL classLoc = new File(binFolder).toURL();	  
	    	ClassLoader classloader = new URLClassLoader(new URL[]{classLoc});
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

	private void setupBindings(Bindings bindings, String constantClassName,ClassLoader classloader) throws TakeException{
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
}
