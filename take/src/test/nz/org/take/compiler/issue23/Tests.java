/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.issue23;
/**
 * Test(s).
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
import java.util.HashMap;
import nz.org.take.AggregationFunction;
import nz.org.take.Function;
import nz.org.take.nscript.DefaultFunctionFactory;
import junit.framework.TestCase;

public class Tests extends TestCase {
	public void test() {
		DefaultFunctionFactory ff = new DefaultFunctionFactory();
		Function f = ff.createFunction ("name",new HashMap<String,AggregationFunction>(),Class2.class);
		assertNotNull(f);
	}
}
