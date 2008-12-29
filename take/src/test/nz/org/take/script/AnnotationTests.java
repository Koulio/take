package test.nz.org.take.script;

import nz.org.take.Annotatable;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;

public class AnnotationTests extends AbstractParserTests {
	
	public void testGlobalAnnotation() throws Exception {
		KnowledgeBase kb = parse(
				"@@test=123           \n" +
				"fact: predicate[]    \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "test", "123");
	}
	
	public void testLocalAnnotation() throws Exception {
		KnowledgeBase kb = parse(
				"@test=123            \n" +
				"fact: predicate[]    \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "test", "123");
	}
	
	public void testAnnotationOverride() throws Exception {
		KnowledgeBase kb = parse(
				"@@scope=global       \n" +
				"@scope=local         \n" +
				"fact: predicate[]    \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "scope", "local");
	}
	
	private void assertAnnotatedWith(Annotatable annotatable, String key, String value) {
		assertEquals(value, annotatable.getAnnotation(key));
	}
}
