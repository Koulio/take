package test.nz.org.take.script;

import java.util.Arrays;
import nz.org.take.Constant;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Variable;

public class TermTests extends AbstractParserTests {
	
	public void testPrimativeVariableDeclaration() throws Exception {
		KnowledgeBase kb = parse(
				"var int anInteger               \n" +
				"fact: predicate| anInteger |    \n"
			);
		Fact fact = (Fact)kb.getElement("fact");
		
		Predicate predicate = fact.getPredicate();
		assertEquals(new Class[] {int.class}, predicate.getSlotTypes());
	}
	
	public void testClassVariableDeclaration() throws Exception {
		KnowledgeBase kb = parse(
				"var java.util.Date aDate        \n" +
				"fact: predicate| aDate |        \n"
			);
		Fact fact = (Fact)kb.getElement("fact");
		
		Predicate predicate = fact.getPredicate();
		assertEquals(new Class[] {java.util.Date.class}, predicate.getSlotTypes());
	}
	
	public void testStringLiteralConstant() throws Exception {
		KnowledgeBase kb = parse(
				"fact: predicate| \"testLiteral\" | \n"
			);
		Fact fact = (Fact)kb.getElement("fact");
		
		Predicate predicate = fact.getPredicate();
		assertEquals(new Class[] {String.class}, predicate.getSlotTypes());
	}
	
	public void testNumericalConstants() throws Exception {
		KnowledgeBase kb = parse(
				"fact: predicate| 10, 10L, 10.0 |  \n"
			);
		Fact fact = (Fact)kb.getElement("fact");
		
		Predicate predicate = fact.getPredicate();
		assertEquals(new Class[] {Integer.class, Long.class, Double.class}, predicate.getSlotTypes());
	}
	
	private void assertEquals(Object[] expected, Object[] actual) {
		assertTrue(Arrays.equals(expected, actual));
	}
}
