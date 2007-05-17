package test.nz.org.take.r2ml.b;

import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.BasicConfigurator;

import nz.org.take.KnowledgeBase;
import nz.org.take.r2ml.DefaultSlotNameGenerator;
import nz.org.take.r2ml.MappingContext;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.XmlTypeHandler;
import junit.framework.TestCase;

public class TestDatatypeMapping extends TestCase {

	static {
		BasicConfigurator.configure();
	}

	R2MLDriver driver = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		driver = new R2MLDriver();
		driver.setSlotNameGenerator(new DefaultSlotNameGenerator());
		driver.setDatatypeMapper(new DefaultMapper());
		driver.initialize();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		driver = null;
	}

	public void test1() {
		try {
			JAXBContext jc = JAXBContext
					.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//
			Unmarshaller um = jc.createUnmarshaller();
			Object root = ((JAXBElement) um.unmarshal(new FileInputStream(
					"src/test/nz/org/take/r2ml/b/attributionTest.xml")))
					.getValue();
			XmlTypeHandler handler = driver
					.getHandlerByXmlType(root.getClass());
			MappingContext context = new MappingContext();
			KnowledgeBase kb = (KnowledgeBase) handler.importObject(root,
					context, driver);
			assertEquals(1, kb.getElements());
		} catch (Exception e) {
			fail("Exception occured" + e.toString());
		}
	}

}
