package iris.database;

import iris.bitcodeMatcher.BitCode;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	 * @throws DbException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DbException, SQLException, IOException{
		
		
		databaseWrapper test = new databaseWrapper();
		byte[] insert = new byte[200];
		insert[100] = 32;
		System.out.println(insert.toString());
		test.addRightByte(insert, "sx008");
		ResultSet rs = stmt.executeQuery("SELECT * FROM iris");
    	rs.next();
    	rs.next();
    	byte[] result = rs.getBytes("rig");
		System.out.println(Arrays.toString(result));
	
	}

			     
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
			 * A method that converts a bitcode to a byte array
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 * @return byte[]
			 */
		   
			public static byte[] toByteArray(BitCode bitcode){
				
				byte[] result = new byte[bitcode.length()/8+1];
				for(int i=0; i<bitcode.length(); i++){
					
					if  (bitcode.get(i))
						result[result.length-i/8-1] |= 1<<(i%8);
					
				}
				
				return result;
				
			}
			
			/**
			 * A method that converts a byte array to a bitcode
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 * @return BitCode
			 */
		   
			
			public static BitCode toBitCode(byte[] stored){
				
				BitCode bitcode = new BitCode(stored.length);
				
				for(int i=0; i<stored.length*8; i++){
					
					if((stored[stored.length-i/8-1]&(1<<(i%8))) > 0)
						bitcode.set(i);
					
				}
				
				return bitcode;
				
			}
			
			
			
			/**
			 * A method that allows a user to add a bitcode to the database for the left iris using the Array format.
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
			 * A method that allows a user to add a bitcode to the database for the left iris using the byte array format.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
		 * @throws SQLException 
			 */
		   
		   private void addLeft(String id, byte[] bitcode) throws SQLException{
			   
			   
	        	 PreparedStatement ps = conn.prepareStatement("UPDATE iris SET l = ? WHERE id = '" + id + "'");
	        	 ps.setBytes(1, bitcode);
	        	 ps.executeUpdate();
	        	 ps.close();
			   
		   }
		   
		   
		   /**
			 * A method that allows a user to add a bitcode to the database for the left iris using the BitCode class.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
		 * @throws SQLException 
			 */
		   
		   
		   private void addLeft(String id, BitCode bitcode) throws SQLException{
			   
			   byte[] insert = this.toByteArray(bitcode);
			   PreparedStatement ps = conn.prepareStatement("UPDATE iris SET l = ? WHERE id = '" + id + "'");
	        	 ps.setBytes(1, insert);
	        	 ps.executeUpdate();
	        	 ps.close();
			   
			   
			   
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
			 * A method that allows a user to add a bitcode to the database for the right iris using the byte array format.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 */
	        
	        
	        private void addRight(byte[] insert, String id) throws SQLException{
	        	 
	        	 
	        	 
	        	 PreparedStatement ps = conn.prepareStatement("UPDATE iris SET rig = ? WHERE id = '" + id + "'");
	        	 ps.setBytes(1, insert);
	        	 ps.executeUpdate();
	        	 ps.close();
	        	 
	        	 
	        	 
	         }
	        
	        /**
			 * A method that allows a user to add a bitcode to the database for the right iris using the BitCode Class.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 */
	        
	        private void addRight(String id, byte[] bitcode) throws SQLException{
				   
				   
	        	 PreparedStatement ps = conn.prepareStatement("UPDATE iris SET r = ? WHERE id = '" + id + "'");
	        	 ps.setBytes(1, bitcode);
	        	 ps.executeUpdate();
	        	 ps.close();
	        	 
	        }
	        
	        /**
			 * A method that allows a user to add a new row to the database with Id as primary key.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
	         * @throws SQLException 
			 */
	        
	         private void addRecord(String id, byte[] left, byte[] right) throws SQLException{
	        	 
	        	 PreparedStatement ps = conn.prepareStatement("INSERT INTO iris (id)(l)(r) VALUES("+id+",?,?)");
	        	 ps.setBytes(1, left);
	        	 ps.setBytes(2,right);
	        	 ps.executeUpdate();
	        	 ps.close();
	        	
	        	 
	        	  }
	        
	         private void addId(String id) throws SQLException{
	        	
	        	stmt.executeUpdate("INSERT INTO iris (id) VALUES('" + id + "');");
	        }
	         
	         /**
				 * A method that returns the id and left and right bitcodes of the next database record in byte array format.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
				 */
	         
	        
	         private String getNext(byte[] left, byte[] right) throws SQLException, IOException {
	     		
	        	ResultSet rs = stmt.executeQuery("SELECT * FROM iris");
	        	String id = new String();
	     		
	     		
	     		if(rs.next()){
	     			id = rs.getString("id");
	     			left = rs.getBytes("l");
	     			right = rs.getBytes("r");
	     			}
	     		
	     		else return null;
	 			
	             
	             return id;
	 	        	 
	     	
	 			
	         }
	         
	         private ResultSet getAll() throws SQLException{
	        	 
	        	ResultSet rs = stmt.executeQuery("SELECT * FROM iris");
	        	 
				return rs; 
	        	 
	        	 
	        	 }
	         
	         
	         
	        
	        
	        
	        private static Connection conn;
	        private static Statement stmt;
	   
	        
	            
	            
	   public class DbException extends Exception{}

	   }


	   





