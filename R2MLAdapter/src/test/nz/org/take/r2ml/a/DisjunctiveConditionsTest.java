/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package test.nz.org.take.r2ml.a;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import test.nz.org.take.r2ml.Log4jConfigurator;

import de.tu_cottbus.r2ml.Condition;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.r2ml.CheckOnlyNormalizer;
import nz.org.take.r2ml.DefaultDatatypeMapper;
import nz.org.take.r2ml.DefaultNameMapper;
import nz.org.take.r2ml.Normalizer;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import junit.framework.TestCase;

public class DisjunctiveConditionsTest extends TestCase {

	R2MLDriver driver = null;

	public DisjunctiveConditionsTest() {
		super("DisjunctiveConditionsTest");
	}

	protected void setUp() throws Exception {
		super.setUp();
		Log4jConfigurator.configure();
		driver = new R2MLDriver();
		driver.setNameMapper(new DefaultNameMapper());
		driver.setDatatypeMapper(new DefaultDatatypeMapper());
		driver.setNormalizer(new CheckOnlyNormalizer());
		driver.initialize();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		Log4jConfigurator.shutdown();
		driver = null;
	}
	
	public void testGenericAtom() {
		driver.logger.info("##### testGenericAtom #####");
		String testXml =
			"<r2ml:conditions\n" +
			"		xmlns:r2ml='http://www.rewerse.net/I1/2006/R2ML'" +
			"		xmlns:xs='http://www.w3.org/2001/XMLSchema'>\n" +
			"	<r2ml:qf.Disjunction>\n" +
			"		<r2ml:GenericAtom r2ml:predicateID='dummy'>\n" +
			"			<r2ml:arguments>\n" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='A'/>\n" +
			"			</r2ml:arguments>\n" +
			"		</r2ml:GenericAtom>\n" +
			"		<r2ml:GenericAtom r2ml:predicateID='dummy'>\n" +
			"			<r2ml:arguments>\n" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='B'/>\n" +
			"			</r2ml:arguments>\n" +
			"		</r2ml:GenericAtom>\n" +
			"	</r2ml:qf.Disjunction>\n" +
			"</r2ml:conditions>\n";

		try {
			JAXBContext jc = JAXBContext
			.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//
			Unmarshaller um = jc.createUnmarshaller();
			Condition condition = (Condition)((JAXBElement)um.unmarshal(new StringReader(testXml))).getValue();
			assertEquals("Normalizer returned another condition.", condition, driver.getNormalizer().normalize(condition));
			
		} catch (Exception e) {
			fail("Exception occured" + e.toString());
		}
	}
	
	public void testUnnormalizedCondition() {
		driver.logger.info("##### testUnnormalizedCondition #####");
		String testXml =
			"<r2ml:conditions" +
			"		xmlns:r2ml='http://www.rewerse.net/I1/2006/R2ML'" +
			"		xmlns:xs='http://www.w3.org/2001/XMLSchema'>" +
			"	<r2ml:qf.Disjunction>" +
			"		<r2ml:GenericAtom r2ml:predicateID='pred1'>" +
			"			<r2ml:arguments>" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='A'/>" +
			"			</r2ml:arguments>" +
			"		</r2ml:GenericAtom>" +
			"		<r2ml:GenericAtom r2ml:predicateID='pred2'>" +
			"			<r2ml:arguments>" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='B'/>" +
			"			</r2ml:arguments>" +
			"		</r2ml:GenericAtom>" +
			"	</r2ml:qf.Disjunction>" +
			"	<r2ml:qf.StrongNegation>" +
			"		<r2ml:GenericAtom r2ml:predicateID='pred3'>" +
			"			<r2ml:arguments>" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='C'/>" +
			"			</r2ml:arguments>" +
			"		</r2ml:GenericAtom>" +
			"	</r2ml:qf.StrongNegation>" +
			"</r2ml:conditions>";

		try {
			JAXBContext jc = JAXBContext.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");
			Unmarshaller um = jc.createUnmarshaller();
			Condition condition = (Condition)((JAXBElement)um.unmarshal(new StringReader(testXml))).getValue();
			Normalizer norm = new CheckOnlyNormalizer();
			norm.normalize(condition);
			fail("Exception expected.");
		} catch (R2MLException r2mle) {
			assertEquals("Wrong Errorcode.", R2MLException.FEATURE_NOT_SUPPORTED, r2mle.getErrorCode());
		} catch (Exception e) {
			fail("Exception occured" + e.toString());
		}
	}
	
	public void testQfDisjunction() {
		driver.logger.info("##### testQfDisjunction #####");
		String testXml =
			"<r2ml:conditions" +
			"		xmlns:r2ml='http://www.rewerse.net/I1/2006/R2ML'" +
			"		xmlns:xs='http://www.w3.org/2001/XMLSchema'>" +
			"	<r2ml:qf.Disjunction>" +
			"		<r2ml:GenericAtom r2ml:predicateID='dummy'>" +
			"			<r2ml:arguments>" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='A'/>" +
			"			</r2ml:arguments>" +
			"		</r2ml:GenericAtom>" +
			"		<r2ml:GenericAtom r2ml:predicateID='dummy'>" +
			"			<r2ml:arguments>" +
			"				<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='B'/>" +
			"			</r2ml:arguments>" +
			"		</r2ml:GenericAtom>" +
			"	</r2ml:qf.Disjunction>" +
			"</r2ml:conditions>";

		try {
			JAXBContext jc = JAXBContext.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");
			Unmarshaller um = jc.createUnmarshaller();
			Condition condition = (Condition)((JAXBElement)um.unmarshal(new StringReader(testXml))).getValue();
			Normalizer norm = new CheckOnlyNormalizer();
			assertEquals(condition, norm.normalize(condition));
		} catch (Exception e) {
			fail("Exception occured" + e.toString());
		}
	}
	
	public void testNestedConjuntion() {
		driver.logger.info("##### testNestedConjuntion #####");
		String testXml =
			"<r2ml:RuleBase " +
			"		xmlns:xs='http://www.w3.org/2001/XMLSchema' " +
			"		xmlns:r2ml='http://www.rewerse.net/I1/2006/R2ML'>" +
			"	<r2ml:DerivationRuleSet r2ml:ruleSetID='DRStestNestedConjuntion'> " +
			"		<r2ml:DerivationRule r2ml:ruleID='DRtestNestedConjuntion'>" +
			"			<r2ml:conditions>" +
			"				<r2ml:qf.Conjunction>" +
			"					<r2ml:qf.Conjunction>" +
			"						<r2ml:GenericAtom r2ml:predicateID='pred1'>" +
			"							<r2ml:arguments>" +
			"								<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='A'/>" +
			"							</r2ml:arguments>" +
			"						</r2ml:GenericAtom>" +
			"						<r2ml:GenericAtom r2ml:predicateID='pred3'>" +
			"							<r2ml:arguments>" +
			"								<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='D'/>" +
			"							</r2ml:arguments>" +
			"						</r2ml:GenericAtom>" +
			"					</r2ml:qf.Conjunction>" +
			"					<r2ml:GenericAtom r2ml:predicateID='pred2'>" +
			"						<r2ml:arguments>" +
			"							<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='B'/>" +
			"						</r2ml:arguments>" +
			"					</r2ml:GenericAtom>" +
			"				</r2ml:qf.Conjunction>" +
			"			</r2ml:conditions>" +
			"			<r2ml:conclusion>" +
			"				<r2ml:GenericAtom r2ml:predicateID='pred3'>" +
			"					<r2ml:arguments>" +
			"						<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='C'/>" +
			"					</r2ml:arguments>" +
			"				</r2ml:GenericAtom>" +
			"			</r2ml:conclusion>" +
			"		</r2ml:DerivationRule>" +
			"	</r2ml:DerivationRuleSet>" +
			"</r2ml:RuleBase>";

		KnowledgeBase kb = null;
		try {
			StringReader input = new StringReader(testXml);
			kb = driver.importKB(input);
		} catch (R2MLException r2mle) {
			fail("Internal Error: " + r2mle.toString());
		}
		assertNotNull("No Knowledgebase returned!", kb);
		assertEquals("Wrong number of rules:", 1, kb.getElements().size());
		Iterator<KnowledgeElement> it = kb.getElements().iterator();
		assertEquals("Wrong number of conditions for rule", 3, ((nz.org.take.DerivationRule)it.next()).getBody().size());
	}

	public void testMisc() {
		driver.logger.info("##### testMisc #####");
		KnowledgeBase kb = null;
		try {
			Reader is = new FileReader(new File(
					"src/test/nz/org/take/r2ml/a/DisjunctiveConditionsTest1.xml"));
			kb = driver.importKB(is);
		} catch (R2MLException r2mle) {
			fail("Internal Error: " + r2mle.toString());
		} catch (Exception e) {
			fail("Exception occured: " + e.toString());
		}
		assertNotNull("KnowledgeBase is null.", kb);
		assertEquals("", 4, kb.getElements().size());
	}

}
