/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.script;

/**
 * Annotations.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Annotation extends ScriptElement {

	private boolean isGlobal = false;
	private String key = null;
	private String value = null;
	public boolean isGlobal() {
		return isGlobal;
	}
	public void addToKey(String token) {
		if (key==null)
			key=token;
		else
			key = key+':'+token;
	}
	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}
	public void setAnnotation(String annotation) {
		int sep = annotation.indexOf('=');
		if (sep==-1)
			throw new IllegalArgumentException("Invalid annotation format, annotation must have the format @key=value");
		this.key=annotation.substring(0,sep).trim();
		this.value=annotation.substring(sep+1).trim();
	}
	public String getValue() {
		return value;
	}
	public String getKey() {
		return key;
	}

	
}
