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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Data structure to store the reference to elements in the knowledge base.
 * Used to record derivation trees, and can be used to cancel the inference process.
 * Recorded are string ids of the rules, facts etc, applications can use this strings to 
 * access artefacts in the knowledge base.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <T> the type of the iterated elements
 */

public class DefaultDerivationController  implements DerivationController {
	private List<String> delegate = new ArrayList<String>();
	private List<Integer> delegate2 = new ArrayList<Integer>();
	private int depth = 0;
	private boolean cancelled = false;
	private int derivationCount = 0;
	private DerivationListener derivationListener = null;
	/**
	 * Log the use of a clause set
	 */
	public void log(String ruleRef,int kind) {
		if (cancelled) 
			throw new DerivationCancelledException();
		
		// System.out.println("Log@" + depth + " : " + ruleRef);
		this.delegate.add(depth,ruleRef);	
		this.delegate2.add(depth,kind);
		this.derivationCount = this.derivationCount+1;
		
		if (derivationListener!=null)
			derivationListener.step(ruleRef, depth, derivationCount);
	}
	/**
	 * Get a copy of the derivation log. 
	 * @return a list
	 */
	public List<DerivationLogEntry> getLog() {
		List<DerivationLogEntry> list = new ArrayList<DerivationLogEntry>();
		for (int i=0;i<=depth;i++) {
			String s = this.delegate.get(i);
			if (s!=null) {
				list.add(new DerivationLogEntry(this.delegate.get(i),this.delegate2.get(i)));
			}
		}
			
		return list;
	}
	
	/**
	 * Print the log to a print stream.
	 * @param out a print stream 
	 */
	public void printLog(PrintStream out) {
		for (int i=0;i<=depth;i++) {
			String s = this.delegate.get(i);
			if (s!=null) {
				out.print(i+1);
				out.print(". ");
				out.println(s);	
			}
		}
	}
	/**
	 * Print the log to System.out.
	 */
	public void printLog() {
		printLog(System.out);
	}
	/**
	 * Get the derivation level.
	 * @return
	 */
	public int getDepth() {
		return depth;
	}
	/**
	 * Increase the derivation level.
	 * @return this
	 */
	public DefaultDerivationController increaseDepth() {
		this.depth = depth+1;
		return this;
	}
	/**
	 * Reset the derivation level.
	 * @param value
	 * @return this
	 */
	public DefaultDerivationController reset(int value) {
		assert value<=depth;
		assert value>=0;
		this.depth = value;
		return this;
	}
	/**
	 * Cancel the derivation.
	 */
	public void cancel() {
		this.cancelled = true;
	}
	/**
	 * Whether the derivation has been cancelled.
	 * @return the cancelled status
	 */
	public boolean isCancelled() {
		return this.cancelled;
	}
	/**
	 * Get the number of derivation steps performed so far.
	 * @return an int
	 */
	public int getDerivationCount() {
		return derivationCount;
	}
	public DerivationListener getDerivationListener() {
		return derivationListener;
	}
	public void setDerivationListener(DerivationListener derivationListener) {
		this.derivationListener = derivationListener;
	}
}
