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

import nz.org.take.compiler.CompilerException;
import org.apache.velocity.Template;

/**
 * Utility used to manage and share a velocity engine.
 * This is a singleton.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class TemplateManager {
	
	public static TemplateManager INSTANCE = new DefaultTemplateManager();
	
	public static TemplateManager getInstance() {
		return INSTANCE;
	}
	/**
	 * Install this template manager to be used.
	 */
	public void install() {
		INSTANCE=this;
	}
	
	/**
	 * Get a template by name.  
	 * @param name
	 * @return a template
	 * @throws CompilerException
	 */
	public abstract Template getTemplate(String name) throws CompilerException;
	/**
	 * Reset the cache, will reload all templates.
	 */
	public abstract void reset() ;

}
