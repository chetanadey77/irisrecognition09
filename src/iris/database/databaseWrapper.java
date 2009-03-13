package iris.database;

import iris.imageToBitcode.BitCode;

import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.BitSet;

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
	 * @throws DbException 
	 * @throws IOException 
	 * @throws SQLException  
	 */
	

			     
			public databaseWrapper() throws DbException, SQLException{
	        
			   System.out.println( "Setting up access point for Iris project");

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
	        }
	        
	        rs = stmt.executeQuery("SELECT * FROM iris");
	        System.out.println("Access Point successfully created");
        	
			
			}

			/**
			 * A method that converts a BitCode object to a byte array
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
			 * A method that converts a byte array to a BitCode object
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 * @return BitCode
			 */
		   
			
			public static BitCode toBitCode(byte[] stored){
				
				BitCode bitcode = new BitCode(2048);
				
				for(int i=0; i<stored.length*8; i++){
					
					if((stored[stored.length-i/8-1]&(1<<(i%8))) > 0)
						bitcode.addBit(1);
					else bitcode.addBit(0);
					
				}
				
				return bitcode;
				
			}
			
		
			
			private Blob bitcodeToBlob(BitCode bitcode) {  
			    
				byte[] byteArray = toByteArray(bitcode);  
			    
				//Blob blob = conn.createBlob(); 
			    
				//blob.setBytes(1, byteArray);  
			  
			    return null;  
			}  
			
			
			/**
			 * A method that allows a user to add a bitcode to the database for the left iris using the Array format.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 */
			
			
		   
		    public void addLeft(String id, BitCode bitcode) throws SQLException, IOException{
	        
			byte[] code = this.toByteArray(bitcode);
	    	String insert = new String();
	    	int count;
	    	
            for(count  =0; count < code.length; count++){

                if(count == 0)
                    insert = insert + code[count];
                else
                    insert = insert +"," + code[count];
            }

            stmt.executeUpdate("UPDATE iris SET l = '{" + insert + "}' WHERE id = '" + id + "'");             
	    	//org.junit.Assert.assertFalse(count==0);
		   
		   }
		   
		   /**
			 * A method that allows a user to add a bitcode to the database for the left iris using the byte array format.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 * @throws SQLException 
			 */
		   /*
		   public void addLeft(String id, byte[] bitcode) throws SQLException{
			   
			   
	        	 
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
		   
		   /*
		   public void addLeft(String id, BitCode bitcode) throws SQLException{
			   
			   byte[] insert = this.toByteArray(bitcode);
			   PreparedStatement ps = conn.prepareStatement("UPDATE iris SET l = ? WHERE id = '" + id + "'");
	        	 ps.setBytes(1, insert);
	        	 ps.executeUpdate();
	        	 ps.close();
			   
			   
			   }
		   */
		        
		   /**
			 * A method that allows a user to add a bitcode to the database for the right iris.
			 * @author Seb Smith & Andrew Durnin
			 * @version 1.0
			 */
	       
		    public void addRight(String id, BitCode bitcode) throws SQLException, IOException{
		        
				byte[] code = this.toByteArray(bitcode);
		    	String insert = new String();
		    	int count;
		    	
	            for(count  =0; count < code.length; count++){

	                if(count == 0)
	                    insert = insert + code[count];
	                else
	                    insert = insert +"," + code[count];
	            }

	            stmt.executeUpdate("UPDATE iris SET r = '{" + insert + "}' WHERE id = '" + id + "'");             
		    	//org.junit.Assert.assertFalse(count==0);
			   
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
	        	 
	        	 PreparedStatement ps = conn.prepareStatement("INSERT INTO iris (id,l,r) VALUES('"+id+"',?,?);");
	        	 ps.setBytes(1, left);
	        	 ps.setBytes(2,right);
	        	 ps.executeUpdate();
	        	 ps.close();
	        	
	        	 
	        	  }
	        
	         /**
				 * A method that allows a user to add a new Id to the database.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
		         * @throws SQLException 
				 */
		        
	         public void addId(String id) throws SQLException{
	        	
	        	stmt.executeUpdate("INSERT INTO iris (id,acc) VALUES('" + id + "', true);");
	        }
	         
	         /**
				 * A method that allows the retreival of the left iris bitcode for a given id,returned as a byte array.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
		         * @throws SQLException 
		         * @return byte[]
				 */
	         
	         private byte[] getLeftArray(String id) throws SQLException{
				

	        	 byte[] result = rs.getBytes("l");
	        	 id = rs.getString("id");
	        	 
	        	 return result;
	        	 
	        	 
	            }
	         
	         

	         /**
				 * A method that allows the retreival of the right iris bitcode for a given id, returned as a byte array.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
		         * @throws SQLException 
		         * @return byte[]
				 */
	    
	         private  byte[] getRightArray(String id) throws SQLException{
	         

	        	 
	        	 byte[] result = rs.getBytes("r");
	        	 id = rs.getString("id");
        	 
	        	 return result;
        	 
        	 
            }
	         
	         /**
				 * A method that allows the retreival of the left iris bitcode for a given id,returned as a BitCode object.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
		         * @throws SQLException 
		         * @return BitCode
				 */
    
	        
	         public BitCode getLeftCode() throws SQLException{
					
	        	 Array r;
	        	 r = rs.getArray("l");
	        	 Integer[] result = (Integer[])r.getArray();
	        	 
	        	 //byte[] result_array = rs.getBytes("l");
	        	// id = rs.getString("id");
	        	 
	        	 byte[] bitarray = new byte[result.length];
	        	 for(int i = 0; i<result.length;i++){
	        		 
	        		 bitarray[i] = result[i].byteValue();
	        		 
	        	 }
	        	 
	        	 System.out.println(Arrays.toString(bitarray));
	        	 
	        	 BitCode bitcode = this.toBitCode(bitarray);
	        	 
	        	 return bitcode;
	        	 
	        	 
	            }
	         
	         /**
				 * A method that allows the retreival of the right iris bitcode for a given id,returned as a BitCode object.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
		         * @throws SQLException 
		         * @return BitCode
				 */
	         
	         
	         
	         
	         public BitCode getRightCode() throws SQLException{
					
	        	 Array r;
	        	 r = rs.getArray("r");
	        	 Integer[] result = (Integer[])r.getArray();
	        	 
	        	 //byte[] result_array = rs.getBytes("l");
	        	// id = rs.getString("id");
	        	 
	        	 byte[] bitarray = new byte[result.length];
	        	 for(int i = 0; i<result.length;i++){
	        		 
	        		 bitarray[i] = result[i].byteValue();
	        		 
	        	 }
	        	 
	        	 System.out.println(Arrays.toString(bitarray));
	        	 
	        	 BitCode bitcode = this.toBitCode(bitarray);
	        	 
	        	 return bitcode;
	        	 }
	         
	         
	         public String getId() throws SQLException{
	        	 
	        	 String id = rs.getString("id");
	        	 return id;
	        	 
	         }
	         
	         public Boolean getAccess() throws SQLException{
	        	 
	        	 Boolean result = rs.getBoolean("acc");
	        	 return result;
	        	 
	        	 
	         }
	         
	         /**
				 * A method that returns a fresh resultset of all records held in the database.
				 * @author Seb Smith & Andrew Durnin
				 * @version 1.0
		         * @throws SQLException 
		         * @return BitCode
				 */
	         
	         
	         private ResultSet getNewSet() throws SQLException{
	        	 
	        	ResultSet rset = stmt.executeQuery("SELECT * FROM iris");
	        	 
				return rset; 
	        	 
	        	 
	        	 }
	         
	         public void DeleteOne(String id) throws SQLException{
	        	 
	        	 stmt.executeUpdate("DELETE FROM iris WHERE id ='" +id + "'");
	        	 
	        	 
	         }
	         
	         public Boolean DeleteAll() throws SQLException{
	        	 
	        	 stmt.executeUpdate("DELETE FROM iris");
	        	 return true;
	        	 
	         }
	         
	         public void setAccess(String id, Boolean value) throws SQLException{
	        	 
	        	 stmt.executeUpdate("UPDATE iris SET acc = " + value + " WHERE id = '" + id + "'");
	        	 
	        	 
	         }
	         
	        
	        
	        public ResultSet rs;
	        private static Connection conn;
	        private static Statement stmt;
	   
	        
	            
	            
	   public class DbException extends Exception{}

	   }


	   





