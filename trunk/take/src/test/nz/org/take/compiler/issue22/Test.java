/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.issue22;

import nz.org.take.rt.ResultSet;
import test.nz.org.take.compiler.issue22.generated.*;
import junit.framework.TestCase;
/**
 * Test(s).
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Test extends TestCase {
	
	public void test1() {
		
		KB kb = new KB();
		Parent parent = new Parent();
		parent.setName("a parent");
		Child child1 = new Child();
		child1.setName("a child");
		parent.getChildren().add(child1);
		Child child2 = new Child();
		child2.setName("a child");
		parent.getChildren().add(child2);
		Child child3 = new Child();
		child3.setName("another child");
		parent.getChildren().add(child3);
		ResultSet<Record> records = kb.getRecords(parent);
		assertTrue(records.hasNext());
		Record c1 = records.next();
		
	}
}
