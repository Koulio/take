/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.compiler.util;

import java.io.*;

import nz.org.take.compiler.CompilerException;
import nz.org.take.compiler.Location;

/**
 * Default location, assuming that src are in ./src.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
  */

public class DefaultLocation implements Location {
	
	private String srcFolder = "src";
	
	public DefaultLocation(String srcFolder) {
		super();
		this.srcFolder = srcFolder;
		checkFolders();
	}
	public DefaultLocation() {
		super();
		checkFolders();
	}
	
	
	/**
	 * Get the file location.
	 * @param c a fully qualified class name
	 * @param isClass whether this is for a class or a resource
	 * @return a path
	 */
	private String getLocation4Class(String c) {
		String p = c.replace('.','/');
		// Java compiler will create folders
		int idx = p.lastIndexOf('/');
		if (idx!=-1) {
			String folder = p.substring(0,idx);
			folder = srcFolder+"/" + folder;
			this.checkFolder(folder);
		}
		return srcFolder+"/"+p+".java";
	}
	/**
	 * Check whether folders exist. Try to create them if not.
	 */
	private void checkFolders() {
		checkFolder(this.srcFolder);
	}
	/**
	 * Check whether a folder exists. Try to create them if not.
	 */
	private void checkFolder(String folder) {
		File f = new File(folder);
		if (!f.exists()) 
			f.mkdirs();
		
	}

	/**
	 * Get a stream to write source code.
	 * @param c a class name
	 * @return a Writer using the specified class as destination
	 */
	public Writer getSrcOut(String c) throws CompilerException {
		try{
			return new FileWriter(getLocation4Class(c));
		}
		catch (IOException x) {
			throw new CompilerException(x);
		}	
	}

	/**
	 * Get the source code file. Some tools (Jalopy) require direct access to the file. 
	 * @param c a class name
	 * @return a file
	 */
	public File getSrcFile(String c) throws CompilerException {
		return new File(getLocation4Class(c));		
	}


	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
		this.checkFolder(srcFolder);
	}

	public OutputStream getResourceOut(String p,String r) throws CompilerException {
		
		String f = this.srcFolder+'/'+p.replace('.','/')+'/'+r;
		try{
			return new FileOutputStream(f);
		}
		catch (IOException x) {
			throw new CompilerException(x);
		}
	}

}
