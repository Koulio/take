package test.nz.org.take.script;

import nz.org.take.Annotatable;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;

public class AnnotationTests extends AbstractParserTests {
	
	public void testGlobalAnnotation() throws Exception {
		KnowledgeBase kb = parse(
				"@@test=123               \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "test", "123");
	}
	
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
	public void testAnnotationKey() throws Exception {
		KnowledgeBase kb = parse(
				"@dc:creator=jens         \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "dc:creator", "jens");
	}
	
	/**
	 * Whitespaces should be allowed in values.
	 * @throws Exception
	 */
	public void testAnnotationValue() throws Exception {
		KnowledgeBase kb = parse(
				"creator=jens dietrich        \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "creator", "jens dietrich");
	}
	
	
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
