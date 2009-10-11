package test.nz.org.take.script;

import org.junit.Test;
import static org.junit.Assert.*;
import nz.org.take.Annotatable;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;

public class AnnotationTests extends AbstractParserTests {
	@Test
	public void testGlobalAnnotation() throws Exception {
		KnowledgeBase kb = parse(
				"@@test=123               \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "test", "123");
	}
	@Test
	public void testLocalAnnotation() throws Exception {
		KnowledgeBase kb = parse(
				"@test=123                \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "test", "123");
	}
	/**
	 * : should be allowed in keys to express name spaces, such as
	 * in DC
	 * @throws Exception
	 */
	@Test
	public void testAnnotationKeys1() throws Exception {
		KnowledgeBase kb = parse(
				"@dc:creator=jens         \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "dc:creator", "jens");
	}
	
	/**
	 * . should be allowed in keys to express name spaces, 
	 * this is used in compiler hints
	 * @throws Exception
	 */
	@Test
	public void testAnnotationKeys2() throws Exception {
		KnowledgeBase kb = parse(
				"@take.compilerhint.method=getGrandfather \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "take.compilerhint.method", "getGrandfather");
	}
	
	
	/**
	 * Whitespaces should be allowed in values.
	 * @throws Exception
	 */
	@Test
	public void testAnnotationValues() throws Exception {
		KnowledgeBase kb = parse(
				"creator=jens dietrich        \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "creator", "jens dietrich");
	}
	
	@Test
	public void testAnnotationOverride() throws Exception {
		KnowledgeBase kb = parse(
				"@@scope=global           \n" +
				"@scope=local             \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "scope", "local");
	}
	
	private void assertAnnotatedWith(Annotatable annotatable, String key, String value) {
		assertEquals(value, annotatable.getAnnotation(key));
	}
}
