package test.nz.org.take.r2ml.c;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import test.nz.org.take.r2ml.Log4jConfigurator;
import test.nz.org.take.r2ml.c.generated.FamilyKB;
import test.nz.org.take.r2ml.c.generated._isFamily;

import nz.org.take.KnowledgeBase;
import nz.org.take.TakeException;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.XmlTypeHandler;
import nz.org.take.rt.ResultSet;

import junit.framework.TestCase;

public class EqualityAtomHandlerTest extends TestCase {
	
	FamilyKB kb = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			Log4jConfigurator.configure();
			kb = new FamilyKB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() {
		Person m = new Person("Max", "Dietrich");
		Person j = new Person("Jens", "Dietrich");
		Person b = new Person("Bastian", "Schenke");
		
		ResultSet<_isFamily> result = kb.isFamily_11(m, j);
		assertTrue(result.hasNext());
		_isFamily r = result.next();
		assertTrue(r.slot1.equals(m));
		
		ResultSet<_isFamily> result2 = kb.isFamily_11(j, b);
		assertFalse(result.hasNext());

	}

}
