/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.compiler.util.jalopy;

import org.apache.log4j.Logger;
import nz.org.take.compiler.*;
import de.hunsicker.jalopy.Jalopy;

/**
 * Code pretty printer.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class JalopyCodeFormatter implements SourceTransformation {

	public Logger LOGGER = Logger.getLogger(JalopyCodeFormatter.class);
	
	public JalopyCodeFormatter() {
		super();
	}
	/**
	 * Transform the source code for the class.
	 * @param loc the location
	 * @param clazz the class name
	 * @throws CompilerException
	 */
	public void transform(Location loc,String clazz) throws CompilerException  {
		Jalopy formatter = new Jalopy();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Format src code for " + clazz + " using Jalopy");
		}
		try {
			formatter.setInput(loc.getSrcFile(clazz));
			formatter.setOutput(loc.getSrcFile(clazz));
			formatter.format();
		}
		catch (Exception x) {
			throw new CompilerException("Exception formating code with Jalopy",x);
		}
		
	}

}
