/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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
package nz.org.take.r2ml;

import nz.org.take.TakeException;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class R2MLException extends TakeException {
	
	/**
	 * 
	 */
	public static final byte GENERIC_ERROR = 00;
	
	/**
	 * 
	 */
	public static final byte FEATURE_NOT_SUPPORTED = 20;
	
	/**
	 * 
	 */
	public static final byte FEATURE_NOT_YET_IMPLEMENTED = 25;
	
	/**
	 * this error useally occurs when the wrong XmlTypeHandler is loaded.
	 */
	public static final byte CLASS_CAST_ERROR = 40;
	
	/**
	 * 
	 */
	public static final byte NO_TYPE_HANDLER_FOUND = 60;
	
	private int errorCode = GENERIC_ERROR;

	/**
	 * @param msg
	 * @param cause
	 * @param error
	 */
	public R2MLException(String msg, Exception cause, int error) {
		super(msg, cause);
		errorCode = error;
	}
	
	/**
	 * @param msg
	 * @param cause
	 */
	public R2MLException(String msg, Exception cause) {
		super(msg, cause);
	}

	/**
	 * @param msg
	 * @param error
	 */
	public R2MLException(String msg, byte error) {
		super(msg);
		errorCode = error;
	}

	/**
	 * @param msg
	 */
	public R2MLException(String msg) {
		super(msg);
	}

	/**
	 * @return
	 */
	public int getErrorCode() {
		return errorCode;
	}
}
