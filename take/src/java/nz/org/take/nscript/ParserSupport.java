/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.nscript;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


/**
 * Contains some utilities used by parsers, mainly for logging and exception handling. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */




public class ParserSupport {

	public Logger LOGGER = Logger.getLogger(Parser.class);

	protected void error(int no, Object... description) throws ScriptException {
		StringBuffer buf = new StringBuffer();
		buf.append("Parser exception at line ");
		buf.append(no);
		buf.append(' ');
		for (Object t:description)
			buf.append(t);
		error(no,buf.toString());
	}
	
	protected void error(int no, Exception x,Object... description) throws ScriptException {
		StringBuffer buf = new StringBuffer();
		buf.append("Parser exception at line ");
		buf.append(no);
		buf.append(' ');
		for (Object t:description)
			buf.append(t);
		error(no,buf.toString(),x);
	}
	

	protected void error(int no, String message) throws ScriptException {
		throw new ScriptException(message,no);
	}

	protected void error(int no, Exception x,String message) throws ScriptException {
		throw new ScriptException(message,x,no);
	}
	
	
	protected void debug(Object ...strings) {
		if (LOGGER.isDebugEnabled()) {
			StringBuffer b = new StringBuffer();
			for (Object s:strings)
				b.append(s);
			LOGGER.debug(b);
		}
	}
	
	protected void warn(Object ...strings) {
		if (LOGGER.isEnabledFor(Priority.WARN)) {
			StringBuffer b = new StringBuffer();
			for (Object s:strings)
				b.append(s);
			LOGGER.warn(b);
		}
	}

}
