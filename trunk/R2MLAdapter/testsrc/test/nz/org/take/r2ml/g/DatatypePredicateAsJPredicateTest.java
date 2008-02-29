package test.nz.org.take.r2ml.g;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.BasicConfigurator;

import de.tu_cottbus.r2ml.RuleBase;
import example.nz.org.take.r2ml.userv.domain.UServDatatypeMapper;
import example.nz.org.take.r2ml.userv.domain.UServQueryGenerator;

import nz.org.take.DerivationRule;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.TakeException;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.r2ml.util.TypeVariablesFilter;
import junit.framework.TestCase;

public class DatatypePredicateAsJPredicateTest extends TestCase {

	KnowledgeBase kb = null;

	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
		InputStream is = DatatypePredicateAsJPredicateTest.class.getResourceAsStream("rules_g3.r2ml");
		R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(is);
		kSrc.setDatatypeMapper(new UServDatatypeMapper());
		kb = kSrc.getKnowledgeBase();
	}

	public void test01 () throws TakeException {
		assertNotNull(kb);
		assertEquals(1, kb.getElements().size());
		DerivationRule dr = (DerivationRule) kb.getElements().get(0);
		assertEquals(4, dr.getBody().size());
		assertTrue(dr.getBody().get(0).toString().startsWith("!isCar"));
		assertTrue(dr.getBody().get(1).toString().startsWith("!isDriverAirbag"));
		assertTrue(dr.getBody().get(2).toString().startsWith("isSideAirbag"));
		assertTrue(dr.getBody().get(3).toString().startsWith("isPassengerAirbag"));
	}
	
}
