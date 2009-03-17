package iris.database;

import iris.imageToBitcode.BitCode;

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
 * A class that sets up an access node to the database
 * @author Seb Smith and Andrew Durnin
 * @version 1.0
 * @throws DbException 
 * @throws IOException 
 * @throws SQLException  
 */

public class databaseWrapper {

	

			public databaseWrapper() throws DbException, SQLException{
	        
			   System.out.println( "Setting up access point for Iris project");

	        try {
	        Class.forName("org.postgresql.Driver");
	        } catch (ClassNotFoundException e) {
	          
	        	System.err.println( "Driver not found: " + e + "\n" + e.getMessage() );
	        }
	    
	        try {
	          conn = DriverManager.getConnection ("jdbc:postgresql://localhost:1432","g08v36205_u","6IxtbnTGoI");
	          stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        	        ResultSet.CONCUR_READ_ONLY);
	        } catch (Exception e) {
	               System.err.println("Exception: " + e + "\n" + e.getMessage() );
	        }
	        
	        rs = stmt.executeQuery("SELECT * FROM iris");
	        System.out.println("Access Point successfully created");
        	
			
			}

			/**
			 * converts a BitCode object to a byte array
			 * @param BitCode
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
			 * converts a byte array to a BitCode object
			 * @param byte[]
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
			
		
			
			
			/**
			 * add a bitcode to the database for the left iris.
			 * @param String, BitCode
			 */
			
			
		   
		@Test   public void addLeft(String id, BitCode bitcode) throws SQLException, IOException{
	        
			byte[] code = databaseWrapper.toByteArray(bitcode);
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
		 * add a bitcode to the database for the right iris.
		 * @param String, BitCode
		 * @throws SQLException
		 * @throws IOException
		 */
		
	       
		  @Test  public void addRight(String id, BitCode bitcode) throws SQLException, IOException{
		        
				byte[] code = databaseWrapper.toByteArray(bitcode);
		    	String insert = new String();
		    	int count;
		    	
	            for(count  =0; count < code.length; count++){

	                if(count == 0)
	                    insert = insert + code[count];
	                else
	                    insert = insert +"," + code[count];
	            }

	            stmt.executeUpdate("UPDATE iris SET r = '{" + insert + "}' WHERE id = '" + id + "'");             
		    	org.junit.Assert.assertFalse(count==0);
			   
			   }
	        
	      
	        /**
			 * add a bitcode to the database for the right iris using BYTEA.
			 * @param String,byte[]
			 * @throws SQLException
			 */
	        
	        private void addRight(String id, byte[] bitcode) throws SQLException{
				   
				   
	        	 PreparedStatement ps = conn.prepareStatement("UPDATE iris SET r = ? WHERE id = '" + id + "'");
	        	 ps.setBytes(1, bitcode);
	        	 ps.executeUpdate();
	        	 ps.close();
	        	 
	        }
	        
	        /**
			 * add a new complete row to the database with Id as primary key.
			 * @param String,byte[]
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
				 * add a new Id to the database.
				 * @param String
		         * @throws SQLException 
				 */
		        
	         public void addId(String id) throws SQLException{
	        	
	        	stmt.executeUpdate("INSERT INTO iris (id,acc) VALUES('" + id + "', true);");
	        }
	         
	         /**
	          * get a byte array for the left iris for next record in the database 
		      * @throws SQLException 
		      * @return byte[]
			  */
	         
	         private byte[] getLeftArray(String id) throws SQLException{
				

	        	 byte[] result = rs.getBytes("l");
	        	 id = rs.getString("id");
	        	 
	        	 return result;
	        	 
	        	 
	            }
	         
	         

	         /**
	          * get a byte array for the right iris for the next record in the database 
	          * @param String
		      * @throws SQLException 
		      * @return byte[]
			  */
	    
	         private  byte[] getRightArray(String id) throws SQLException{
	         

	        	 
	        	 byte[] result = rs.getBytes("r");
	        	 id = rs.getString("id");
        	 
	        	 return result;
        	 
        	 
            }
	         
	         /**
	          * get a BitCode object for the left iris for the next record in the database 
		      * @throws SQLException 
		      * @return BitCode[]
			  */
    
	        
	         public BitCode getLeftCode() throws SQLException{
					
	        	 Array r;
	        	 r = rs.getArray("l");
	        	 Integer[] result = (Integer[])r.getArray();
	        	 
	        
	        	 
	        	 byte[] bitarray = new byte[result.length];
	        	 for(int i = 0; i<result.length;i++){
	        		 
	        		 bitarray[i] = result[i].byteValue();
	        		 
	        	 }
	        	 
	        	 System.out.println(Arrays.toString(bitarray));
	        	 
	        	 BitCode bitcode = databaseWrapper.toBitCode(bitarray);
	        	 
	        	 return bitcode;
	        	 
	        	 
	            }
	         
	         /**
	          * get a BitCode object for the right iris for the next record in the database 
		      * @throws SQLException 
		      * @return BitCode[]
			  */
    
	         
	         
	         
	         
	         public BitCode getRightCode() throws SQLException{
					
	        	 Array r;
	        	 r = rs.getArray("r");
	        	 Integer[] result = (Integer[])r.getArray();
	        	 
	        
	        	 byte[] bitarray = new byte[result.length];
	        	 for(int i = 0; i<result.length;i++){
	        		 
	        		 bitarray[i] = result[i].byteValue();
	        		 
	        	 }
	        	 
	        	 System.out.println(Arrays.toString(bitarray));
	        	 
	        	 BitCode bitcode = this.toBitCode(bitarray);
	        	 
	        	 return bitcode;
	        	 }
	         
	         /**
				 * retreives the id String where the resultset is currently positioned
				 * @throws SQLException 
		         * @return String
				 */
	         
	         
	         public String getId() throws SQLException{
	        	 
	        	 String id = rs.getString("id");
	        	 return id;
	        	 
	         }
	         
	         /**
				 * retreives the id status where the resultset is currently positioned
				 * @throws SQLException 
		         * @return Boolean
				 */
	         
	         public Boolean getAccess() throws SQLException{
	        	 
	        	 Boolean result = rs.getBoolean("acc");
	        	 return result;
	        	 
	        	 
	         }
	         
	         /**
				 * retreives a fresh resultset
				 * @throws SQLException 
		         * @return ResultSet
				 */
	         
	         
	         public ResultSet getNewSet() throws SQLException{
	        	 
	        	ResultSet rset = stmt.executeQuery("SELECT * FROM iris");
	        	 
				return rset; 
	        	 
	        	 
	        	 }
	         
	         /**
				 * delete a single specified entry in the database
				 * @throws SQLException 
		         * @param String
				 */
	         
	         public void DeleteOne(String id) throws SQLException{
	        	 
	        	 stmt.executeUpdate("DELETE FROM iris WHERE id ='" +id + "'");
	        	 
	        	 
	         }
	         
	         /**
				 * delete all entries in the database
				 * @throws SQLException 
		         * @return Boolean
				 */
	         
	         public Boolean DeleteAll() throws SQLException{
	        	 
	        	 stmt.executeUpdate("DELETE FROM iris");
	        	 return true;
	        	 
	         }
	         
	         /**
				 * change the access status of an existing database record
				 * @throws SQLException 
		         * @param String, Boolean
				 */
	         
	         public void setAccess(String id, Boolean value) throws SQLException{
	        	 
	        	 stmt.executeUpdate("UPDATE iris SET acc = " + value + " WHERE id = '" + id + "'");
	        	 
	        	 
	         }
	         
	         /**
				 * A method that allows a user to obtain the size of the current database
				 * 
		         * @throws SQLException 
		         * @return int
				 */
	         
	         
	         public int getNumberRecords() throws SQLException{
	        	 
	        	 rs.last();
	        	 int result = rs.getRow();
	        	 return result;
	         
	         }
	         
	        
	        
	        public ResultSet rs;				//The resultset set up in constructor
	        private static Connection conn;		//Connection automatically set up on construction
	        private static Statement stmt;	
	   
	        
	            
	            
	   public class DbException extends Exception{
		   
		   public DbException(){
		   System.out.println("DatabaseWrapper Error");
		   }
	   }

	   }


	   





