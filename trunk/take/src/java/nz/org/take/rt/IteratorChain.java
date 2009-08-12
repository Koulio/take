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
 * Chain of iterators. Alternative implementation that chains iterators and objects.
 * This is more effective than wrapping objects as singleton iterators.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <T> the type of the iterated elements
 */

public abstract class IteratorChain<T> extends AbstractIterator<T>{
	private int cursor = -1;
	private ResourceIterator<T> delegate = null;
	private ResourceIterator<T>[] parts = null;
	private class ReusableSingletonIterator extends AbstractIterator<T> {
		T object = null;
		public void close() {
			object = null;
		}
		public boolean hasNext() {
			return object!=null;
		}
		public T next() {
			T obj = object;
			if (obj==null)
				throw new java.lang.IllegalStateException("ReusableSingletonIterator: object already consumed, next() should not be called");
			object = null;
			return obj;
		}
		public void remove() {
			
		}		
	}
	private ReusableSingletonIterator reusableSingletonIterator = new ReusableSingletonIterator();
	
	@SuppressWarnings("unchecked")
	public IteratorChain(int size) {
		super();
		this.parts = new ResourceIterator[size];
	}
	private void moveCursor() {
		cursor = cursor+1;
		if (cursor<parts.length){
			Object object = this.getIteratorOrObject(cursor);
			if (object==null) {
				delegate = EmptyIterator.DEFAULT;
			}
			else if (object instanceof ResourceIterator){
				parts[cursor]=(ResourceIterator)object;
				delegate = (ResourceIterator)object;
			}
			else {
				reusableSingletonIterator.object = (T)object;
				delegate = reusableSingletonIterator;
			}
				
		}
		else
			delegate = null;
	}
	public boolean hasNext() {
		if (cursor==parts.length) return false;
		else if (cursor==-1) {
			moveCursor();
			return hasNext();
		}
		else if (delegate.hasNext()) {
			return true;
		}
		else {
			moveCursor();
			return hasNext();
		}
	}
	public T next() {
		if (hasNext()) {
			return delegate.next();
		}
		else 
			return null;
	}
	/**
	 * Get the iterator at the given position.
	 * @param pos the index
	 * @return an iterator
	 */
	public abstract Object getIteratorOrObject(int pos);

	/**
	 * Close the iterator.
	 */
	public void close() {
		for (ResourceIterator iter:this.parts) {
			if (iter!=null)
				iter.close();
		}
	}

}
