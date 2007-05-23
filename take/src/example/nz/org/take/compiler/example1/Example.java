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


package example.nz.org.take.compiler.example1;

import java.io.FileReader;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.log4j.BasicConfigurator;

import example.nz.org.take.compiler.example1.spec.FamilyKnowledge;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.Location;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.script.KnowledgeBaseReader;


/**
 * Script that generates the kb, compiles, loads and queries it.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Example {
		
	/**
	 * Generate the sources for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		
		// STEP 0: prepare
		FamilyKnowledge KB = null;
		
		String source = "src/example/nz/org/take/compiler/example1/family.take";
		String packageName = "example.nz.org.take.compiler.example1.impl";
		String className = "KB";
		File tmp = new File("tmp");
		
		BasicConfigurator.configure();
		Location location = new DefaultLocation("tmp");
		NameGenerator nameGenerator = new DefaultNameGenerator();
		
		if (!tmp.exists())
			tmp.mkdir();
		
		
		// STEP 1: generate sources into tmp
		KnowledgeBaseReader reader = new KnowledgeBaseReader();
		nz.org.take.compiler.Compiler kbCompiler = new DefaultCompiler();
		KnowledgeBase kb = reader.read(new FileReader(source));
		kbCompiler.setLocation(location);
		kbCompiler.setGenerateDataClassesForQueryPredicates(false); // part of interface !
		kbCompiler.setPackageName(packageName);
		kbCompiler.setClassName(className);
		kbCompiler.setInterfaceNames(FamilyKnowledge.class.getName());
		kbCompiler.setImportStatements("example.nz.org.take.compiler.example1.spec.*"); // the interface
		kbCompiler.compile(kb);
		
		// STEP 2: compile
		File[] files = new File("tmp/example/nz/org/take/compiler/example1/impl/").listFiles();
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
	    
	    
	    
	    File classLocation = new File("tmp");
	    URLClassLoader classloader = new URLClassLoader(new URL[]{classLocation.toURL()});
	    Class clazz = classloader.loadClass("example.nz.org.take.compiler.example1.impl.KB");
	    
	    KB = (FamilyKnowledge)clazz.newInstance();
	    // KB.getGrandfather(new Person("Max"));
	    System.out.println("it worked !");
	    

	}
}
