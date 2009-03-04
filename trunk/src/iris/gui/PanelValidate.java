package iris.gui;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.EyeDataType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class PanelValidate extends javax.swing.JPanel implements ActionListener {

	static JLabel imageEye;
	static JLabel imageUnwrappedEye;
	static JLabel imageBitCode;
	
	static JButton getimage;
	
	static ImageIcon iconEye;
	static ImageIcon iconUnwrappedEye;
	static ImageIcon iconBitCode;
	
	
    static JTextField hamming_result = new JTextField(30);
        
    static BufferedImage Eye;
    static BufferedImage UnwrappedEye;
    static BufferedImage ValidateBitCode;
  
    
    static BitCode[] bc;
    static EyeDataType eyeData;
    static Boolean eyeLoaded;
	
	public PanelValidate(int FRAME_WIDTH,int FRAME_HEIGHT) {
		    
 			
 			getimage= new JButton("Load eye image");
 			
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
             	imageEye = new  JLabel();
             	imageUnwrappedEye = new  JLabel();
             	imageBitCode = new  JLabel();
             	iconEye = new ImageIcon();
             	iconUnwrappedEye = new ImageIcon();
             	iconBitCode = new ImageIcon();
             	eyeLoaded = false;
             	getimage = new JButton("Load image to be validated");
             	getimage.setSize(320, 30);
             	getimage.setEnabled(true);
             	
             	Eye = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
             	UnwrappedEye  = new BufferedImage(512,128,BufferedImage.TYPE_INT_RGB);
             	ValidateBitCode = new BufferedImage(512,128,BufferedImage.TYPE_BYTE_GRAY);
                         
                           
          /*   
             //set images to alternate squares of grey and white
             	int square_size=10;
             	int grey = 0xf0f0f0;
             	int white = 0xffffff;
             	int colour;
             	for(int x =0;x<biEye[n].getWidth();x++)
             		for(int y=0;y<biEye[n].getHeight();y++) 
             		{
             			colour=((x/square_size +y/square_size)%2==1)?grey:white;
             			biEye[n].setRGB(x,y,colour);
             		}
             	square_size=8;
             	for(int x =0;x<biUnwrappedEye[n].getWidth();x++)
                 		for(int y=0;y<biUnwrappedEye[n].getHeight();y++) 
                 		{
                 			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                 			biUnwrappedEye[n].setRGB(x,y,colour);
                 		}
             	for(int x =0;x<biBitCode[n].getWidth();x++)
                     		for(int y=0;y<biBitCode[n].getHeight();y++) 
                     		{
                     			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                     			biBitCode[n].setRGB(x,y,colour);
                     		}
             */
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
             
             JPanel background = new JPanel();
             background.setBackground(Color.WHITE);
             background.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
             add(background);
             hamming_result.setEnabled(false);
             background.add(panelWhole);
             background.add(hamming_result);
             background.add(panelWhole);
             
             getimage.addActionListener(this);
             
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
