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
 * Singleton iterator. This iterator index' a "Collection" containing only one single
 * object. This object can be accessed only one single time.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <T> the type of the iterated element
 */
public class SingletonIterator<T> extends AbstractIterator<T> {
	private T object = null;
	
	/**
	 * Construct a new <code>SingletonIterator</code> indexing the provided object.
	 * @param object the element indexed by this Iterator.
	 */
	public SingletonIterator(T object) {
		super();
		assert(object!=null);
		this.object = object;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return object!=null;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		T next = object;
		object = null;
		return next;
	}

	/* (non-Javadoc)
	 * @see org.mandarax.compiler.rt.ResourceIterator#close()
	 */
	public void close() {
		// nothing to do here
	}




}
