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
import java.util.List;

/**
 * Utility class to controll the derivation process.
 * Responsibilities:
 * <ol>
 * <li>record the derivation tree, and return it to the application</li>
 * <li>interrupt derivations</li>
 * <li>optional: progress notification</li>
 * <li>optional: loop checking</li>
 * <li>optional: stop derivation after a certain number of steps</li>
 * </ol>
 * Used to record derivation trees, and can be used to cancel the inference process.
 * Recorded are string ids of the rules, facts etc, applications can use this strings to 
 * access artefacts in the knowledge base.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <T> the type of the iterated elements
 */

public interface DerivationController  {
	// constant signaling that no parameter has been provided
	public static final Object NIL = new Object() {
		public String toString() {
			return "?";
		}
	};
	// constants to be used as parameters in log
	public static final int ANY = 0;
	public static final int RULE = 1;
	public static final int FACT = 2;
	public static final int BEAN_PROPERTY = 3;
	public static final int JAVA_METHOD = 4;
	public static final int COMPARISON = 5;
	public static final int EXTERNAL_FACT_SET = 6;
	
	/**
	 * Log the use of a clause set
	 * @param ruleRef a string referencing the knowledge element (id or similar)
	 * @param in kind what kind of knowledge this is (one of the constants RULE, FACT etc)
	 * @param the parameter bindings used
	 */
	public void log(String ruleRef,int kind,Object... param) ;
	/**
	 * Get (a copy of) the derivation log. 
	 * May throw a runtime exception (e.g., if the derivation has been cancelled). 
	 * @return a list
	 */
	public List<DerivationLogEntry> getLog() ;
	
	/**
	 * Print the log to a print stream.
	 * @param out a print stream 
	 */
	public void printLog(PrintStream out) ;
	/**
	 * Print the log to System.out.
	 */
	public void printLog() ;
	/**
	 * Get the derivation level.
	 * @return
	 */
	public int getDepth() ;
	/**
	 * Increase the derivation level.
	 * @return this
	 */
	public DerivationController increaseDepth() ;
	/**
	 * Reset the derivation level.
	 * @param value
	 * @return this
	 */
	public DerivationController reset(int value);
	/**
	 * Cancel the derivation.
	 */
	public void cancel() ;
	/**
	 * Whether the derivation has been cancelled. If the derivation is cancelled, the next call to log should
	 * trigger a DerivationCancelledException. 
	 * This can be used by applications to stop long running derivations.
	 * @return the cancelled status
	 */
	public boolean isCancelled() ;
	/**
	 * Get the number of derivation steps performed so far.
	 * @return an int
	 */
	public int getDerivationCount() ;
}
