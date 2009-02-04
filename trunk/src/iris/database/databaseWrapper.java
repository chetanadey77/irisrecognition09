package iris.database;

import java.sql.*;
import java.util.Collection;
import org.junit.*;
import java.io.*;

/**
 * A class that allows access and manipulation of the central database
 * @author Seb Smith
 * @version 1.0
 */

public class databaseWrapper {

	public static void main(String [] args){}
	
	/**
	 * A class that sets up an access node to the database
	 * @author Seb Smith
	 * @version 1.0
	 */
	
	   public class dbAccess {
		   
		   
		   public dbAccess() throws DbException{
	        
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

		   
		   
		   
		   
	       private void addLeft(String id, int[]code) throws SQLException{
	        
	        	String holder = code.toString();
	        	stmt.executeUpdate("UPDATE iris SET l ='" + holder +"' WHERE id ='" + id + "';" );
	        }
	 
	       
	       
	        private void addRight(String id, Collection code) throws SQLException{
	        
	        	String holder = code.toString();
	        	String strFilePath = new String; 
	        	strFilePath = //RAM memory to save file
	        		
	        	try
	        	{
	        	FileOutputStream fos = new FileOutputStream(strFilePath);
	        	String strContent = holder;
	        
	        	  /*
	        	  * This method writes given byte array to a file.
	        	  */
	        	
	        	fos.write(strContent.getBytes());
	        	
	        	  /*
	        	  * Close FileOutputStream using,
	        	  * void close() method of Java FileOutputStream class.
	        	  */
	        	fos.close(); 
	        }
		        	      
	        catch(FileNotFoundException ex)
	        {
	        
	        System.out.println("FileNotFoundException : " + ex);
	        }
	        
	        catch(IOException ioe)
	        
	        {
	       
	        System.out.println("IOException : " + ioe);
	        
	        	stmt.executeUpdate("UPDATE iris SET r ='" + /*filename*/ +"' WHERE id ='" + id + "';" );
	        
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
	   }
	        
	            
	            
	   public class DbException extends Exception{}

	   }


	   





