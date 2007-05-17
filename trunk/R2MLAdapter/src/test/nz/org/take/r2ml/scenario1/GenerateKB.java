/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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


package test.nz.org.take.r2ml.scenario1;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.apache.log4j.BasicConfigurator;

import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.r2ml.DefaultSlotNameGenerator;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.SlotNameGenerator;

/**
 * Script to generate a KB.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class GenerateKB {
//	public static final String ROOT = "r";
//	public static final int DEPTH = 3;
		
		/**
		 * Generate the sources for the example.
		 * @param args
		 */
		public static KnowledgeBase buildKB() throws Exception {
			//BasicConfigurator.configure();			
			File r2ml = new File("src/test/nz/org/take/r2ml/scenario1/rules.xml");
			// import rules into kb
			R2MLDriver driver = new R2MLDriver();
			SlotNameGenerator slotty = new DefaultSlotNameGenerator();
			slotty.setSlotName(new QName("isEnrolled"), new String[]{"student", "college"});
			driver.setSlotNameGenerator(slotty);
			driver.setDatatypeMapper(new MyDatatypeMapper());
			driver.initialize();
			KnowledgeBase kb = driver.importKB(new FileInputStream(r2ml));
			// add querries to kb
			Query ie1 = new Query();
			ie1.setInputParams(new boolean[]{true, true});
			Query ie2 = new Query();
			ie2.setInputParams(new boolean[]{true, false});
			Collection<Predicate> preds = kb.getSupportedPredicates();
			for (Predicate pred:preds) {
				if (pred.getName().equals("isEnrolled")) {
					ie1.setPredicate(pred);
					ie2.setPredicate(pred);
					break;
				} // if
			} // for
			kb.add(ie1);
			kb.add(ie2);
			
			return kb;
		}
}
