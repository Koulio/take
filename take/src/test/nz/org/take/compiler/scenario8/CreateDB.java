package test.nz.org.take.compiler.scenario8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CreateDB {
	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver" );
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:testdata/example8/example8db", "sa", "");
		Statement statement = connection.createStatement();
		statement.execute("CREATE TABLE people (son VARCHAR(20),father VARCHAR(20),PRIMARY KEY(son))");
		System.out.println("table people created");
		PreparedStatement pStatement = connection.prepareStatement("insert into people values (?,?)");
		addRecord("Frank","Lutz",pStatement);		
        addRecord("Guenther","Otto",pStatement);
        addRecord("Jens","Klaus",pStatement);
        addRecord("Lutz","Otto",pStatement);
        addRecord("Klaus","Otto",pStatement);
        addRecord("Max","Jens",pStatement);
        addRecord("Ralf","Lutz",pStatement);
        addRecord("Werner","Otto",pStatement);
	                                        
		connection.close();                                                                                                       
	}
	private static void addRecord(String p1,String p2,PreparedStatement s) throws Exception {
		s.clearParameters();
		s.setObject(1,p1);
		s.setObject(2,p2);
		System.out.println("added record ["+p1+","+p2+"]" );

	}
}
