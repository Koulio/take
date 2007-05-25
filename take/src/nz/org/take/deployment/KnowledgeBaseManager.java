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
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.BasicConfigurator;

import example.nz.org.take.compiler.example1.spec.FamilyKnowledge;
import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.Location;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.script.KnowledgeBaseReader;


/**
 * Utility class used to instantiate the generated interface with an instance of the
 * generated class.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class KnowledgeBaseManager<I> {
	
	// the folder where files are stored
	private String workingDirRoot = "takeWorkingDir/";
	private String className = "KBImpl";
	private ClassLoader baseClassLoader = this.getClass().getClassLoader();

	public I getKnowledgeBase(Class spec,KnowledgeBase kb) throws Exception {
	
		assert(spec.isInterface());
		NameGenerator nameGenerator = new DefaultNameGenerator();
		checkFolder(workingDirRoot);
		String srcFolder = workingDirRoot+"src/";
		String binFolder = workingDirRoot+"src/";
		checkFolder(srcFolder);
		checkFolder(binFolder);
		
		Location location = new DefaultLocation(srcFolder,binFolder);
		Date now = new Date();
		
		String version = "impl_v"+now.getYear()+(now.getMonth()+1)+now.getDate()+"_"+now.getHours()+now.getMinutes()+now.getSeconds();
		
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
	    compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
	    
		// load class
		URL classLoc = new File(binFolder).toURL();
		String fullClassName = packageName+'.'+className;
		Class clazz = new URLClassLoader(new URL[]{classLoc}).loadClass(fullClassName);
		return (I)clazz.newInstance();
		
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
}
