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


package example.nz.org.take.r2ml.eurent;

import java.io.InputStream;
import org.apache.log4j.BasicConfigurator;

import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.script.ScriptKnowledgeSource;


/**
 * Script to generate the classes.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class GenerateClasses {
		
	/**
	 * Generate the sources for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		NameGenerator nameGenerator = new DefaultNameGenerator();
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();
		
		// generate kb
		InputStream r2ml = GenerateClasses.class.getResourceAsStream("/example/nz/org/take/r2ml/eurent/DR_car_availability.xml");
		R2MLKnowledgeSource ksource = new R2MLKnowledgeSource(r2ml);
		ksource.setGenerateQuerries(true);
		
		ksource.setDatatypeMapper(new EURentDatatypeMapper());
		ksource.setSlotNameGenerator(new EURentNameMapper());
		
		compiler.setLocation(new DefaultLocation("examplesrc"));
		compiler.add(new JalopyCodeFormatter());
		compiler.setNameGenerator(nameGenerator);
		compiler.setPackageName("example.nz.org.take.r2ml.eurent.generated");
		compiler.setClassName("EURentKB");
		compiler.setAutoAnnotate(false);
		KnowledgeBase kb = ksource.getKnowledgeBase(); 
		compiler.compile(kb);
	}
}