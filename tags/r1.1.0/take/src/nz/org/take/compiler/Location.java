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

package nz.org.take.compiler;

import java.io.*;
/**
 * Interface describing how to access source code and compiled classes.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public interface Location {
	/**
	 * Get a stream to write source code.
	 * @param c a class name
	 * @return a Writer using the specified class as destination
	 * @throws CompilerException if the Writer couldn't be created because <code>c</code> references a directory or 
	 */
	public Writer getSrcOut(String c) throws CompilerException;
	/**
	 * Get a stream to read source code.
	 * @param c a class name
	 * @return a reader
	 */
	public Reader getSrcIn(String c) throws CompilerException;
	/**
	 * Get a stream to write compiled code.
	 * @param c a class name
	 * @return an output stream 
	 */
	public OutputStream getBinOut(String c) throws CompilerException;
	/**
	 * Get a stream to read compiled code.
	 * @param c a class name
	 * @return an input stream
	 */
	public InputStream getBinIn(String c) throws CompilerException;
	/**
	 * Get the source code file. Some tools (Jalopy) require direct access to the file. 
	 * @param c a class name
	 * @return a file
	 */
	public File getSrcFile(String c) throws CompilerException;
}
