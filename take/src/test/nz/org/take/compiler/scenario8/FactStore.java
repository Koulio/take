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
import java.util.HashMap;
import java.util.Map;
import nz.org.take.SimplePredicate;
import nz.org.take.rt.DBFactStore;
import nz.org.take.rt.ExternalFactStoreException;
import nz.org.take.rt.Record;
import nz.org.take.rt.RecordIterator;


/**
 * Example fact store.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class FactStore extends DBFactStore {

	private SimplePredicate predicate = null;
	private static Map<String,Person> peopleByName = new HashMap<String,Person>();
	
	class FamilyRecord implements Record {
		private Person person1 = null;
		private Person person2 = null;
		FamilyRecord(Person p1,Person p2) {
			super();
			person1 = p1;
			person2 = p2;
		}
		public Object getObject(int pos) throws ExternalFactStoreException {
			if (pos==0) 
				return person1;
			else
				return person2;
		}

		public SimplePredicate getPredicate() {
			return FactStore.this.getPredicate();
		}
		public String toString() {
			return "father["+person1.getName()+","+person2.getName()+"]";
		}
		
	};
	
	
	public FactStore() {
		super();
	}
	/**
	 * Generate the interface for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		RecordIterator records = new FactStore().getFacts();
		while (records.hasNext())
			System.out.println(records.next());

	}
	private Person getOrAddPerson(String name) {
		Person p = peopleByName.get(name);
		if (p==null) {
			p=new Person(name);
			peopleByName.put(name,p);
		}
		return p;
	}
	public void preQueryDo(Connection con) throws SQLException {
		try {
			Statement statement = con.createStatement();
			statement.execute("CREATE TEXT TABLE people (son VARCHAR(20),father VARCHAR(20),PRIMARY KEY(son))");
			statement.execute("SET TABLE people SOURCE \"people.csv\"");
			statement.close();
		}
		catch (Exception x) {
			// exception is thrown if table already exists
		}
	}

	public SimplePredicate getPredicate() {
		if (predicate==null) {
			predicate = new SimplePredicate();
			predicate.setName("is_father_of");
			predicate.setSlotTypes(new Class[]{Person.class,Person.class});
			predicate.setSlotNames(new String[]{"son","father"});
		}
		return predicate;
	}
	@Override
	public String buildQuery() {
		return "SELECT * FROM people";
	}
	@Override
	public Record buildRecord(ResultSet rs) throws SQLException {
		return new FamilyRecord(
				getOrAddPerson(rs.getString("son")),
				getOrAddPerson(rs.getString("father"))
			);
	}
	@Override
	public void dispose(Connection con) throws SQLException {
		con.close();
		
	}
	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:file:testdata/example8/example8db", "sa", "");
	}
	@Override
	public String getDriverClass() {
		return "org.hsqldb.jdbcDriver";
	}

}