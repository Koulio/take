//package test.nz.org.take.r2ml.a;
//
//import java.io.StringReader;
//import java.util.List;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBElement;
//import javax.xml.bind.Unmarshaller;
//
//import nz.org.take.r2ml.EqualityAtomHandler;
//import nz.org.take.r2ml.EqualityAtomHandler.EqualityPair;
//
//import de.tu_cottbus.r2ml.EqualityAtom;
//import junit.framework.TestCase;
//
//public class EqualityAtomHandlerTest extends TestCase {
//	
//	EqualityAtomHandler handler = null;
//	EqualityAtom atom = null;
//
//	@Override
//	protected void setUp() throws Exception {
//		String testXml =
//			"<r2ml:EqualityAtom \n" +
//			"		xmlns:r2ml=\"http://www.rewerse.net/I1/2006/R2ML\">" +
//			"	<r2ml:ObjectVariable r2ml:name=\"A\" r2ml:classID=\"string\" />" +
//			"	<r2ml:ObjectVariable r2ml:name=\"B\" r2ml:classID=\"string\" />" +
//			"	<r2ml:ObjectVariable r2ml:name=\"C\" r2ml:classID=\"string\" />" +
//			"</r2ml:EqualityAtom>";
//
//		try {
//			JAXBContext jc = JAXBContext
//					.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//
//			Unmarshaller um = jc.createUnmarshaller();
//			handler = new EqualityAtomHandler();
//			atom = (EqualityAtom) ((JAXBElement<EqualityAtom>)(um.unmarshal(new StringReader(testXml)))).getValue();
//		} catch (Exception e) {
//			fail("Exception occured" + e.toString());
//		}
//		super.setUp();
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//
//	public void test1() {
//		System.out.println(atom.getObjectTerm().size() * (atom.getObjectTerm().size()-1));
//		List<EqualityPair> pairs = handler.getArgsAsPairs(atom.getObjectTerm());
//		assertEquals(
//				"Wrog number of pairs",
//				atom.getObjectTerm().size() * (atom.getObjectTerm().size()-1),
//				pairs.size());
//	}
//
//}
