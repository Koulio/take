package test.nz.org.take.r2ml.scenario1;

import nz.org.take.TakeException;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.r2ml.scenario1.generated.FamilyKB;
import test.nz.org.take.r2ml.scenario1.generated._isFather;
import junit.framework.TestCase;

public class Scenario1Test extends TestCase {
	
	private FamilyKB kb = null;
	
	/**
	 * Perform pre-test initialization
	 * 
	 * @throws Exception
	 * 
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		try {
			KnowledgeBaseManager<FamilyKB> kbm = new KnowledgeBaseManager<FamilyKB>();
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(Scenario1Test.class.getResourceAsStream("/test/nz/org/take/r2ml/scenario1/rules.xml"));
			kSrc.setDatatypeMapper(new MyDatatypeMapper());
			kSrc.setSlotNameGenerator(new MyNameMapper());
			KBUtil.addKnowledge(kSrc);
			kb = kbm.getKnowledgeBase(FamilyKB.class, kSrc);
		} catch (TakeException e) {
			e.printStackTrace();
		}
	}

	public void test1 () {
		
		Person jens = new Person("Dietrich");
		Person max = new Person("Dietrich");
		
		ResultSet<_isFather> result = kb.isFather_10(max);
		
		assertTrue(result.hasNext());
		_isFather isFather = result.next();
		assertTrue(jens.equals(isFather.father));
	}

}
