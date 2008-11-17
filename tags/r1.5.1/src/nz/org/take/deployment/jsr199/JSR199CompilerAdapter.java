/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.deployment.jsr199;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import nz.org.take.TakeException;
import nz.org.take.deployment.CompilerAdapter;


/**
 * Compiler adapter based on JSR199. This is the default adapter.
 * The main reason to have alternative adapters is JDK1.5 compatibility.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JSR199CompilerAdapter implements CompilerAdapter{

	@Override
	public void compile(String packageName,String srcFolder,String binFolder) throws TakeException {
		String javaFolder = srcFolder + packageName.replace('.','/');
		
		File[] files = new File(javaFolder).listFiles();
		List<File> sources = new ArrayList<File>();
		for (File f:files) {
			if (f.getAbsolutePath().endsWith(".java"))
				sources.add(f);
		}
		
		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits1 =
        	fileManager.getJavaFileObjectsFromFiles(sources);
        String[] options = new String[]{"-d",binFolder};
	    compiler.getTask(null, fileManager, null, Arrays.asList(options), null, compilationUnits1).call();
	    

	}

}
