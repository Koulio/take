/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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