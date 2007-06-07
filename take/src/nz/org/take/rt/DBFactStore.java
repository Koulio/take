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


package nz.org.take.rt;

import java.sql.*;
import java.util.Iterator;


/**
 * Abstract superclass for facts stores based on databases accessed using JDBC.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class DBFactStore implements ExternalFactStore {

	
	public DBFactStore() {
		super();
		try {
			Class.forName(getDriverClass());
		}
		catch (Exception x) {
			throw new RuntimeException("Cannot find JDBC driver class",x);
		}
	}
	public RecordIterator getFacts() {
		Connection connection = null;
		Statement statement = null;;
		java.sql.ResultSet result = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			statement.execute("CREATE TEXT TABLE people (son VARCHAR(20),father VARCHAR(20),PRIMARY KEY(son))");
			statement.execute("SET TABLE people SOURCE \"people.csv\"");
			preQueryDo(connection);
			result = statement.executeQuery(buildQuery());
			postQueryDo(connection);
		}
		catch (Exception x) {
			throw new RuntimeException(x);
		}
		final java.sql.ResultSet rs = result;
		final Connection con = connection;
		RecordIterator iter = new RecordIterator() {
			public boolean hasNext() {
				try{
					return rs.next();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			public Record next() {
				try {
					return buildRecord(rs);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			public void remove() {
				throw new UnsupportedOperationException();				
			}
			public void close() {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					try {
						dispose(con);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
		} ;

		return iter;
	}
	public Iterator iterator() {
		return getFacts();
	}
	
	/**
	 * Get the JDBC driver class used.
	 * @return the name of the driver class
	 */
	public abstract String getDriverClass() ;
	/**
	 * Get a jdbc connection.
	 * @return a connection
	 */
	public abstract Connection getConnection() throws SQLException  ;
	/**
	 * Dispose a jdbc connection.
	 * @param con the connection to dispose (or to recycle)
	 */
	public abstract void dispose(Connection con) throws SQLException ;
	/**
	 * Get the SQL query.
	 * @return a query
	 */
	public abstract String buildQuery() ;
	/**
	 * Build a record from the current row in the result set.
	 * @param rs a db result set
	 * @return a record
	 */
	public abstract Record buildRecord(java.sql.ResultSet rs) throws SQLException ;
	/**
	 * Prepare a query. E.g., can execute additional SQL commands to set the database or
	 * default schema etc.
	 * By default, do nothing.
	 * @param con
	 * @throws SQLException
	 */
	public void preQueryDo(Connection con) throws SQLException {}
	/**
	 * Executated after the query has been fired. E.g., can execute additional SQL commands to reset the database or
	 * default schema etc.
	 * By default, do nothing.
	 * @param con
	 * @throws SQLException
	 */
	public void postQueryDo(Connection con) throws SQLException {}
	

}