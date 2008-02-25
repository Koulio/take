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

import nz.org.take.KnowledgeBase;
import nz.org.take.TakeException;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.r2ml.util.TypeVariablesFilter;
import junit.framework.TestCase;

public class TypeVariableFilterTest extends TestCase {

	RuleBase ruleBase = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
		JAXBContext jc = JAXBContext
		.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//

		Unmarshaller um = jc.createUnmarshaller();

		um.setSchema(null);
		InputStream is = TypeVariableFilterTest.class.getResourceAsStream("rules_g2.r2ml");
		Reader reader = new InputStreamReader(is);
		ruleBase = (RuleBase) ((JAXBElement) um.unmarshal(reader)).getValue();
	}

	public void test01 () throws TakeException {
		RuleBaseFilter f = new TypeVariablesFilter();
		f.repair(ruleBase);
		R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(ruleBase);
		kSrc.setDatatypeMapper(new UServDatatypeMapper());
		kSrc.setQueryGenerator(new UServQueryGenerator());
		kSrc.setPropertyMode(R2MLDriver.INFER_PROPERTIES_MODE);
		KnowledgeBase kb = kSrc.getKnowledgeBase();
		assertTrue(true);
	}
	
}
