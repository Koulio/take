/**
 * 
 */
package test.nz.org.take.r2ml.d;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.r2ml.d.generated.ThingKB;
import test.nz.org.take.r2ml.d.generated.result;
import de.tu_cottbus.r2ml.RuleBase;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class ReplacePropertyFunctionTest extends TestCase {
	
	private ThingKB kb;

	/**
	 * @param name
	 */
	public ReplacePropertyFunctionTest(String name) {
		super(name);
		BasicConfigurator.configure();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(GenerateKbIf.class.getResourceAsStream("/test/nz/org/take/r2ml/d/properties2.r2ml"));
		kSrc.setPropertyMode(R2MLDriver.INFER_PROPERTIES_MODE);
		kSrc.setDatatypeMapper(new ThingMapper());
		kSrc.setQueryGenerator(new ThingQueryGenerator());
		KnowledgeBaseManager<ThingKB> kbM = new KnowledgeBaseManager<ThingKB>();
		kb = kbM.getKnowledgeBase(ThingKB.class, kSrc);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		kb = null;
	}

	public void test01 () throws R2MLException {
		assertNotNull(kb);
		Thing thing1 = new Thing();
		ResultSet<result> result = kb.result_10(thing1);
		assertTrue(result.hasNext());
		result r1 = result.next();
		assertFalse(result.hasNext());		
		Thing thing2 = new Thing();
		thing2.setValue(20);
		result = kb.result_10(thing2);
		assertFalse(result.hasNext());
		System.out.println("15 is not greater than 20");
	}
}
