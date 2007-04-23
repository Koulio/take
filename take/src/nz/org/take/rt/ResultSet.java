/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

import java.util.List;
/**
 * Result set. Wraps a resource iterator. Has an additional reference to the derivation log.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class ResultSet<T> implements ResourceIterator<T>{

	private DerivationController derivationController = null;
	private ResourceIterator<T> delegate = null;
	
	public ResultSet( ResourceIterator<T> delegate,DerivationController derivationController) {
		super();
		this.derivationController = derivationController;
		this.delegate = delegate;
	}

	/**
	 * Close the iterator.
	 */
	public void close() {
		this.delegate.close();
	}

	public List<String> getDerivationLog() {
		return derivationController.getLog();
	}

	public DerivationController getDerivationController() {
		return derivationController;
	}
	public boolean hasNext() {
		return this.delegate.hasNext();
	}

	public T next() {
		return this.delegate.next();
	}

	public void remove() {
		this.delegate.remove();
	}
	
	/**
	 * Cancel the derivation.
	 */
	public void cancel() {
		this.derivationController.cancel();
	}
	/**
	 * Whether the derivation has been cancelled.
	 * @return the cancelled status
	 */
	public boolean isCancelled() {
		return this.derivationController.isCancelled();
	}


}
