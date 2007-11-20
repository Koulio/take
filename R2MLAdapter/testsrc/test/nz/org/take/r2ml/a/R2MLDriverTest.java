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

import java.io.StringReader;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import test.nz.org.take.r2ml.Log4jConfigurator;

import de.tu_cottbus.r2ml.DerivationRule;
import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.GenericAtom;
import de.tu_cottbus.r2ml.RuleBase;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.TakeException;
import nz.org.take.r2ml.MappingContext;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.XmlTypeHandler;
import nz.org.take.r2ml.reference.DefaultNameMapper;
import junit.framework.TestCase;

public class R2MLDriverTest extends TestCase {

	R2MLDriver driver = null;

	public R2MLDriverTest() {
		super("R2MLDriverTest");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log4jConfigurator.configure();
		driver = R2MLDriver.get();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Log4jConfigurator.shutdown();
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
		String testXml =
			"<r2ml:RuleBase " +
			"		xmlns:xs='http://www.w3.org/2001/XMLSchema' " +
			"		xmlns:r2ml='http://www.rewerse.net/I1/2006/R2ML'>" +
			"	<r2ml:DerivationRuleSet r2ml:ruleSetID='DRS0047'> " +
			"		<r2ml:DerivationRule r2ml:ruleID='DR047a'>" +
			"			<r2ml:conditions>" +
			"				<r2ml:qf.Disjunction>" +
			"					<r2ml:GenericAtom r2ml:predicateID='pred1'>" +
			"						<r2ml:arguments>" +
			"							<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='A'/>" +
			"						</r2ml:arguments>" +
			"					</r2ml:GenericAtom>" +
			"					<r2ml:GenericAtom r2ml:predicateID='pred2'>" +
			"						<r2ml:arguments>" +
			"							<r2ml:TypedLiteral r2ml:datatypeID='xs:string' r2ml:lexicalValue='B'/>" +
			"						</r2ml:arguments>" +
			"					</r2ml:GenericAtom>" +
			"				</r2ml:qf.Disjunction>" +
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
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(input);
			kb = kSrc.getKnowledgeBase();
		} catch (R2MLException r2mle) {
			fail("Internal Error: " + r2mle.toString());
		} catch (TakeException e) {
			fail("Internal Error: " + e.toString());
		}
		assertNotNull("No Knowledgebase returned!", kb);
		assertEquals("Wrong number of rules:", 2, kb.getElements().size());
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
			Fact fact = (Fact) handler.importObject(genAtom);
			assertTrue("Handler call recursion not properly resolved.", MappingContext.get().isClean());
			assertEquals("Wrong Predicatename", genAtom.getPredicateID().toString(), fact.getPredicate().getName());
			assertEquals("Number of arguments not correct", genAtom.getArguments().getTerm().size(), fact.getPredicate().getSlotTypes().length);
		} catch (Exception e) {
			fail("Exception occured" + e.toString());
		}
		
		driver.logger.info("testGenericAtomHandler finished");
		
	}

}
