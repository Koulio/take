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

package nz.org.take.verification;

import org.apache.log4j.Logger;

import nz.org.take.KnowledgeBase;

/**
 * Interface for verification tool. Verification tools 
 * should use a logger defined here to report their findings in detail.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class VerificationTool {
	public static Logger LOGGER = Logger.getLogger("take.verification");
	/**
	 * Check the knowledge base.
	 * Return true if the knowledge base is ok.
	 * @param kb
	 * @return
	 */
	public abstract boolean verify(KnowledgeBase kb) ;
	/**
	 * Get the name of this tool
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * Report a violation.
	 * @param details
	 */
	public void reportViolation(Object... details) {
		StringBuffer b = new StringBuffer();
		b.append(getName());
		b.append(" - ");
		for (Object s:details)
			b.append(s);
		LOGGER.warn(b.toString());
	}
	/**
	 * Report that an object has been check and no problem has been found
	 * @return
	 */
	public void reportOK(Object obj) {
		LOGGER.warn(getName() + " check succeeded for " + obj);
		StringBuffer b = new StringBuffer();
		b.append(getName());
		b.append(" check succeeded for ");
		b.append(obj);
		LOGGER.info(b.toString());
	}
	
}
