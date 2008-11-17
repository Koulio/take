/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
