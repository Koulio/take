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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import nz.org.take.AbstractAnnotatable;
import nz.org.take.ExternalFactStore;
import nz.org.take.ExternalFactStoreException;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeBaseVisitor;
import nz.org.take.Predicate;
import nz.org.take.Record;
import nz.org.take.RecordIterator;
import nz.org.take.SimplePredicate;
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
	
	class FamilyRecord implements Record {
		private Person person1 = null;
		private Person person2 = null;
		private static Predicate predicate = newe SimplePredicate("father",new Class[]{Person.class,Person.class}); 
		FamilyRecord(String p1,String p2) {
			super();
			person1 = new Person(p1);
			person2 = new Person(p2);
		}
		public Object getObject(int pos) throws ExternalFactStoreException {
			if (pos==0) 
				return person1;
			else
				return person2;
		}

		public SimplePredicate getPredicate() {
			return null;
		}
		
	};
	
	
	/**
	 * Generate the interface for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		

	}

	public RecordIterator getKnowledge() {
		Connection connection = null;
		Statement statement = null;;
		ResultSet result = null;
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:file:testdata/example8/example8db", "sa", "");
			statement = connection.createStatement();
			result = statement.executeQuery("select son,father from people");
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		
		// read data into memory buffer 
		final ResultSet rs = result;
		RecordIterator iter = new RecordIterator() {
			

			public boolean hasNext() {
				return rs.next();
			}

			public Record next() {
				
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