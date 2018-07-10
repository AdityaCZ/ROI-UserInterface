package backendCalculations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class concheck {

	public static void main(String[] args) throws SQLException {
		Connection con=null;
		
        try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        
        //URL="jdbc:hsqldb:hsql://localhost/testdb", User=SA, Password="", 
        
        con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", ""); 
        
		//Table Creation
    	 Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        	    	     
        /**
         * to commit all changes when data is inserted into all tables
         */
         con.setAutoCommit(false);

         stmt = con.createStatement();
         
         
         
         
         System.out.println("one");
         
         
         Connection con1=null;
 		
         try {
 			Class.forName("org.hsqldb.jdbc.JDBCDriver");
 		} catch (ClassNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}   
         System.out.println("tweo");
         
         //URL="jdbc:hsqldb:hsql://localhost/testdb", User=SA, Password="", 
         
         con1 = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", ""); 
         System.out.println("three");
 		//Table Creation
     	 Statement stmt1 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
         	    	     
         /**
          * to commit all changes when data is inserted into all tables
          */
     	System.out.println("cac");
          con1.setAutoCommit(false);
          System.out.println("xcv");
          stmt = con1.createStatement();
          System.out.println("ZZZZZ");
          con.commit();
          System.out.println("qqqqqq");
          con1.commit();
	}
}
