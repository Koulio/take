package test.nz.org.take.compiler.scenario0;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import test.nz.org.take.compiler.scenario0.generatedclasses.KB;
import test.nz.org.take.compiler.scenario0.generatedclasses.son;


public class Tests {
	@Test
	public void test1() throws Exception {
		Iterator<son> results = new KB().son_01("jens");
		assertTrue(results.hasNext());
	}
}
