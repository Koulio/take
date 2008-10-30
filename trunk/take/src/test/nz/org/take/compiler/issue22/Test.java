package test.nz.org.take.compiler.issue22;

import nz.org.take.rt.ResultSet;
import test.nz.org.take.compiler.issue22.generated.*;
import junit.framework.TestCase;

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
