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
 * Script to test the DB and the settings used in the example.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class TestDB   {

	
	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver" );
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:testdata/example8/example8db", "sa", "");
		Statement statement = connection.createStatement();
		try{
			statement.execute("CREATE TEXT TABLE people (son VARCHAR(20),father VARCHAR(20),PRIMARY KEY(son))");
			statement.execute("SET TABLE people SOURCE \"people.csv\"");
		}
		catch (Exception x) {
			System.out.println("Cannot create table - it probably already exists");
		}
		ResultSet rs = statement.executeQuery("SELECT * FROM people");
		while (rs.next()) {
			System.out.print(rs.getString(1));
			System.out.print(" - ");
			System.out.println(rs.getString(2));
		}
		rs.close();
		connection.close();
		System.exit(0);
		
	}
	
	

}