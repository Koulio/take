/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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


package nz.org.take.compiler.reference;

import java.util.HashMap;
import java.util.Map;
import nz.org.take.compiler.CompilerException;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

/**
 * Utility used to manage and share a velocity engine.
 * All template are loaded from the classpath.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class VelocitySupport {
	
	public static final String TEMPLATEPATH = "nz/org/take/compiler/reference/";
	public static VelocityEngine VE = new VelocityEngine();
	
	static {
		// template loading
		VE.setProperty("resource.loader","class");
		VE.setProperty("class.resource.loader.description","Velocity Classpath Resource Loader");
		VE.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// logging		
		VE.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		VE.setProperty("runtime.log.logsystem.log4j.category",CompilerPlugin4PropertyPredicates.class.getName());

		try {
			VE.init();
		} catch (Exception e) {
			Logger.getLogger(CompilerPlugin4Comparisons.class).error("Error initialising velocity");
		}
			
	}
	
	private static Map<String,Template> cache = new HashMap<String,Template>();
	/**
	 * Get a template by name. 
	 * The name is local, i.e., the template path will be added to it.
	 * Note that templates will be cached. To clear the cache, use reset. 
	 * @param name
	 * @return a template
	 * @throws CompilerException
	 */
	public static Template getTemplate(String name) throws CompilerException {
		synchronized(cache) {
			Template template = cache.get(name);
			if (template==null) {
				try {
					template = VE.getTemplate(TEMPLATEPATH+name);			
				}
				catch (Exception x) {
					throw new CompilerException("Cannot load compilation template " + TEMPLATEPATH + name);
				}	
			}
			return template;
		}
	}
	/**
	 * Reset the cache, will reload all templates.
	 */
	public static void reset() {
		synchronized(cache) {
			cache.clear();
		}
	}

}
