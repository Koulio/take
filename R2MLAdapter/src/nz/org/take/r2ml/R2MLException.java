/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
