/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.rt;


/**
 * Abstract iterator. remove is not supported by subclasses.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public abstract class AbstractIterator<T> implements ResourceIterator<T>{

	
	/**
	 * Remove the current element.
	 * Not supported.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
