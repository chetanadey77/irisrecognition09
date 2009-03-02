package iris.gui;

import iris.imageToBitcode.BitcodeGenerator;
import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.LocateIris;
import iris.imageToBitcode.UnWrapper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.synth.ColorType;

import unittest.ImageToBitcode.ImageSaverLoader;

public class MainGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable()
         {
                         public void run()
                         {
                                 MainFrame frame = new MainFrame();
                                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                 frame.setVisible(true);
                         }
         });
	}

}


class MainFrame extends JFrame
{
	static JLabel imageEye[] = new  JLabel[2];
	static JLabel imageUnwrappedEye[] = new  JLabel[2];
	static JLabel imageBitCode[] = new  JLabel[2];
	
	static JButton getimage[] = new JButton[2];
	
	static ImageIcon iconEye[] = new ImageIcon[2];
	static ImageIcon iconUnwrappedEye[] = new ImageIcon[2];
	static ImageIcon iconBitCode[] = new ImageIcon[2];
	
	
    static JTextField hamming_result = new JTextField(30);
        
    static BufferedImage biEye[] = new BufferedImage[2];
    static BufferedImage biUnwrappedEye[] = new BufferedImage[2];
    static BufferedImage biBitCode[] = new BufferedImage[2];
  
    
    static BitCode[] bc = new BitCode[2];
    static EyeDataType eyeData[] = new EyeDataType[2];
  
    static final int FRAME_WIDTH = 900;
    static final int FRAME_HEIGHT = 850;
    
    static boolean[] eyeLoaded = new boolean[2];
    
    public MainFrame()
        
        {       
    			
    			getimage[1] = new JButton("Load eye image");
    			//final Color WHITE = new Color(255,255,255);
                setTitle("Iris Recognition");
                setSize(FRAME_WIDTH,FRAME_HEIGHT);
                setBackground(Color.WHITE);
                setLayout(new FlowLayout());
                JPanel panelEyeImage[] = new JPanel[2];
                JPanel panelWhole[] = new JPanel[2];
                JPanel panelData[] = new JPanel[2];
                JPanel panelUnwrap[] = new JPanel[2];
                JPanel panelBitcode[] = new JPanel[2];
                
//              create panel for each section in turn
                for (int n = 0; n<2;n++)
                {
                	panelEyeImage[n] = new JPanel();
                    panelWhole[n] = new JPanel();
                    panelData[n] = new JPanel();
                    panelUnwrap[n] = new JPanel();
                    panelBitcode[n] = new JPanel();
                	imageEye[n] = new  JLabel();
                	imageUnwrappedEye[n] = new  JLabel();
                	imageBitCode[n] = new  JLabel();
                	iconEye[n] = new ImageIcon();
                	iconUnwrappedEye[n] = new ImageIcon();
                	iconBitCode[n] = new ImageIcon();
                	eyeLoaded[n] = false;
                	getimage[n] = new JButton("Load eye image");
                	getimage[n].setSize(320, 30);
                	getimage[n].setEnabled(true);
                	
                	biEye[n] = new BufferedImage(320,280,BufferedImage.TYPE_INT_RGB);
                	biUnwrappedEye[n]  = new BufferedImage(512,128,BufferedImage.TYPE_INT_RGB);
                	biBitCode[n] = new BufferedImage(512,128,BufferedImage.TYPE_BYTE_GRAY);
                            
                              
                
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
                
                	iconEye[n].setImage(biEye[n]);
                	iconUnwrappedEye[n].setImage(biUnwrappedEye[n]);
                	iconBitCode[n].setImage(biBitCode[n]);
                
                	imageEye[n].setIcon(iconEye[n]);
                	imageUnwrappedEye[n].setIcon(iconUnwrappedEye[n]);
                	imageBitCode[n].setIcon(iconBitCode[n]);
                	
                	panelEyeImage[n].setLayout(new BorderLayout());
                	panelEyeImage[n].setBackground(Color.WHITE);
                	panelEyeImage[n].add(getimage[n],BorderLayout.NORTH);
                	panelEyeImage[n].add(imageEye[n],BorderLayout.SOUTH);
                	
                	panelUnwrap[n].setLayout(new BorderLayout());
                	panelUnwrap[n].setBackground(Color.WHITE);
                	panelUnwrap[n].add(new JLabel("Unwrapped iris"),BorderLayout.NORTH);
                	panelUnwrap[n].add(imageUnwrappedEye[n],BorderLayout.SOUTH);
                	panelBitcode[n].setLayout(new BorderLayout());
                	panelBitcode[n].setBackground(Color.WHITE);
                	panelBitcode[n].add(new JLabel("Bitcode"),BorderLayout.NORTH);
                	panelBitcode[n].add(imageBitCode[n],BorderLayout.SOUTH);
                	panelData[n].setLayout(new BorderLayout());
                	panelData[n].setBackground(Color.WHITE);
                	panelData[n].add(panelUnwrap[n],BorderLayout.NORTH);
                	panelData[n].add(panelBitcode[n],BorderLayout.SOUTH);
                	
                	//panelWhole[n].setLayout(new GridLayout(1,2));
                	panelWhole[n].setBackground(Color.WHITE);
                	panelWhole[n].setLayout(new BorderLayout());
                	panelWhole[n].add(panelEyeImage[n],BorderLayout.WEST);
                	panelWhole[n].add(panelData[n],BorderLayout.EAST);
                }
                JPanel background = new JPanel();
                background.setBackground(Color.WHITE);
                background.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
                getContentPane().add(background);
                hamming_result.setEnabled(false);
                background.add(panelWhole[0]);
                background.add(hamming_result);
                background.add(panelWhole[1]);
                
                getimage[0].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                    	iconEye[0] = gtImage();
                    	long startTime=System.currentTimeMillis(); //calculate runtime
                        imageEye[0].setIcon(iconEye[0]);
                        eyeLoaded[0] = true;
                        biEye[0].getGraphics().drawImage( iconEye[0].getImage(),0,0,null);
                        
                        eyeData[0] = LocateIris.find_iris(biEye[0]);
                        UnWrapper uw = new UnWrapper();
                        biUnwrappedEye[0] = UnWrapper.unWrap(uw.originalWithGuides(biEye[0],eyeData[0]),eyeData[0],512,128);//biUnwrappedEye[0].getWidth(),biUnwrappedEye[0].getHeight());
                        iconUnwrappedEye[0].setImage(biUnwrappedEye[0]);
                        imageUnwrappedEye[0].setIcon(iconUnwrappedEye[0]);
                        imageUnwrappedEye[0].repaint();
                		BitcodeGenerator b = new BitcodeGenerator();
                		bc[0] =  b.getBitcode(biEye[0],eyeData[0]);
                		biBitCode[0] = bc[0].getBitCodeImage(512,128,32);
                		iconBitCode[0].setImage(biBitCode[0]);
                        imageBitCode[0].setIcon(iconBitCode[0]);
                        imageBitCode[0].repaint();
                        System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
                	
                		if (eyeLoaded[1])
                		{
                			double hd = BitCode.hammingDistance(bc[0],bc[1]);
                        	hamming_result.setText("Hamming Distance "+hd);
                        	hamming_result.setEnabled(true);
                        }
                    }
                });
        }
    private static void copyIris(BufferedImage bifrom, BufferedImage bito, EyeDataType ed )
    {
    	int colour;
        for(int x =0;x<320;x++)
        	for(int y=0;y<280;y++) 
        		{
        			if (ed.outer.radius * ed.outer.radius < (	(x - ed.outer.x)*(x - ed.outer.x) +
        														(y - ed.outer.y)*(y - ed.outer.y))) colour=0;
        			else if (ed.inner.radius * ed.inner.radius > (	(x - ed.inner.x)*(x - ed.inner.x) +
																(y - ed.inner.y)*(y - ed.inner.y))) colour=0xffffff;
        			else colour = bifrom.getRGB(x,y);
        			bito.setRGB(x,y,colour);
        		}
    }
    private ImageIcon gtImage() {
    	
        JFileChooser filedialog = new  JFileChooser();
        try{
        File f = new File(new File("./images/automatic/").getCanonicalPath());
        filedialog.setCurrentDirectory(f);
    } catch (IOException e) {
    }

        filedialog.showOpenDialog(this.getContentPane());
        //System.out.println(filedialog.getSelectedFile().getPath());
        ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        return icon;    
    }
}

              