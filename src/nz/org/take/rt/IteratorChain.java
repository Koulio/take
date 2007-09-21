/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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
