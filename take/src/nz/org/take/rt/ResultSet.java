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

	public List<DerivationLogEntry> getDerivationLog() {
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
