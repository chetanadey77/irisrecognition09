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
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * PanelValidate allows the user to validate an uploaded image against the database. It shows the bitcode and unwrapped version of any uploaded image.
 * @author ss1008
 */

public class PanelValidate extends javax.swing.JPanel implements ActionListener {

	
	private static final long serialVersionUID = -1280204992778919543L;
	static JLabel imageEye;
	static JLabel imageUnwrappedEye;
	static JLabel imageBitCode;
	
	static JButton getimage;
	static JButton validate;
	static JButton reset;
	
	static ImageIcon iconEye;
	static ImageIcon iconUnwrappedEye;
	static ImageIcon iconBitCode;
	
	
    static JTextField hamming_result = new JTextField(50);
        
    static BufferedImage Eye;
    static BufferedImage OriginalEye;
    static BufferedImage UnwrappedEye;
    static BufferedImage ValidateBitCode;
    static BufferedImage OriginalUnwrappedEye;
    static BufferedImage OriginalValidateBitCode;
    
    static BitCode[] bc = new BitCode[2];
    static EyeDataType eyeData;
    static Boolean eyeLoaded;
    static JPanel panelValidate;
    static JPanel panelButtons;
    
	public PanelValidate(int FRAME_WIDTH,int FRAME_HEIGHT) {
		    
 			
 			
             JPanel panelEyeImage;
             JPanel panelWhole;
             JPanel panelData;
             JPanel panelUnwrap;
             JPanel panelBitcode;
             
             
//           create panel 
             
             	panelEyeImage = new JPanel();
                 panelWhole = new JPanel();
                 panelData = new JPanel();
                 panelUnwrap = new JPanel();
                 panelBitcode = new JPanel();
                 panelValidate = new JPanel();
                 panelButtons = new JPanel();
               
             	imageEye = new  JLabel();
             	imageUnwrappedEye = new  JLabel();
             	imageBitCode = new  JLabel();
             	iconEye = new ImageIcon();
             	iconUnwrappedEye = new ImageIcon();
             	iconBitCode = new ImageIcon();
             	eyeLoaded = false;
             	getimage = new JButton("Load image to be validated");
             	validate = new JButton("Validate Identity");
             	reset = new JButton("Reset");
             	getimage.setSize(320, 30);
             	getimage.setEnabled(true);
             	validate.setSize(320, 30);
             	validate.setEnabled(true);
             	
             	Eye = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
             	OriginalEye = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
             	UnwrappedEye  = new BufferedImage(512,128,BufferedImage.TYPE_INT_RGB);
             	OriginalUnwrappedEye  = new BufferedImage(512,128,BufferedImage.TYPE_INT_RGB);
             	ValidateBitCode = new BufferedImage(475,128,BufferedImage.TYPE_BYTE_GRAY);
             	OriginalValidateBitCode = new BufferedImage(475,128,BufferedImage.TYPE_BYTE_GRAY);
               
             
             //set logo as background on OriginalEye	
             	
             	try {
					OriginalEye = javax.imageio.ImageIO.read(new File("Gray_logo.gif"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  	
                           
            
             //set images to alternate squares of grey and white
             	int square_size=10;
             	int grey = 0xf0f0f0;
             	int white = 0xffffff;
             	int colour;
             	
                    	
             	
             	square_size=8;
             	for(int x =0;x<OriginalUnwrappedEye.getWidth();x++)
                 		for(int y=0;y<OriginalUnwrappedEye.getHeight();y++) 
                 		{
                 			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                 			OriginalUnwrappedEye.setRGB(x,y,colour);
                 		}
             	for(int x =0;x<OriginalValidateBitCode.getWidth();x++)
                     		for(int y=0;y<OriginalValidateBitCode.getHeight();y++) 
                     		{
                     			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                     			OriginalValidateBitCode.setRGB(x,y,colour);
                     		}
             
             	iconEye.setImage(OriginalEye);
             	iconUnwrappedEye.setImage(OriginalUnwrappedEye);
             	iconBitCode.setImage(OriginalValidateBitCode);
             
             	imageEye.setIcon(iconEye);
             	imageUnwrappedEye.setIcon(iconUnwrappedEye);
             	imageBitCode.setIcon(iconBitCode);
             
             	
             	panelEyeImage.setLayout(new BorderLayout());
             	panelEyeImage.setBackground(Color.WHITE);
             	panelEyeImage.add(getimage,BorderLayout.NORTH);
             	panelEyeImage.add(imageEye,BorderLayout.SOUTH);
             	
             	panelValidate.setLayout(new GridBagLayout());
             	panelValidate.setBackground(Color.WHITE);
             	panelButtons.add(validate);
             	panelButtons.add(reset);
             	
             	
             	panelUnwrap.setLayout(new BorderLayout());
             	panelUnwrap.setBackground(Color.WHITE);
             	panelUnwrap.add(new JLabel("Unwrapped iris"),BorderLayout.NORTH);
             	panelUnwrap.add(imageUnwrappedEye,BorderLayout.SOUTH);
             	panelBitcode.setLayout(new BorderLayout());
             	panelBitcode.setBackground(Color.WHITE);
             	panelBitcode.add(new JLabel("Bitcode"),BorderLayout.NORTH);
             	panelBitcode.add(imageBitCode,BorderLayout.SOUTH);
             	
             	panelData.setLayout(new BorderLayout());
             	panelData.setBackground(Color.WHITE);
             	panelData.add(panelUnwrap,BorderLayout.NORTH);
             	panelData.add(panelBitcode,BorderLayout.SOUTH);
             	
             	
             	panelWhole.setBackground(Color.WHITE);
             	BorderLayout layout = new BorderLayout();
             	layout.setHgap(10);
             	panelWhole.setLayout(layout);
             
             	panelWhole.add(panelEyeImage,BorderLayout.WEST);
             	panelWhole.add(panelData,BorderLayout.EAST);
             	panelWhole.add(panelValidate,BorderLayout.SOUTH);
             	
             	
             JPanel background = new JPanel();
             background.setBackground(Color.WHITE);
             background.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
             add(background);
             hamming_result.setEnabled(false);
             background.add(panelWhole);
             background.add(hamming_result);
             background.add(panelButtons);
           
             
             getimage.addActionListener(this);
             validate.addActionListener(this);
             reset.addActionListener(this);
		
		
	}
	 /**
     * Handles all button presses on this tab
     */
	
	public void actionPerformed(ActionEvent ev){
		
		long startTime = 0;
    	
		if (ev.getActionCommand()=="Load image to be validated"){
    	
    	hamming_result.setText("");
    	panelButtons.setBackground(Color.WHITE);
        eyeLoaded = false;
        Eye = gtImage();
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
		//System.out.println(bc[n].getBitcodeSize());
		ValidateBitCode = bc[0].getBitCodeImage(475,128,32);
		iconBitCode.setImage(ValidateBitCode);
        imageBitCode.setIcon(iconBitCode);
        imageBitCode.repaint();
		}
        else if (ev.getActionCommand()=="Validate Identity"){
    	
        	hamming_result.setEnabled(true);
			
        BitCode left;
       
      
        
        try {
        	databaseWrapper db = new databaseWrapper(); 
        	
        	String id;
        	Boolean access;
        	double hd;
        	
        	
        	
			while(db.rs.next()){
        	
        	id = db.getId();
        	access = db.getAccess();
			left = db.getLeftCode();
			bc[1] = left;
			byte[] testing = db.toByteArray(bc[0]);
			System.out.println(Arrays.toString(testing));
			
			hd = BitCode.hammingDistance(bc[0],bc[1]);
        	
			
			if(hd<.33){  
				
				if(access == true){
				hamming_result.setText("Identity Verified as '" +id +"' with Hamming Distance: "+hd);

				panelButtons.setBackground(Color.GREEN);
				panelButtons.repaint();
				break;
				}
				else if (access == false){

				hamming_result.setText("Identity Verified as '" +id +"' with Hamming Distance: "+hd +" : WARNING: ID SUSPENDED");

				panelButtons.setBackground(Color.yellow);
				panelButtons.repaint();
				break;
					
				}}
				hamming_result.setText("No Match Found");
				hamming_result.setEnabled(true);
				panelButtons.setBackground(Color.RED);
				panelButtons.repaint();
			
			}
			
			
		} catch (DbException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Syntax Error");
			e.printStackTrace();
		}
        
        
         System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
	
		}
        else if (ev.getActionCommand()=="Reset"){
        	
        	hamming_result.setText("");
        	hamming_result.setEnabled(false);
        	panelButtons.setBackground(Color.white);
        	iconUnwrappedEye.setImage(OriginalUnwrappedEye);
        	iconBitCode.setImage(OriginalValidateBitCode);
            imageUnwrappedEye.setIcon(iconUnwrappedEye);
            imageBitCode.setIcon(iconBitCode);
        	iconEye.setImage(OriginalEye);
        	imageEye.setIcon(iconEye);
        	imageUnwrappedEye.repaint();
        	imageBitCode.repaint();
        	imageEye.repaint();
        	
        	
        	}
		else System.out.println("Error with the Main Frame Action Event Handler");
		
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
	}
