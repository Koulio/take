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
