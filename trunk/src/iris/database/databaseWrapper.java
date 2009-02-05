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

	

	public static void main(String [] args)throws SQLException, DbException, IOException{
	
		
	    //testing the binary bitcode generation
		Integer[] one = {32,45,34,53,56};
		//String test = Integer.toBinaryString(one);
		String outcome = new String();
    	String iterates = new String();
    	
		for (int count = 0; count < one.length; count++){
	   	 
			iterates = Integer.toBinaryString(one[count]);
			int length = 32-(iterates.length());
		
		for(;length>0; length--){
			//ensuring that the leading 0s are included
			outcome = outcome + "0";
			}
		
		outcome = outcome + iterates;
		System.out.println(outcome);
		}
		
		BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
		out.write(outcome);
		out.close();
		
		File file = new File("test.txt");
    	FileInputStream fis = new FileInputStream(file);
    	PreparedStatement ps = conn.prepareStatement("UPDATE iris SET l = ? WHERE id = ?");
    	ps.setString(2, "ss1008");
    	ps.setBinaryStream(1, fis, (int)file.length());
    	ps.executeUpdate();
    	ps.close();
    	fis.close();
	
	
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

		   
		   
		   
		   
	       private void addLeft(String id, int[] code) throws SQLException, IOException{
	        
	    	 //(at the moment this is assuming that only a single integer is being passed in)
	        	String outcome = new String();
	        	String iterates = new String();
	        	
	    		for (int count = 0; count < 65; count++){
	    	   	 
	    			iterates = Integer.toBinaryString(code[count]);
	    			int length = 32-(iterates.length());
	    		
	    		for(;length>0; length--){
	    			//ensuring that the leading 0s are included
	    			outcome = outcome + "0";
	    			}
	    		
	    		outcome = outcome + iterates;
	    		System.out.println(outcome);
	    		}
	        
	    		try{
	        		//writing the 2048 bit string to file
	        		BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
	    			out.write(outcome);
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
	        	
	        	File file = new File("test.txt");
	        	FileInputStream fis = new FileInputStream(file);
	        	PreparedStatement ps = conn.prepareStatement("UPDATE iris SET l = ? WHERE id = ?");
	        	ps.setString(2, id);
	        	ps.setBinaryStream(1, fis, (int)file.length());
	        	ps.executeUpdate();
	        	ps.close();
	        	fis.close();
	       
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
	        
	        	private String Next(Integer[] left, Integer[] right) throws SQLException, IOException {
	        		BufferedInputStream leftin;
	        		BufferedInputStream rightin;
	        		String id = new String();
	        		byte leftbyte[] = null;
	        		byte rightbyte[] = null;
	        		
	        		//String l = new String();
	        		//String r = new String();
	        		//String id = new String();
	        		
	        		
	        		if(rs.next()){
	        			leftin = new BufferedInputStream(rs.getBinaryStream("l"));
	        			leftin.read(leftbyte,0,32);
	        			rightin = new BufferedInputStream(rs.getBinaryStream("r"));
		                rightin.read(rightbyte, 0,32);
	        			//r = rs.getString("r"); 
	        			id = rs.getString("id");
	        			
	        			}
	        		
	        		else return null;
					int result = 0;
					for(int count = 0; count<4; count++){
					
					int n = (leftbyte[count] < 0 ? (int)leftbyte[count] + 256 : (int)leftbyte[count]) << (8*count);
					System.out.println(n);
					result += n;
					}
	        		System.out.println(result);
					return id;
			        	 
	        	
					
	        	}
	        	
	        	private ResultSet rs;
	        	
	        }
	        
	        
	        
	        private static Connection conn;
	        private Statement stmt;
	   
	        
	            
	            
	   public class DbException extends Exception{}

	   }


	   





