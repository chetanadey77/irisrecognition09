package iris.database;

import java.io.*;
import java.sql.*;


public class databaseWrapper {

	    public static void main (String[] args) {

	        System.out.println( "Testing Postgres access from Java for Iris project\n" );

	        try {
	            Class.forName("org.postgresql.Driver");
	        } catch (ClassNotFoundException e) {
	            System.err.println( "Driver not found: " + e + "\n" + e.getMessage() );
	        }
	    
	        try {
	            Connection conn = DriverManager.getConnection ("jdbc:postgresql://db.doc.ic.ac.uk/",
	                                                           "ad108","CV6sDl7jxq");
	            //This will be substituted for our group id and password once CSG have set up our gr\
	oup database                                                                                     

	            Statement stmt = conn.createStatement();

	            ResultSet rs;



	            stmt.executeUpdate("INSERT into iris VALUES(2,'Sebastian');");

	            rs = stmt.executeQuery("SELECT * FROM iris");
	               while ( rs.next() ) {
	                int col1 = rs.getInt("a");
	                String col2 = rs.getString("b");
	               System.out.println(col1+","+col2);
	            }


	               conn.close();
	           } catch (Exception e) {
	               System.err.println("Exception: " + e + "\n" + e.getMessage() );
	           }

	       }

	   }





