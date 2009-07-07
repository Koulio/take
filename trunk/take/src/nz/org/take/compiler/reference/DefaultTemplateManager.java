/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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

public class DefaultTemplateManager extends TemplateManager{
	
	public static final String TEMPLATEPATH = "nz/org/take/compiler/reference/";
	public static VelocityEngine VE = new VelocityEngine();
	
	static {
		// template loading
		VE.setProperty("resource.loader","class");
		VE.setProperty("class.resource.loader.description","Velocity Classpath Resource Loader");
		VE.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// logging		
		VE.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.SimpleLog4JLogSystem");

		try {
			VE.init();
		} catch (Exception e) {
			Logger.getLogger(DefaultTemplateManager.class).error("Error initialising velocity");
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
	public Template getTemplate(String name) throws CompilerException {
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
	public void reset() {
		synchronized(cache) {
			cache.clear();
		}
	}

}
