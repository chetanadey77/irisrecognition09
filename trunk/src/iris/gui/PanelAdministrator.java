package iris.gui;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.LocateIris;
import iris.imageToBitcode.UnWrapper;
import iris.database.databaseWrapper;
import iris.database.databaseWrapper.DbException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import org.jdesktop.layout.GroupLayout;

public class PanelAdministrator extends javax.swing.JPanel implements ActionListener {

	
	static JButton addEntry;
	static JButton deleteEntry;
	static JButton deleteAll;
	
    
    static BitCode[] bc = new BitCode[2];
    static EyeDataType eyeData;
    static Boolean eyeLoaded;
	
	public PanelAdministrator(int FRAME_WIDTH,int FRAME_HEIGHT) {
		    
 			
 			addEntry= new JButton("Add entry");
 			deleteEntry= new JButton("Delete entry");
 			deleteAll= new JButton("Delete all entries");
 			
             JPanel panelEyeImage;
             JPanel panelWhole;
             JPanel panelData;
             JPanel panelUnwrap;
             JPanel panelBitcode;
             JPanel panelAdministrator;
             
//           create panel 
             
             	 panelEyeImage = new JPanel();
                 panelWhole = new JPanel();
                 panelData = new JPanel();
                 panelUnwrap = new JPanel();
                 panelBitcode = new JPanel();
                 panelAdministrator = new JPanel();
             	
             
             	eyeLoaded = false;
             	addEntry = new JButton("Add entry to Database");
             	deleteEntry = new JButton("Delete entry from Database");
             	deleteAll = new JButton("Delete all entries");
             	addEntry.setSize(320, 30);
             	addEntry.setEnabled(true);
             	deleteEntry.setSize(320, 30);
             	deleteEntry.setEnabled(true);
             	deleteAll.setSize(320, 30);
             	deleteAll.setEnabled(true);
      	
             	
             	panelAdministrator.add(addEntry,BorderLayout.NORTH);             	
                panelAdministrator.setLayout(new BorderLayout());
             	panelAdministrator.setBackground(Color.WHITE);
             	panelAdministrator.add(deleteEntry,BorderLayout.WEST);
             	panelAdministrator.add(deleteAll,BorderLayout.SOUTH);
                    	
             	//panelWhole[n].setLayout(new GridLayout(1,2));
             	panelWhole.setBackground(Color.WHITE);
             	panelWhole.setLayout(new BorderLayout());
             	panelWhole.add(panelEyeImage,BorderLayout.WEST);
             	panelWhole.add(panelData,BorderLayout.EAST);
             	panelWhole.add(panelAdministrator,BorderLayout.SOUTH);
       
             
             addEntry.addActionListener(this);
             deleteEntry.addActionListener(this);
             deleteAll.addActionListener(this);
		
	}
	
	
	public void actionPerformed(ActionEvent ev){
		
		long startTime = 0;
    	//System.out.println( ev.getActionCommand()+"  "+ev.getClass()+" "+ev.getSource());
    	
		if (ev.getActionCommand()=="Add entry to Database"){
  
        
         System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
	
		}
		else System.out.println("Error with the Main Frame Action Event Handler");
		
	}

	private ImageIcon gtImage() {
        JFileChooser filedialog = new  JFileChooser();
        try{
        	File f = new File(new File("./images/automatic/").getCanonicalPath());
        	filedialog.setCurrentDirectory(f);
        }
        catch (IOException e) {}
        filedialog.showOpenDialog(this.getParent());
        //System.out.println(filedialog.getSelectedFile().getPath());
        ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        return icon;    }
	}
