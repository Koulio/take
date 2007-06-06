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
package test.nz.org.take.r2ml.b;

import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.BasicConfigurator;

import nz.org.take.KnowledgeBase;
import nz.org.take.r2ml.CheckOnlyNormalizer;
import nz.org.take.r2ml.DefaultNameMapper;
import nz.org.take.r2ml.MappingContext;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.XmlTypeHandler;
import junit.framework.TestCase;

public class AttributionAtomHandlerTest extends TestCase {

	R2MLDriver driver = null;

	public AttributionAtomHandlerTest() {
		super("AttributionAtomHandlerTest");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		driver = new R2MLDriver();
		driver.setNameMapper(new DefaultNameMapper());
		driver.setDatatypeMapper(new DefaultMapper());
		driver.setNormalizer(new CheckOnlyNormalizer());
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
			assertEquals(1, kb.getElements().size());
			assertNotNull("Rule id \"DR000\" not found", kb.getElement("DR000"));
			assertEquals("Wron predicate identifier in head of rule", "surname", kb.getElement("DR000").getPredicate().getName());
		} catch (Exception e) {
			fail("Exception occured :" + e.toString());
		}
	}

}
