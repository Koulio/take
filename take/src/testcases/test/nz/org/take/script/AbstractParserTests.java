package test.nz.org.take.script;

import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import nz.org.take.KnowledgeBase;
import nz.org.take.script.Parser;

/**
 * The base class for all parser tests. Implements the common setup and tear-down
 * routines for all parser tests as well as exposing a number of helper functions.
 */
public abstract class AbstractParserTests {

	protected Parser parser;
	@Before
	public void setUp() {
		parser = new Parser();
	}
	@After
	public void tearDown() {
		parser = null;
	}
	
	protected KnowledgeBase parse(String script) throws Exception {
		return parser.parse(new StringReader(script));
	}
}
