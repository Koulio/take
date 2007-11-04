/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package test.nz.org.take.compiler.scenario8;

import java.sql.*;


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