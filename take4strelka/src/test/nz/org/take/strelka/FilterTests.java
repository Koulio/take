/**
 * 
 */
package test.nz.org.take.strelka;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.BasicConfigurator;

import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.r2ml.util.TypeVariablesFilter;
import nz.org.take.strelka.filter.SubstituteRealFilter;

import de.tu_cottbus.r2ml.ObjectVariable;
import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.TypedLiteral;

import junit.framework.TestCase;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class FilterTests extends TestCase {

	private RuleBase ruleBase = null;

	public FilterTests() {
		super("TestRepairFilter");
	}

	/**
	 * @param name
	 */
	public FilterTests(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
		JAXBContext jc = JAXBContext
				.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//

		Unmarshaller um = jc.createUnmarshaller();

		um.setSchema(null);
		InputStream is = FilterTests.class.getResourceAsStream("tmp.xml");
		Reader reader = new InputStreamReader(is);
		ruleBase = (RuleBase) ((JAXBElement) um.unmarshal(reader)).getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		ruleBase = null;
	}

	public void testTypeVariableFilter() throws JAXBException, R2MLException {
		// printRuleBase();
		List<RuleBaseFilter> filters = new ArrayList<RuleBaseFilter>(3);
		filters.add(new TypeVariablesFilter());
		for (RuleBaseFilter filter : filters) {
			filter.repair(ruleBase);
		}

		Iterator iter = JXPathContext
				.newContext(ruleBase)
				.iterate(
						"//objectTerm[declaredType='class de.tu_cottbus.r2ml.ObjectVariable']");
		int i = 0;
		while (iter.hasNext()) {
			i++;
			ObjectVariable element = (ObjectVariable) ((JAXBElement) iter
					.next()).getValue();
			System.out.println(element.getName() + " is of type "
					+ element.getClassID());
			assertNotNull(element);
			assertNotNull(element.getName() + "", element.getClassID());
		}
		assertTrue(i > 2);
		assertNotNull(ruleBase);

		// printRuleBase();

	}

//	public void testReal2DoubleFilter() throws R2MLException {
//		List<RuleBaseFilter> filters = new ArrayList<RuleBaseFilter>(3);
//		filters.add(new SubstituteRealFilter());
//
//		Iterator it = JXPathContext.newContext(ruleBase).iterate(
//				"//*[datatypeID]");
//		if (it.hasNext()) {
//			TypedLiteral element2 = (TypedLiteral) it.next();
//			System.out.println(element2.getDatatypeID());
//		}
//		for (RuleBaseFilter filter : filters) {
//			filter.repair(ruleBase);
//		}
//
//		Iterator it2 = JXPathContext.newContext(ruleBase).iterate(
//				"//*[datatypeID]");
//		while (it2.hasNext()) {
//			TypedLiteral element = (TypedLiteral) it2.next();
//			assertNotNull(element.getDatatypeID());
//			assertEquals("{http://www.w3.org/2001/XMLSchema}double", element
//					.getDatatypeID().toString());
//		}
//	}

//	/**
//	 * @throws JAXBException
//	 * @throws PropertyException
//	 */
//	private void printRuleBase() throws JAXBException, PropertyException {
//		JAXBContext jc = JAXBContext
//				.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");
//		Marshaller m = jc.createMarshaller();
//		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//		m.marshal(ruleBase, System.out);
//	}

}
