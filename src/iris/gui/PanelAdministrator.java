package iris.gui;

import iris.database.databaseWrapper;
import iris.database.databaseWrapper.DbException;
import iris.imageToBitcode.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.LocateIris;
import iris.imageToBitcode.UnWrapper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * PanelAdministrator provides database admin facilities
 * @author ss1008
 */

public class PanelAdministrator extends javax.swing.JPanel implements ActionListener {

	

	private static final long serialVersionUID = 1L;
	static JLabel imageEye;
	static JLabel imageUnwrappedEye;
	static JLabel imageBitCode;
	static JLabel recordsCount;
	
	static JTextArea output;
	
	
	static JButton getimage;
	static JButton deleteEntry;
	static JButton addEntry;
	static JButton deleteOne;
	static JButton suspend;
	static JButton restore;
	static JButton reset;
	static JButton dbinfo;
	static JButton refreshout;
	
	static ImageIcon iconEye;
	static ImageIcon iconUnwrappedEye;
	static ImageIcon iconBitCode;
	
	static JTable table;
	static TableModel dataModel;
	
	
    static JTextField hamming_result = new JTextField(30);
        
    static BufferedImage Eye;
    static BufferedImage OriginalEye;
    static BufferedImage UnwrappedEye;
    static BufferedImage ValidateBitCode;
  
    
    static BitCode[] bc = new BitCode[2];
    static EyeDataType eyeData;
    static Boolean eyeLoaded;
    static Boolean added = false;
    static Boolean displayed;
    
    static JPanel paneldbinfo;
    static JPanel panelTable;
    static JScrollPane scrollpane;
    static JPanel background;

    
    static databaseWrapper db;
    
    static String[] contents;
    
    static String selected;
    
	public PanelAdministrator(int FRAME_WIDTH,int FRAME_HEIGHT) {
		
		
			
              JPanel panelEyeImage;
              JPanel panelWhole;
              JPanel panelData;
              JPanel panelUnwrap;
              JPanel panelBitcode;
              JPanel panelAdministrator;
              JPanel panelButtons;
              
              
              
              try {
				db = new databaseWrapper();
			
              } catch (DbException e) {
    				
    				e.printStackTrace();
    			} catch (SQLException e) {
    				
    				e.printStackTrace();
    			}
              
 		
//           create panel 
             
             	panelEyeImage = new JPanel();
                panelWhole = new JPanel();
                panelData = new JPanel();
                panelUnwrap = new JPanel();
                panelBitcode = new JPanel();
                panelAdministrator = new JPanel();
                panelButtons = new JPanel();
                paneldbinfo = new JPanel();

                
                /**
				 * 
				 */
               
					dataModel = new AbstractTableModel() {
		      	         
						private static final long serialVersionUID = 1L;

						public int getColumnCount() { return 2; }
		      	          public int getRowCount() { 
		      	        	  int result = 0;
		      	        	  
							try {
								result =  db.getNumberRecords();
								
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
							return result;}
		      	        
		      	          public Object getValueAt(int row, int col) {
		      	        	String id = null;
		      	        	Boolean status = null;
		      	        		
			      	        	  if(col==0){
			      	        		  try {
										db.rs.absolute(row+1);
										id = db.getId();
										
										
									} catch (SQLException e) {
										e.printStackTrace();
									}
			      	        	  	  
			      	        		 
			      	        	  }
			      	        	  
			      	        	  else if(col==1){
			      	        		  
			      	        		 try {
										db.rs.absolute(row+1);
										status = db.getAccess();
				      	        		if(status==true)
				      	        			id = "Access Active";
				      	        		else id = "Access Suspended";
				      	        		
										
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
			      	        		 
			      	        		
			      	        	  }
			      	        	  ;
								return id;  }
		      	      };
		      	      
		      	    table = new JTable(dataModel);
		            table.getColumnModel().getColumn(0).setHeaderValue("ID");
		            table.getColumnModel().getColumn(1).setHeaderValue("Status");
		            table.setRowSelectionAllowed(true);
		            table.getSelectionModel().addListSelectionListener(new RowListener());
					
                
                
                
              
               
                scrollpane = new JScrollPane(table);
                scrollpane.setPreferredSize(new Dimension(300,275));
                scrollpane.setVisible(true);
                
                
                try {
					int i = db.getNumberRecords();
                	contents = new String[i];
                	int c;
                	for(c = 0; c<i; c++){
                		db.rs.absolute(c+1);
                		contents[c] = db.getId();
                }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
                
				
				
				
               
                imageEye = new  JLabel();
             	imageUnwrappedEye = new  JLabel();
             	imageBitCode = new  JLabel();
             	recordsCount = new JLabel();
             	iconEye = new ImageIcon();
             	iconUnwrappedEye = new ImageIcon();
             	iconBitCode = new ImageIcon();
             	eyeLoaded = false;
             	displayed = false;
             	getimage = new JButton("Load image");
             	deleteEntry = new JButton("Delete All");
             	addEntry = new JButton("Add to Database");
             	deleteOne = new JButton("Delete Entry");
             	deleteOne.setEnabled(false);
             	restore = new JButton("Restore Access");
             	restore.setEnabled(false);
             	suspend = new JButton("Suspend Access");
             	suspend.setEnabled(false);
             	reset = new JButton("Reset");
             	dbinfo = new JButton("Refresh Database details");
             	refreshout  = new JButton("Clear History");
             
             	
             	
             	getimage.setSize(320, 30);
             	getimage.setEnabled(true);
             	deleteEntry.setSize(320, 30);
             	deleteEntry.setEnabled(true);
             	addEntry.setSize(320, 30);
             	addEntry.setEnabled(true);
             	deleteOne.setEnabled(true);
             	restore.setEnabled(true);
             	suspend.setEnabled(true);
             	
             	OriginalEye = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
             	Eye = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
             	
             	
                //set images to alternate squares of grey and white
             	int square_size=10;
             	int grey = 0xf0f0f0;
             	int white = 0xffffff;
             	int colour;
             	for(int x =0;x<OriginalEye.getWidth();x++)
             		for(int y=0;y<OriginalEye.getHeight();y++) 
             		{
             			colour=((x/square_size +y/square_size)%2==1)?grey:white;
             			OriginalEye.setRGB(x,y,colour);
             		}
             
             
             	iconEye.setImage(OriginalEye);
             	imageEye.setIcon(iconEye);
             
             	panelEyeImage.setLayout(new BorderLayout());
             	panelEyeImage.setBackground(Color.WHITE);
             	panelEyeImage.add(getimage,BorderLayout.NORTH);
             	panelEyeImage.add(imageEye,BorderLayout.SOUTH);
             	panelButtons.setLayout(new GridLayout());
             	panelButtons.add(deleteEntry);
             	panelButtons.add(addEntry);
             	panelButtons.add(deleteOne);
             	panelButtons.add(suspend);
             	panelButtons.add(restore);
             	panelEyeImage.add(reset);
             	
             	GridLayout contents = new GridLayout();
             	contents.setHgap(40);
             	panelWhole.setBackground(Color.WHITE);
             	panelWhole.setLayout(contents);
             	panelWhole.add(panelEyeImage);
             	panelWhole.add(scrollpane);
             	
            // GridLayout overall = new GridLayout();
             //overall.setVgap(10);
             background = new JPanel();
             //background.setLayout(overall);
             background.setBackground(Color.WHITE);
             background.setPreferredSize(new Dimension(FRAME_WIDTH-50,FRAME_HEIGHT-75));
             background.setBorder(BorderFactory.createLineBorder(Color.BLACK));
             add(background);
             hamming_result.setEnabled(false);
             background.add(panelWhole);
            // background.add(scrollpane);
             background.add(panelButtons);
             paneldbinfo.add(dbinfo);
             paneldbinfo.add(refreshout);
             background.add(dbinfo);
             //background.add(hamming_result);
             
             output = new JTextArea(1, 10);
             output.setEditable(false);
             JScrollPane outputPane = new JScrollPane(output,
                              ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
             outputPane.setPreferredSize(new Dimension(550,300));
             background.add(outputPane);
             
             
             
             background.setAutoscrolls(true);
             
             getimage.addActionListener(this);
             deleteEntry.addActionListener(this);
             addEntry.addActionListener(this);
             deleteOne.addActionListener(this);
             reset.addActionListener(this);
             restore.addActionListener(this);
             suspend.addActionListener(this);
             dbinfo.addActionListener(this);
             
            
            
           
		
	}
	
	 /**
     * Handles all button presses on this tab
     */
	
	
	public void actionPerformed(ActionEvent ev){
		
		long startTime = 0;
    	
		if (ev.getActionCommand()=="Load image"){
    	
    	Eye = gtImage();
    	//startTime=System.currentTimeMillis(); //calculate runtime
        //imageEye.setIcon(iconEye);
        eyeLoaded = true;
        //Eye.getGraphics().drawImage( iconEye.getImage(),0,0,null);
        eyeData = LocateIris.find_iris(Eye);
        UnWrapper uw = new UnWrapper();
        BufferedImage validate=uw.originalWithGuides(Eye,eyeData);
        iconEye.setImage(validate);
        imageEye.setIcon(iconEye);
        imageEye.repaint();
        
        
        BufferedImage biUnwrapped = uw.unWrapWithGuides(Eye,eyeData,128,512);
        iconUnwrappedEye.setImage(biUnwrapped);
        imageUnwrappedEye.setIcon(iconUnwrappedEye);
        imageUnwrappedEye.repaint();
		BitcodeGenerator b = new BitcodeGenerator();
		bc[0] =  b.getFastBitcode(Eye,eyeData);
		ValidateBitCode = bc[0].getBitCodeImage(512,128,32);
		iconBitCode.setImage(ValidateBitCode);
        imageBitCode.setIcon(iconBitCode);
        imageBitCode.repaint();
        added = false
        ;
        
		}
	
       else if (ev.getActionCommand()=="Delete All"){
       
       try {
    	   int confirm = JOptionPane.showConfirmDialog(
                   this,
                   "Are you sure you wish to delete all records?");
        
    	if (confirm==0){
    	
		Boolean success;
    	databaseWrapper db = new databaseWrapper();
		success = db.DeleteAll();
		output.setEnabled(true);
		if(success == true){
	    output.append("All Entries Deleted");
	    output.append("\n");
	    this.updateTable();
		}}
				
		else if(confirm == 1){ output.append("No Entries Deleted");
		output.append("\n");}
		
		else if(confirm == -1) {output.append("Delete All Cancelled");
		output.append("\n");}
		
    	
		
	} catch (DbException e) {
	
		e.printStackTrace();
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
    
       
       }
		
	else if (ev.getActionCommand()=="Add to Database"){
		
		output.setEnabled(true);
		
		if(added == true){
			output.append("Already Added");
			output.append("\n");
			return;
		}
		if(eyeLoaded == false){
			output.append("No Image Loaded");
			output.append("\n");
			return;
		}
		
		String s = (String)JOptionPane.showInputDialog(
                this,
                "Enter ID: ",
                "Adding to Database",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Write Text Here");
		if(s.length()==0) {
				output.append("No ID Entered");
				output.append("\n");
				output.setEnabled(true);
				
	}
				else if (s.length()>10) {
				output.append("ID too long");
				output.append("\n");
				output.setEnabled(true);
				}
				else {
					try {
						databaseWrapper db = new databaseWrapper();
						int size = db.getNumberRecords();
						db.addId(s);
						db.addLeft(s, bc[0]);
						output.append("Id '" + s + "' entered into database");
						output.append("\n");
						this.updateTable();
						String[] updated = new String[contents.length+1];
						updated[contents.length] = s;
						
						for(int i = 0; i<contents.length; i++)
							updated[i] = contents[i];
						
						System.out.println(updated[size]);
						
						contents = updated.clone();
						
						System.out.println(contents
								[size]);
						
						
						
						
						
					} catch (DbException e) {
						
						e.printStackTrace();
					} catch (SQLException e) {
						output.append("ID already in use");
						output.append("\n");
						e.printStackTrace();
						return;
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					added = true;
				
				}
	}
		
	else if (ev.getActionCommand()=="Delete Entry"){	
	
		try {
			   int confirm = JOptionPane.showConfirmDialog(
	                   this,
	                   "Are you sure you wish to delete " + selected + "?");
	        
	    	if (confirm==0){
			
			
			
			
			
			int i;
			for(i=0;i<contents.length;i++){
				
				if(contents[i].contains(selected)){
					databaseWrapper db = new databaseWrapper();
					db.DeleteOne(selected);
					output.append("ID " + selected + " deleted from database");
					output.append("\n");
					
					
					for(;i<contents.length-1;i++){
						
						contents[i] = contents[i+1];
						
					}
					
					break;
					
					}
			
				else if(i==contents.length-1){
				output.append("ID not found");
				output.append("\n");}
				
			}
			
			}
			else{ output.append("ID too long");
			output.append("\n");
			}
			} catch (DbException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		this.updateTable();
	}    
	
	else if (ev.getActionCommand()=="Reset"){
	
	//output.setText("");
	//output.setEnabled(false);
	iconEye.setImage(OriginalEye);
	imageEye.setIcon(iconEye);
	imageEye.repaint();
	
	added = true;
	
	
	}
	
	else if (ev.getActionCommand()=="Suspend Access"){
		
		
		
		output.setEnabled(true);
		
	
		try {
			String id = (String)JOptionPane.showInputDialog(
	                this,
	                "Enter ID to be suspended: ",
	                "ID suspension",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                "Write ID Here");
			if(id.length()<11){
		int i;
		int size = contents.length;
		Boolean found = false;
		for(i=0;i<size;i++){
					
					if(contents[i].contains(id)){
					
						databaseWrapper db = new databaseWrapper();
						db.setAccess(id, false);
						output.append("ID " + id + " has access suspended");
						output.append("\n");
						this.updateTable();
						found = true;
					}
		}	if(found == false) output.append("ID not in database");
	
			}
			else{ output.append("ID too long");
			output.append("\n");}
} catch (DbException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			output.append("Database Error");
			output.append("\n");
			e.printStackTrace();
		}}
		
		
		
	
			
	
	else if (ev.getActionCommand()=="Restore Access"){
		output.setEnabled(true);
		
		try {
			String id = (String)JOptionPane.showInputDialog(
	                this,
	                "Enter ID to be restored: ",
	                "ID restore",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                "Write ID Here");
			if(id.length()<11){
				int size = contents.length;
				int i;
				for(i=0;i<size;i++){
					
					if(contents[i].contains(id)){
					
						databaseWrapper db = new databaseWrapper();
						db.setAccess(id, true);
						output.append("ID " + id + " has access restored");
						output.append("\n");
						this.updateTable();
						break;
					}
					
					else if(i==size-1){
				
					output.append("ID not in database");
					output.append("\n");
				}
				}
				
				}
			else {output.append("ID too long");
			output.append("\n");}
			
		} catch (DbException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			output.append("Database Error");
			output.append("\n");
			e.printStackTrace();
		}}
		
	else if (ev.getActionCommand()=="Refresh Database details"){
		
		this.updateTable();
		
		}
	}
	
	private void updateTable(){
		
		
		try {
			final databaseWrapper dbUpdate = new databaseWrapper();
		
		
		TableModel dataModel = new AbstractTableModel() {
	         
			public int getColumnCount() { return 2; }
	          public int getRowCount() { 
	        	  int result = 0;
	        	  
				try {
					result =  dbUpdate.getNumberRecords();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				return result;}
	        
	          public Object getValueAt(int row, int col) {
	        	String id = null;
	        	Boolean status = null;
	        		
    	        	  if(col==0){
    	        		  try {
							dbUpdate.rs.absolute(row+1);
							id = dbUpdate.getId();
						} catch (SQLException e) {
							e.printStackTrace();
						}
    	        	  	  
    	        		  
    	        	  }
    	        	  
    	        	  else if(col==1){
    	        		  
    	        		 try {
							db.rs.absolute(row+1);
							status = dbUpdate.getAccess();
	      	        		if(status==true)
	      	        			id = "Access Active";
	      	        		else id = "Access Suspended";
	      	        		
							
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
    	        		 
    	        		
    	        	  }
    	        	  ;
					return id;  }
	      };
		    
		    table.setModel(dataModel);
		    table.getColumnModel().getColumn(0).setHeaderValue("ID");
            table.getColumnModel().getColumn(1).setHeaderValue("Status");
		} catch (DbException e1) {
			
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
		
		
		
	}
	
	 /**
     * 
     * @return an ImageIcon of an eye chosen by the user (using JFileUser)
     */
	
	private BufferedImage gtImage() {
        JFileChooser filedialog = new  JFileChooser();
        try{
        	File f = new File(new File("./images/automatic/smalltest/").getCanonicalPath());
        	filedialog.setCurrentDirectory(f);
        }
        catch (IOException e) {}
        filedialog.showOpenDialog(this.getParent());
        //System.out.println(filedialog.getSelectedFile().getPath());
        BufferedImage bi = null;
		try { 
			File f = new File(filedialog.getSelectedFile().getPath());
			bi = ImageIO.read(f);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
		return bi;
        //ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        //BufferedImage bi =new BufferedImage(filedialog.getSelectedFile().getPath());
        //return icon;    
    }
	

	private class RowListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			
			int row = table.getSelectedRow();
			selected = (String) table.getValueAt(row, 0);
			
			deleteOne.setEnabled(true);
			
			if(table.getValueAt(row, 1) == "Access Active")
					suspend.setEnabled(true);
			else restore.setEnabled(true);
			
			
		}}



}


