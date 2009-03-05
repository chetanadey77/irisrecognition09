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

public class PanelValidate extends javax.swing.JPanel implements ActionListener {

	static JLabel imageEye;
	static JLabel imageUnwrappedEye;
	static JLabel imageBitCode;
	
	static JButton getimage;
	static JButton validate;
	
	static ImageIcon iconEye;
	static ImageIcon iconUnwrappedEye;
	static ImageIcon iconBitCode;
	
	
    static JTextField hamming_result = new JTextField(30);
        
    static BufferedImage Eye;
    static BufferedImage UnwrappedEye;
    static BufferedImage ValidateBitCode;
  
    
    static BitCode[] bc = new BitCode[2];
    static EyeDataType eyeData;
    static Boolean eyeLoaded;
	
	public PanelValidate(int FRAME_WIDTH,int FRAME_HEIGHT) {
		    
 			
 			getimage= new JButton("Load eye image");
 			
             JPanel panelEyeImage;
             JPanel panelWhole;
             JPanel panelData;
             JPanel panelUnwrap;
             JPanel panelBitcode;
             JPanel panelValidate;
             
//           create panel 
             
             	panelEyeImage = new JPanel();
                 panelWhole = new JPanel();
                 panelData = new JPanel();
                 panelUnwrap = new JPanel();
                 panelBitcode = new JPanel();
                 panelValidate = new JPanel();
             	imageEye = new  JLabel();
             	imageUnwrappedEye = new  JLabel();
             	imageBitCode = new  JLabel();
             	iconEye = new ImageIcon();
             	iconUnwrappedEye = new ImageIcon();
             	iconBitCode = new ImageIcon();
             	eyeLoaded = false;
             	getimage = new JButton("Load image to be validated");
             	validate = new JButton("Validate Identity");
             	getimage.setSize(320, 30);
             	getimage.setEnabled(true);
             	validate.setSize(320, 30);
             	validate.setEnabled(true);
             	
             	Eye = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
             	UnwrappedEye  = new BufferedImage(512,128,BufferedImage.TYPE_INT_RGB);
             	ValidateBitCode = new BufferedImage(512,128,BufferedImage.TYPE_BYTE_GRAY);
                         
                           
            
             //set images to alternate squares of grey and white
             	int square_size=10;
             	int grey = 0xf0f0f0;
             	int white = 0xffffff;
             	int colour;
             	for(int x =0;x<Eye.getWidth();x++)
             		for(int y=0;y<Eye.getHeight();y++) 
             		{
             			colour=((x/square_size +y/square_size)%2==1)?grey:white;
             			Eye.setRGB(x,y,colour);
             		}
             	square_size=8;
             	for(int x =0;x<UnwrappedEye.getWidth();x++)
                 		for(int y=0;y<UnwrappedEye.getHeight();y++) 
                 		{
                 			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                 			UnwrappedEye.setRGB(x,y,colour);
                 		}
             	for(int x =0;x<ValidateBitCode.getWidth();x++)
                     		for(int y=0;y<ValidateBitCode.getHeight();y++) 
                     		{
                     			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                     			ValidateBitCode.setRGB(x,y,colour);
                     		}
             
             	iconEye.setImage(Eye);
             	iconUnwrappedEye.setImage(UnwrappedEye);
             	iconBitCode.setImage(ValidateBitCode);
             
             	imageEye.setIcon(iconEye);
             	imageUnwrappedEye.setIcon(iconUnwrappedEye);
             	imageBitCode.setIcon(iconBitCode);
             	
             	panelEyeImage.setLayout(new BorderLayout());
             	panelEyeImage.setBackground(Color.WHITE);
             	panelEyeImage.add(getimage,BorderLayout.NORTH);
             	panelEyeImage.add(imageEye,BorderLayout.SOUTH);
             	
             	panelValidate.setLayout(new BorderLayout());
             	panelValidate.setBackground(Color.WHITE);
             	panelValidate.add(validate,BorderLayout.WEST);
             	
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
             	
             	//panelWhole[n].setLayout(new GridLayout(1,2));
             	panelWhole.setBackground(Color.WHITE);
             	panelWhole.setLayout(new BorderLayout());
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
             //background.add(panelWhole);
             
             getimage.addActionListener(this);
             validate.addActionListener(this);
		
		
	}
	
	
	public void actionPerformed(ActionEvent ev){
		
		long startTime = 0;
    	//System.out.println( ev.getActionCommand()+"  "+ev.getClass()+" "+ev.getSource());
    	
		if (ev.getActionCommand()=="Load image to be validated"){
    	
    	iconEye = gtImage();
    	startTime=System.currentTimeMillis(); //calculate runtime
        imageEye.setIcon(iconEye);
        eyeLoaded = false;
        Eye.getGraphics().drawImage( iconEye.getImage(),0,0,null);
        eyeData = LocateIris.find_iris(Eye);
        UnWrapper uw = new UnWrapper();
        BufferedImage validate=uw.originalWithGuides(Eye,eyeData);
        iconEye.setImage(validate);
        imageEye.setIcon(iconEye);
        imageEye.repaint();
        
        
        BufferedImage biUnwrapped = uw.unWrapWithGuides(Eye,eyeData,128,512);//biUnwrappedEye[n].getWidth(),biUnwrappedEye[n].getHeight());
        iconUnwrappedEye.setImage(biUnwrapped);
        imageUnwrappedEye.setIcon(iconUnwrappedEye);
        imageUnwrappedEye.repaint();
		BitcodeGenerator b = new BitcodeGenerator();
		bc[0] =  b.getBitcode(Eye,eyeData);
		//System.out.println(bc[n].getBitcodeSize());
		ValidateBitCode = bc[0].getBitCodeImage(512,128,32);
		iconBitCode.setImage(ValidateBitCode);
        imageBitCode.setIcon(iconBitCode);
        imageBitCode.repaint();
		}
        else if (ev.getActionCommand()=="Validate Identity"){
    	
       
        BitCode left;
        //BitCode right;
        String id;
        
        try {
        	databaseWrapper db = new databaseWrapper(); 
        	//db.addId("testi");
        	//db.addLeft("testi", bc[0]);
        	/*
        	
        	
        	double hd;
        	
			while(db.rs.next()){
        	
        	id = db.getId();
			left = db.getLeftCode();
			bc[1] = left;
			//right = db.getRightCode(id);
			//eyeLoaded = true;
			
			hd = BitCode.hammingDistance(bc[0],bc[1]);
        	
			if(hd<1){  
			hamming_result.setText("Identity Verified as " +id + ": Hamming Distance "+hd);
        	hamming_result.setEnabled(true);
			}
			}
			*/
			
		} catch (DbException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Syntax Error");
			e.printStackTrace();
		}
        
        
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
