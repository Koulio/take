package test.nz.org.take.r2ml.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import de.tu_cottbus.r2ml.Conclusion;
import de.tu_cottbus.r2ml.DerivationRule;
import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.GenericAtom;
import de.tu_cottbus.r2ml.RuleBase;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.r2ml.DefaultSlotNameGenerator;
import nz.org.take.r2ml.MappingContext;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.SlotNameGenerator;
import nz.org.take.r2ml.XmlTypeHandler;
import junit.framework.TestCase;

public class TestR2MLDriver extends TestCase {

	static {
		BasicConfigurator.configure();
	}
	R2MLDriver driver = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		driver = new R2MLDriver();
		driver.setSlotNameGenerator(new DefaultSlotNameGenerator());
		driver.setDatatypeMapper(new TestDatatypeMapper());
		driver.initialize();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		driver = null; 
	}

	public void test1() {
		driver.logger.info("testInitialize01 started");
		assertNotNull(driver.getHandlerByXmlType(RuleBase.class));
		assertNotNull(driver.getHandlerByXmlType(DerivationRuleSet.class));
		assertNotNull(driver.getHandlerByXmlType(DerivationRule.class));
		driver.logger.info("testInitialize01 finished");
	}

	public void test2() {
		driver.logger.info("testImportKB01 started");
		KnowledgeBase kb = null;
		try {
			InputStream is = new FileInputStream(new File(
					"resources/default.r2ml.xml"));
			kb = driver.importKB(is);
		} catch (R2MLException r2mle) {
			fail("Internal Error: " + r2mle.toString());
		} catch (FileNotFoundException e) {
			fail("Exception occured:/n" + e.toString());
		}
		assertNotNull("No Knowledgebase returned!", kb);
		assertEquals("More or less then one rule (expected exactly one)!", 1, kb.getElements().size());
		driver.logger.info("testImportKB01 finished");
	}
	
	public void test3() {
		driver.logger.info("testGenericAtomHandler started");
		String testXml =
			"<r2ml:GenericAtom " +
			"		r2ml:predicateID='fatherOf' " +
			"		xmlns:xs='http://www.w3.org/2001/XMLSchema' " +
			"		xmlns:r2ml='http://www.rewerse.net/I1/2006/R2ML'>" +
			"	<r2ml:arguments> " +
			"		<r2ml:TypedLiteral " +
			"				r2ml:datatypeID='xs:string' " +
			"				r2ml:lexicalValue='Max' /> " +
			"		<r2ml:PlainLiteral " +
			"				r2ml:languageTag='de-de' " +
			"				r2ml:lexicalValue='Jens' /> " +
			"	</r2ml:arguments>" +
			"</r2ml:GenericAtom>";

		try {
			JAXBContext jc = JAXBContext
			.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//
			Unmarshaller um = jc.createUnmarshaller();
			GenericAtom genAtom = (GenericAtom)((JAXBElement)um.unmarshal(new StringReader(testXml))).getValue();
			XmlTypeHandler handler = driver.getHandlerByXmlType(genAtom.getClass());
			MappingContext context = new MappingContext();
			Fact fact = (Fact) handler.importObject(genAtom, context, driver);
			assertEquals("Handler call recursion not properly resolved.", true, context.isClean());
			assertEquals("Wrong Predicatename", genAtom.getPredicateID().toString(), fact.getPredicate().getName());
			assertEquals("Number of arguments not correct", genAtom.getArguments().getTerm().size(), fact.getPredicate().getSlotTypes().length);
		} catch (Exception e) {
			fail("Exception occured" + e.toString());
		}
		
		driver.logger.info("testGenericAtomHandler finished");
		
	}

}
