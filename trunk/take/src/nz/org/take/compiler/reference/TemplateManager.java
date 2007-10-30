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
