package test.nz.org.take.script;

import static org.junit.Assert.*;
import org.junit.Test;

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
	
	@Test
	public void testColonNamespacesInAnnotationKey() throws Exception {
		KnowledgeBase kb = parse(
				"@dc:creator=jens         \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "dc:creator", "jens");
	}
	
	@Test
	public void testDotNamespacesInAnnotationKey() throws Exception {
		KnowledgeBase kb = parse(
                "@take.compilerhint.method=getGrandfather \n" +
                "fact: predicate| true |  \n"
        );
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "take.compilerhint.method", "getGrandfather");
	}
	
	@Test
	public void testWhitespaceInAnnotationValue() throws Exception {
		KnowledgeBase kb = parse(
				"@creator=jens dietrich   \n" +
				"fact: predicate| true |  \n"
			);
		KnowledgeElement fact = kb.getElement("fact");
		assertAnnotatedWith(fact, "creator", "jens dietrich");
	}
	
	@Test
	public void testOverridingAnnotation() throws Exception {
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
