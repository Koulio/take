package test.nz.org.take.r2ml.scenario1;

import nz.org.take.TakeException;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.r2ml.Log4jConfigurator;
import test.nz.org.take.r2ml.scenario1.generated.FamilyKB;
import test.nz.org.take.r2ml.scenario1.generated._isFather;
import junit.framework.TestCase;

public class Scenario1Test extends TestCase {
	

	private FamilyKB kb = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			Log4jConfigurator.configure();
			KnowledgeBaseManager<FamilyKB> kbm = new KnowledgeBaseManager<FamilyKB>();
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(Scenario1Test.class.getResourceAsStream("/test/nz/org/take/r2ml/scenario1/rules.xml"));
			kSrc.setDatatypeMapper(new MyDatatypeMapper());
			kSrc.setSlotNameGenerator(new MyNameMapper());
			KBUtil.addQuerries(kSrc);
			kb = kbm.getKnowledgeBase(FamilyKB.class, kSrc);
		} catch (TakeException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Log4jConfigurator.shutdown();
	}

	public void test1 () {
		
		Person jens = new Person("Dietrich");
		jens.setFirstname("Jens");
		Person max = new Person("Dietrich");
		max.setFirstname("Max");
		
		ResultSet<_isFather> result = kb.isFather_11(max, jens);
		
		assertTrue(result.hasNext());
		_isFather isFather = result.next();
		assertTrue(jens.equals(isFather.father));
		assertTrue(max.equals(isFather.son));
	}
	
	public void test2() {
		Person bastian = new Person("Schenke");
		bastian.setFirstname("Bastian");
		Person max = new Person("Dietrich");
		max.setFirstname("Max");
		
		ResultSet<_isFather> result = kb.isFather_11(max, bastian);
		assertFalse(result.hasNext());
	}

}
