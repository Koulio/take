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
 * Empty iterator. Uses the singleton pattern.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <T> the type of the iterated element
 */

public class EmptyIterator extends AbstractIterator {

	/**
	 * Return the default instance.
	 * @return the instance
	 */
	public static ResourceIterator DEFAULT = new EmptyIterator() ; 
	
	private EmptyIterator() {
		super();
	}

	public boolean hasNext() {
		return false;
	}

	public Object next() {
		throw new RuntimeException("this is an empty iterator");
	}

	public void close() {
		// nothing to do here
	}



}
