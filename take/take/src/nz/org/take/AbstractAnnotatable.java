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

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract superclass for classes that can be annotated.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class AbstractAnnotatable implements Annotatable  {
	Map<String,String> annotations = new HashMap<String,String>();

	public Map<String, String> getAnnotations() {
		return annotations;
	}
	public void addAnnotation(String key,String value) {
		this.annotations.put(key, value);
	}
	public void addAnnotations(Map<String,String> newAnnotations) {
		annotations.putAll(newAnnotations);
	}
	public String removeAnnotation(String key) {
		return this.annotations.remove(key);
	}
	public String getAnnotation(String key) {
		return this.annotations.get(key);
	}

}
