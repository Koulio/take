/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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
