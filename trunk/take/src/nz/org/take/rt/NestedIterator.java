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

import java.util.ArrayList;
import java.util.List;

/**
 * Nested iterator.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <O> the outer iterator type
 * @param <I> the inner iterator type
 */
public abstract class NestedIterator<O,I> extends AbstractIterator<I>{

	private ResourceIterator<O> outerIterator = null;
	private ResourceIterator<I> innerIterator = null;
	private List<ResourceIterator> usedIterators = null;
	private boolean exhausted = false;

	
	public NestedIterator(ResourceIterator<O> outerIterator) {
		super();
		this.outerIterator = outerIterator;
	}

	public boolean hasNext() {
		if (exhausted) 
			return false;	
		boolean hasMore = false;
		if (this.innerIterator==null || !this.innerIterator.hasNext()) {
			while (!hasMore && !exhausted) 
				hasMore = moveCursor();		
			return !exhausted && this.innerIterator.hasNext(); 
		}
		else
			return true;
	}
		
	public I next() {
		if (hasNext()) 
			return innerIterator.next();
		else
			return null;
	}
	
	private boolean moveCursor() {
		if (this.outerIterator.hasNext()) {
			O selectedObject = this.outerIterator.next();
			this.innerIterator = this.getNextIterator(selectedObject);
			if (usedIterators==null) {
				usedIterators = new ArrayList<ResourceIterator>();
			}
			usedIterators.add(innerIterator);
			return innerIterator.hasNext();
		}
		else {
			exhausted = true;
			return false;
		}
	}
	
	/**
	 * Get the iterator for the next object returned by the outer iterator.
	 * @param object an object
	 * @return an iterator.
	 */
	protected abstract ResourceIterator<I> getNextIterator(O object);
	
	/**
	 * Close the iterator.
	 */
	public void close() {
		this.outerIterator.close();
		if (usedIterators!=null) {
			for (ResourceIterator iter:this.usedIterators) {
				iter.close();
			}
		}
		this.usedIterators = null;
		this.innerIterator = null;
		this.outerIterator = null;
		
	}

}
