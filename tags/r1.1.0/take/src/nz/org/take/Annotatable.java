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
package nz.org.take;

import java.util.Map;

/**
 * Interface for classes that can be annotated.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface Annotatable {

	/**
	 * Add a new annotation.
	 * @param key
	 * @param value
	 */
	public abstract void addAnnotation(String key, String value);

	/**
	 * Removes an annotation.
	 * @param key
	 * @return the value of the removed annotation or null if there is no such annotation
	 */
	public abstract String removeAnnotation(String key);
	
	/**
	 * Get the annotation value for a given key.
	 * @param key the key
	 * @return the value or null if there is no such annotation
	 */
	public abstract String getAnnotation(String key);

	/**
	 * Add all Annotations.
	 * @param newAnnotations
	 */
	public void addAnnotations(Map<String,String> newAnnotations);

	public abstract Map<String, String> getAnnotations();

}