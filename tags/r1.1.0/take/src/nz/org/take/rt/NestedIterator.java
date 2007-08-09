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
		for (ResourceIterator iter:this.usedIterators) {
			iter.close();
		}
		this.usedIterators = null;
		this.innerIterator = null;
		this.outerIterator = null;
		
	}

}
