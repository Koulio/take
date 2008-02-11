/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take;

import org.apache.log4j.BasicConfigurator;
import junit.framework.TestCase;

/**
 * Abstract superclass for all take tests.
 * Mainly used to initialise log4j.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */


public class TakeTestCase extends TestCase
{

	/**
	 * Construct new test instance
	 * @param name the test name
	 */
	public TakeTestCase(String name)
	{
		super(name);
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
	}
	@Override
	protected void tearDown() throws Exception {
		org.apache.log4j.LogManager.shutdown();
		super.tearDown();
	}

	
}
	