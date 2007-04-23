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
/**
 * Compiler exception.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class CompilerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5961995027233446277L;

	/**
	 * 
	 */
	public CompilerException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CompilerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CompilerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CompilerException(Throwable cause) {
		super(cause);
	}



}
