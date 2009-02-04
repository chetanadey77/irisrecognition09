package iris.database;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.*;
import java.io.*;
import java.util.Arrays.*;
import iris.database.*;



/**
 * A class that allows access and manipulation of the central database
 * @author Seb Smith & Andrew Durnin
 * @version 1.0
 */

public class databaseWrapper {

	

	public static void main(String [] args)throws SQLException, DbException{
	
		
	    //testing the binary bitcode generation
		Integer one = 32;
		String test = Integer.toBinaryString(one);
		int length = 32-(test.length());
		String outcome = new String();
		for(;length>0; length--){
			outcome = outcome + "0";
			}
		outcome = test + outcome;
		System.out.println(outcome);
	}
	
	
	/**
	 * A class that sets up an access node to the database
	 * @author Seb Smith and Andrew Durnin
	 * @version 1.0
	 */
	
	     
		   
		   public databaseWrapper() throws DbException{
	        
			   System.out.println( "Setting up access point for Iris project\n" );

	        try {
	        Class.forName("org.postgresql.Driver");
	        } catch (ClassNotFoundException e) {
	          
	        	System.err.println( "Driver not found: " + e + "\n" + e.getMessage() );
	        }
	    
	        try {
	            conn = DriverManager.getConnection ("jdbc:postgresql://db.doc.ic.ac.uk/","g08v36205_u","6IxtbnTGoI");
	            stmt = conn.createStatement();
	        } catch (Exception e) {
	               System.err.println("Exception: " + e + "\n" + e.getMessage() );
	        }}

		   
		   
		   
		   
	       private void addLeft(String id, int code) throws SQLException{
	        
	    	 //(at the moment this is assuming that only a single integer is being passed in)
	        	
	    		String test = Integer.toBinaryString(code);
	    		int length = 32-(test.length());
	    		String outcome = new String();
	    		
	    		for(;length>0; length--){
	    			//ensuring that the leading 0s are included
	    			outcome = outcome + "0";
	    			}
	    		
	    		outcome = test + outcome;
	    		System.out.println(outcome);
	        	
	        try{
	        		//writing the 32 bit string to file
	        		BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
	    			out.write("outcome");
	    			out.close();
	        	}
		        	      
	        catch(FileNotFoundException ex)
	        {
	        	System.out.println("FileNotFoundException : " + ex);
	        }
	        
	        catch(IOException ioe)
	        {
	        	System.out.println("IOException : " + ioe);
	        }
	        	stmt.executeUpdate("UPDATE iris SET l ='output.txt' WHERE id ='" + id + "';" ); 
	        }
	        
		        
	       
	       
	        private void addRight(String id, int code) throws SQLException{
	        	
	        	//(at the moment this is assuming that only a single integer is being passed in)
	        	
	    		String test = Integer.toBinaryString(code);
	    		int length = 32-(test.length());
	    		String outcome = new String();
	    		
	    		for(;length>0; length--){
	    			//ensuring that the leading 0s are included
	    			outcome = outcome + "0";
	    			}
	    		
	    		outcome = test + outcome;
	    		System.out.println(outcome);
	        	
	        try{
	        		//writing the 32 bit string to file
	        		BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
	    			out.write("outcome");
	    			out.close();
	        	}
		        	      
	        catch(FileNotFoundException ex)
	        {
	        	System.out.println("FileNotFoundException : " + ex);
	        }
	        
	        catch(IOException ioe)
	        {
	        	System.out.println("IOException : " + ioe);
	        }
	        	stmt.executeUpdate("UPDATE iris SET r ='output.txt' WHERE id ='" + id + "';" ); 
	        }
	        
	        
	     
	        
	        
	        
	         private void addId(String id) throws SQLException{
	        	
	        	stmt.executeUpdate("INSERT into iris (id) VALUES('" + id + "');");
	        }
	        
	        private class contents{ 
	        
	        	
	        	public contents() throws SQLException{
		        
	            rs = stmt.executeQuery("SELECT * FROM iris");
	        	
	        	}
	        
	        	private void Next(Collection left, Collection right, String Id) throws SQLException {
	        		
	        		
	        		if(rs.next()){
	        			
	        			String L = rs.getString("l");
		                String R = rs.getString("r"); 
	        			Id = rs.getString("id");
	        			
	        		}
	        		
	        		//code to transfer bitstring to ArrayList or some such
	        		//32 bit integers = 64 index array?
	        	}
	        	
	        	private ResultSet rs;
	        	
	        }
	        
	        
	        
	        private Connection conn;
	        private Statement stmt;
	   
	        
	            
	            
	   public class DbException extends Exception{}

	   }


	   





