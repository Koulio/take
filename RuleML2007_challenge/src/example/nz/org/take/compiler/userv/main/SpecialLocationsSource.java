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

package example.nz.org.take.compiler.userv.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import nz.org.take.rt.EmptyIterator;
import nz.org.take.rt.ResourceIterator;
import nz.org.take.rt.SingletonIterator;
import example.nz.org.take.compiler.userv.domainmodel.Driver;
import example.nz.org.take.compiler.userv.spec.ExternalFactStore4specialLocation;
import example.nz.org.take.compiler.userv.spec.specialLocation;

/**
 * External data source for special locations records.
 * Based on a built-in RDBMS.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class SpecialLocationsSource implements
		ExternalFactStore4specialLocation {

	public SpecialLocationsSource() {
		super();
		try {
			Class.forName("org.hsqldb.jdbcDriver" );
			// try to setup db
			try {
				Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:data/locations/riskylocations", "sa", "");
				Statement statement = connection.createStatement();
				statement.execute("CREATE TEXT TABLE locations (loc VARCHAR(20),PRIMARY KEY(loc))");
				statement.execute("SET TABLE locations SOURCE \"riskylocations.csv\"");
			}
			catch (Exception x) {
				// assume table already exists
			}
		}
		catch (Exception x) {
			throw new RuntimeException("Cannot find JDBC driver class",x);
		}
	}
	
	public static void main(String[] args) throws Exception {
		new SpecialLocationsSource();
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		connection = DriverManager.getConnection("jdbc:hsqldb:file:data/locations/riskylocations", "sa", "");
		statement = connection.createStatement();
		String query = "SELECT * FROM locations WHERE loc='CA'";
		result = statement.executeQuery(query);
		result.next();
		System.out.println(result.getString("loc"));
		result.close();
		connection.close();

	}

	@Override
	public ResourceIterator<specialLocation> fetch(Driver driver) {
		Connection connection = null;
		Statement statement = null;
		boolean isInRiskyLocation = false;
		ResultSet result = null;
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:file:data/locations/riskylocations", "sa", "");
			statement = connection.createStatement();
			String query = "SELECT * FROM locations WHERE loc='"+driver.getLocation()+"'";
			System.out.println("query db: " + query);
			result = statement.executeQuery(query);
			isInRiskyLocation = result.next();
			// System.out.println("the result is " + isInRiskyLocation);
			result.close();
			connection.close();
		}
		catch (Exception x) {
			x.printStackTrace();
		}

		if (isInRiskyLocation) {
			specialLocation loc = new specialLocation();
			loc.slot1 = driver;
			return new SingletonIterator(loc);	
		}
		else {
			return EmptyIterator.DEFAULT;
		}
	}

}
