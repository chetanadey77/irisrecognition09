package iris.database;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.junit.Test;



/**
 * A class that allows access and manipulation of the central database
 * @author Seb Smith & Andrew Durnin
 * @version 1.0
 */

public class databaseWrapper {

	
	/**
	 * A class that sets up an access node to the database
	 * @author Seb Smith and Andrew Durnin
	 * @version 1.0
	 * @return 
	 * @throws DbException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	

			     
			public databaseWrapper() throws DbException{
	        
			   System.out.println( "Setting up access point for Iris project\n" );

	        try {
	        Class.forName("org.postgresql.Driver");
	        } catch (ClassNotFoundException e) {
	          
	        	System.err.println( "Driver not found: " + e + "\n" + e.getMessage() );
	        }
	    
	        try {
	            conn = DriverManager.getConnection ("jdbc:postgresql://localhost:1432","g08v36205_u","6IxtbnTGoI");
	            stmt = conn.createStatement();
	        } catch (Exception e) {
	               System.err.println("Exception: " + e + "\n" + e.getMessage() );
	        }}

			/**
			 * A method that allows a user to add a bitcode to the database for the left iris.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 */
		   
		   @Test private void addLeft(String id, int[] code) throws SQLException, IOException{
	        
	    	String insert = new String();
	    	int count;
	    	
            for(count  =0; count < code.length; count++){

                if(count == 0)
                    insert = insert + code[count];
                else
                    insert = insert +"," + code[count];
            }

            stmt.executeUpdate("UPDATE iris SET l = '{" + insert + "}' WHERE id = '" + id + "'");             
	    	org.junit.Assert.assertFalse(count==0);
		   }
		        
		   /**
			 * A method that allows a user to add a bitcode to the database for the right iris.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 */
	       
	        @Test private void addRight(String id, int[] code) throws SQLException{
	        	
	        	String insert = new String();
	        	int count;
	        	
	            for(count =0; count < code.length; count++){

	                if(count == 0)
	                    insert = insert + code[count];
	                else
	                    insert = insert +"," + code[count];
	            }

	            stmt.executeUpdate("UPDATE iris SET r = '{" + insert + "}' WHERE id = '" + id + "'");             
		    	org.junit.Assert.assertFalse(count == 0);
	        	
	        }
	        
	        /**
			 * A method that allows a user to add a new row to the database with Id as primary key.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
	         * @throws SQLException 
			 */
	        
	         private void addRecord(String id, int[] left, int right[]) throws SQLException{
	        	 
	        	 String insert_l = new String();
		        	int count;
		        	
		            for(count =0; count < left.length; count++){

		                if(count == 0)
		                    insert_l = insert_l + left[count];
		                else
		                    insert_l = insert_l +"," + left[count];
		            }
		         	 
		            String insert_r = new String();
			        	int count_2;
			        	
			            for(count =0; count < right.length; count++){

			                if(count == 0)
			                    insert_r = insert_r + right[count];
			                else
			                    insert_r = insert_r +"," + right[count];
			            }
		        	   	 
	        	 
	        	 
	        	 stmt.executeUpdate("INSERT into iris (id)(left)(right)VALUES('" + id + "', '{" + insert_l + "}', '{" + insert_r + "}')");
	         }
	        
	         private void addId(String id) throws SQLException{
	        	
	        	stmt.executeUpdate("INSERT into iris (id) VALUES('" + id + "');");
	        }
	         
	         /**
				 * A method that returns the id and left and right bitcodes of the next database record.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
				 */
	         
	        
	         private String getNext(Integer[] left, Integer[] right) throws SQLException, IOException {
	     		
	        	ResultSet rs = stmt.executeQuery("SELECT * FROM iris");
	        	String id = new String();
	     		Array leftiris;
	     		Array rightiris;
	     		
	     		
	     		if(rs.next()){
	     			id = rs.getString("id");
	     			leftiris = rs.getArray("l");
	     			rightiris = rs.getArray("r");
	     			}
	     		
	     		else return null;
	 			
	             left = (Integer[])leftiris.getArray();
	             right = (Integer[])rightiris.getArray();
	     		
	             return id;
	 	        	 
	     	
	 			
	     	}
	         
	        
	        
	        
	        private static Connection conn;
	        private static Statement stmt;
	   
	        
	            
	            
	   public class DbException extends Exception{}

	   }


	   





