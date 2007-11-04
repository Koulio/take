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

import java.util.Iterator;
/**
 * Resource iterator.
 * This is an iterator that can be closed. The close operation releases resources, e.g.
 * closes database connections.
 * Note that close(), next() and hasNext() may throw unchecked exceptions DerivationException.
 * @see DerivationException
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public interface ResourceIterator<T> extends Iterator<T>{


	/**
	 * Close the iterator.
	 */
	public void close();



}
