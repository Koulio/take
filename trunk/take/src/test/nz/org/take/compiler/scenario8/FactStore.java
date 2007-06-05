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


package test.nz.org.take.compiler.scenario8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import nz.org.take.AbstractAnnotatable;
import nz.org.take.ExternalFactStore;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeBaseVisitor;
import nz.org.take.Predicate;
import nz.org.take.Record;
import nz.org.take.RecordIterator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.script.ScriptException;
import nz.org.take.script.ScriptKnowledgeSource;
import java.io.*;


/**
 * Example fact store.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class FactStore extends AbstractAnnotatable implements ExternalFactStore {

	private String id = null;
	/**
	 * Generate the interface for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		

	}

	public RecordIterator getKnowledge() {
		// read data into memory buffer 
		InputStream factSrc = this.getClass().getResourceAsStream("/test/nz/org/take/compiler/scenario8/facts.txt");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(factSrc));
		final List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line=reader.readLine())!=null) {
			lines.add(line);
		}
		try {
			reader.close();
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		
		RecordIterator iter = new RecordIterator() {
			private int cursor = -1;
			public void close() {}

			public boolean hasNext() {
				return cursor<lines.size()-1;
			}

			public Record next() {
				cursor=cursor+1;
				String line = lines.get(cursor); 
				return buildRecord(line);
			}

			public void remove() {
				throw new UnsupportedOperationException();				
			}
			
		} ;

		return iter;
	}

	protected Record buildRecord(String line) {
		
		return null;
	}

	public Predicate getPredicate() {
		return null;
	}

	public String getId() {
		return id;
	}

	public void accept(KnowledgeBaseVisitor visitor) {
		
		
	}

}