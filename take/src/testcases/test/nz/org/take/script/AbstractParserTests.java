package test.nz.org.take.script;

import java.io.StringReader;

import junit.framework.TestCase;
import nz.org.take.KnowledgeBase;
import nz.org.take.script.Parser;

/**
 * The base class for all parser tests. Implements the common setup and tear-down
 * routines for all parser tests as well as exposing a number of helper functions.
 */
public abstract class AbstractParserTests extends TestCase {

	protected Parser parser;
	
	protected void setUp() {
		parser = new Parser();
	}
	
	protected void tearDown() {
		parser = null;
	}
	
	protected KnowledgeBase parse(String script) throws Exception {
		return parser.parse(new StringReader(script));
	}
}
