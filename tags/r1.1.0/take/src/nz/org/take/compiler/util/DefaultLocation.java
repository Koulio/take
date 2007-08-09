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
 * Default location, assuming that src are in ./src and compiled classes
 * are in ./bin.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
  */

public class DefaultLocation implements Location {
	
	private String srcFolder = "src";
	private String binFolder = "bin";
	
	public DefaultLocation(String srcFolder, String binFolder) {
		super();
		this.srcFolder = srcFolder;
		this.binFolder = binFolder;
		checkFolders();
	}
	public DefaultLocation(String outputFolder) {
		super();
		this.srcFolder = outputFolder;
		this.binFolder = outputFolder;
		checkFolders();
	}
	public DefaultLocation() {
		super();
		checkFolders();
	}
	
	
	/**
	 * Get the file location.
	 * @param c a fully qualified class name
	 * @param isSrc whether this is for a source or a compiled class file
	 * @return a path
	 */
	private String getLocation(String c,boolean isSrc) {
		String p = c.replace('.','/');
		if (isSrc) { // Java compiler will create folders
			int idx = p.lastIndexOf('/');
			if (idx!=-1) {
				String folder = p.substring(0,idx);
				folder = srcFolder+"/" + folder;
				this.checkFolder(folder);
			}
		}
		if (isSrc) 
			return srcFolder+"/"+p+".java";
		else 
			return binFolder+"/"+p+".class";
	}
	/**
	 * Check whether folders exist. Try to create them if not.
	 */
	private void checkFolders() {
		checkFolder(this.binFolder);
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
			return new FileWriter(getLocation(c,true));
		}
		catch (IOException x) {
			throw new CompilerException(x);
		}	
	}
	/**
	 * Get a stream to read source code.
	 * @param c a class name
	 * @return a reader
	 */
	public Reader getSrcIn(String c) throws CompilerException {
		try {
			return new FileReader(getLocation(c,true));
		}
		catch (IOException x) {
			throw new CompilerException(x);
		}	
	}
	/**
	 * Get a stream to write compiled code.
	 * @param c a class name
	 * @return an output stream 
	 */
	public OutputStream getBinOut(String c) throws CompilerException {
		try {
			return new FileOutputStream(getLocation(c,false));
		}
		catch (IOException x) {
			throw new CompilerException(x);
		}	
	}
	/**
	 * Get a stream to read compiled code.
	 * @param c a class name
	 * @return an input stream
	 */
	public InputStream getBinIn(String c) throws CompilerException {
		try{
			return new FileInputStream(getLocation(c,true));
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
		return new File(getLocation(c,true));		
	}

	public String getBinFolder() {
		return binFolder;
	}

	public void setBinFolder(String binFolder) {
		this.binFolder = binFolder;
		this.checkFolder(binFolder);
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
		this.checkFolder(srcFolder);
	}


}
