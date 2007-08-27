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

import test.nz.org.take.compiler.scenario8.generated.ExternalFactStore4IsFatherOf;
import test.nz.org.take.compiler.scenario8.generated.IsFatherOf;
import nz.org.take.rt.ResourceIterator;


/**
 * Example fact store.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class FactStore implements ExternalFactStore4IsFatherOf  {

	private static Map<String,Person> peopleByName = new HashMap<String,Person>();
	
	public FactStore() {
		super();
		try {
			Class.forName("org.hsqldb.jdbcDriver" );
		}
		catch (Exception x) {
			throw new RuntimeException("Cannot find JDBC driver class",x);
		}
	}
	/**
	 * Generate the interface for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ResourceIterator<IsFatherOf> records = new FactStore().fetch(null,null);
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
	 public ResourceIterator<IsFatherOf> fetch(final Person son,final Person father) {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:file:testdata/example8/example8db", "sa", "");
			statement = connection.createStatement();
			try {
				statement.execute("CREATE TEXT TABLE people (son VARCHAR(20),father VARCHAR(20),PRIMARY KEY(son))");
				statement.execute("SET TABLE people SOURCE \"people.csv\"");
			}
			catch (Exception x) {
				// assume table already exists
			}
			String query = null;
			if (son==null && father==null)
				query = "SELECT * FROM people";
			if (son!=null && father==null)
				query = "SELECT * FROM people WHERE son='"+son.getName()+"'";
			if (father==null && father!=null)
				query = "SELECT * FROM people WHERE father='"+father.getName()+"'";
			if (son!=null && father!=null)
				query = "SELECT * FROM people WHERE son='"+son.getName()+"' AND father='"+father.getName()+"'";
			result = statement.executeQuery(query);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		final ResultSet rs = result;
		ResourceIterator<IsFatherOf> iter = new ResourceIterator<IsFatherOf>() {
			int NEXT_AVAILABLE = 0;
			int NEXT_CONSUMED = 1;
			int status = NEXT_CONSUMED;
			
			public boolean hasNext() {
				if (status==NEXT_AVAILABLE)
					return true;
				try{
					boolean next = rs.next();
					//System.out.println("has next");
					status = NEXT_AVAILABLE;
					return next;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			public IsFatherOf next() {
				try {
					//System.out.println("try next");
					IsFatherOf isFatherOf = new IsFatherOf(
						getOrAddPerson(rs.getString("son")),
						getOrAddPerson(rs.getString("father"))
					);
					//System.out.println("next: " + isFatherOf.son.getName() + " " + isFatherOf.father.getName());
					status = NEXT_CONSUMED;
					return isFatherOf;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			public void remove() {
				throw new UnsupportedOperationException();				
			}
			public void close() {
				System.out.println("closing external fact set");
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		} ;

		return iter;
	}


}