package iris.gui;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestingGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable()
         {
                         public void run()
                         {
                                 MainFrame2 frame = new MainFrame2();
                                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                 frame.setVisible(true);
                         }
         } );
	}

}
class MainFrame2 extends JFrame
{
	static JLabel imageEyeOne = new JLabel();
	static JLabel imageEyeTwo = new JLabel();
	static JLabel imageIrisOne = new JLabel();
	static JLabel imageIrisTwo = new JLabel();
	static JLabel imageBitCodeOne = new JLabel();
	static JLabel imageBitCodeTwo = new JLabel();
	
    static JButton getimageOne = new JButton("Click to load image");
    static JButton getimageTwo = new JButton("Click to load image");
    static JButton editimageOne = new JButton("Edit image");
    static JButton editimageTwo = new JButton("Edit image");
    static JButton bitcodeimageOne = new JButton("Bitcode image");
    static JButton bitcodeimageTwo = new JButton("Bitcode image");
    static JTextField hamming_result = new JTextField(30);
    static JButton compare = new JButton("Click to compare eyes");

    static ImageIcon iconEyeOne = new ImageIcon();
    static ImageIcon iconEyeTwo = new ImageIcon();
    static ImageIcon iconIrisOne = new ImageIcon();
    static ImageIcon iconIrisTwo = new ImageIcon();
    static ImageIcon iconBitCodeOne = new ImageIcon();
    static ImageIcon iconBitCodeTwo = new ImageIcon();
    
    static BufferedImage biEyeOne;
    static BufferedImage biEyeTwo;
    static BufferedImage biIrisOne;
    static BufferedImage biIrisTwo;
    static BufferedImage biBitCodeOne;
    static BufferedImage biBitCodeTwo;
    
    static EditDialog editdialogOne = null;
    static EditDialog editdialogTwo = null;
    
    static BitCode bcOne;
    static BitCode bcTwo;
    static EyeDataType eyeOne;
    static EyeDataType eyeTwo;
    
    static final int FRAME_WIDTH = 900;
    static final int FRAME_HEIGHT = 850;
    
    public MainFrame2()
        
        {       
                setTitle("Iris Recognition");
                setSize(FRAME_WIDTH,FRAME_HEIGHT);
                setLayout(new FlowLayout());
//              create button panel for the first image
                JPanel panelbuttonOne = new JPanel();
                editimageOne.setEnabled(false);
                bitcodeimageOne.setEnabled(false);
                panelbuttonOne.setLayout(new GridLayout(3,1));
                panelbuttonOne.add(getimageOne);
                panelbuttonOne.add(editimageOne);
                panelbuttonOne.add(bitcodeimageOne);
//              create button panel for the second image
                JPanel panelbuttonTwo = new JPanel();
                editimageTwo.setEnabled(false);
                bitcodeimageTwo.setEnabled(false);
                panelbuttonTwo.setLayout(new GridLayout(3,1));
                panelbuttonTwo.add(getimageTwo);
                panelbuttonTwo.add(editimageTwo);
                panelbuttonTwo.add(bitcodeimageTwo);
                // set the blank images to something
                
                biEyeOne = new BufferedImage(320,280,BufferedImage.TYPE_BYTE_GRAY);
                biEyeTwo = new BufferedImage(320,280,BufferedImage.TYPE_BYTE_GRAY);
                biIrisOne = new BufferedImage(320,280,BufferedImage.TYPE_BYTE_GRAY);
                biIrisTwo = new BufferedImage(320,280,BufferedImage.TYPE_BYTE_GRAY);
                biBitCodeOne = new BufferedImage(768,24,BufferedImage.TYPE_BYTE_GRAY);
                biBitCodeTwo = new BufferedImage(768,24,BufferedImage.TYPE_BYTE_GRAY);
                
                //set images to alternate squares of grey and white
                int square_size=10;
                int grey = 0xe0e0e0;
                int white = 0xffffff;
                int colour;
                for(int x =0;x<320;x++)
                	for(int y=0;y<280;y++) 
                		{
                			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                			biEyeOne.setRGB(x,y,colour);
                			biEyeTwo.setRGB(x,y,colour);
                			biIrisOne.setRGB(x,y,colour);
                			biIrisTwo.setRGB(x,y,colour);
                		}
                
                //Do the same for the bitcode images
                square_size=8;
                for(int x =0;x<768;x++)
                	for(int y=0;y<24;y++) 
                		{
                			colour=((x/square_size +y/square_size)%2==1)?grey:white;
                			biBitCodeOne.setRGB(x,y,colour);
                			biBitCodeTwo.setRGB(x,y,colour);
                		}
                
                
                iconEyeOne.setImage(biEyeOne);
                iconEyeTwo.setImage(biEyeTwo);
                iconIrisOne.setImage(biIrisOne);
                iconIrisTwo.setImage(biIrisTwo);
                iconBitCodeOne.setImage(biBitCodeOne);
                iconBitCodeTwo.setImage(biBitCodeTwo);
                
                
                imageEyeOne.setIcon(iconEyeOne);
                imageEyeTwo.setIcon(iconEyeTwo);
                imageIrisOne.setIcon(iconIrisOne);
                imageIrisTwo.setIcon(iconIrisTwo);
                imageBitCodeOne.setIcon(iconBitCodeOne);
                imageBitCodeTwo.setIcon(iconBitCodeTwo);
                
                
//              create a panel for the first eye
                JPanel paneleyeOne = new JPanel();
                paneleyeOne.add(panelbuttonOne);
                paneleyeOne.add(imageEyeOne);
                paneleyeOne.add(imageIrisOne);
//              create a panel for the second eye
                JPanel paneleyeTwo = new JPanel();
                paneleyeTwo.add(panelbuttonTwo);
                paneleyeTwo.add(imageEyeTwo);
                paneleyeTwo.add(imageIrisTwo);
                
                getContentPane().add(paneleyeOne);
                getContentPane().add(imageBitCodeOne);
                compare.setEnabled(false);
                getContentPane().add(compare);
                hamming_result.setEnabled(false);
                getContentPane().add(hamming_result);
                getContentPane().add(paneleyeTwo);
                getContentPane().add(imageBitCodeTwo);
                //Handle the get Image buttons
                getimageOne.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                    	iconEyeOne = gtImage();
                        imageEyeOne.setIcon(iconEyeOne);
                        editimageOne.setEnabled(true);
                    }
                });
                
                getimageTwo.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		iconEyeTwo = gtImage();
                		imageEyeTwo.setIcon(iconEyeTwo);
                		editimageTwo.setEnabled(true);
                	}
                });
                
                editimageOne.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		//System.out.println("edit image button is pressed");
                        biEyeOne = new BufferedImage(imageEyeOne.getWidth(),
                        									imageEyeOne.getHeight(),
                        									BufferedImage.TYPE_BYTE_GRAY) ; //= new BufferedImage(null, null, rootPaneCheckingEnabled, null);
                        biEyeOne.getGraphics().drawImage( iconEyeOne.getImage(),0,0,null);
                        if (editdialogOne == null) editdialogOne = new EditDialog(MainFrame2.this,biEyeOne);
                            
                            editdialogOne.setVisible(true);
                        	//System.out.println("returned from edit");
                            eyeOne = editdialogOne.getEyeData();
                        	copyIris(biEyeOne,biIrisOne,eyeOne);
                        	 iconIrisOne.setImage(biIrisOne);
                             imageIrisOne.setIcon(iconIrisOne);
                             imageIrisOne.repaint();
                             bitcodeimageOne.setEnabled(true);
                            }
            });
                editimageTwo.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		//System.out.println("edit image2 button is pressed");
                    biEyeTwo = new BufferedImage(imageEyeTwo.getWidth(),
                    									imageEyeTwo.getHeight(),
                    									BufferedImage.TYPE_BYTE_GRAY) ; //= new BufferedImage(null, null, rootPaneCheckingEnabled, null);
                    biEyeTwo.getGraphics().drawImage( iconEyeTwo.getImage(),0,0,null);
                    if (editdialogTwo == null) editdialogTwo = new EditDialog(MainFrame2.this,biEyeTwo);
                        
                        editdialogTwo.setVisible(true);
                        eyeTwo = editdialogTwo.getEyeData();
                    	copyIris(biEyeTwo,biIrisTwo,eyeTwo);
                    	 iconIrisTwo.setImage(biIrisTwo);
                         imageIrisTwo.setIcon(iconIrisTwo);
                         imageIrisTwo.repaint();
                         bitcodeimageTwo.setEnabled(true);
                        }
        });
                bitcodeimageOne.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		long startTime=System.currentTimeMillis(); //calculate runtime
                		BitcodeGenerator b = new BitcodeGenerator();
                		bcOne =  b.getBitcode(biEyeOne,eyeOne);
                		biBitCodeOne = bcOne.getBitCodeImage(768,24,8);
                		iconBitCodeOne.setImage(biBitCodeOne);
                        imageBitCodeOne.setIcon(iconBitCodeOne);
                        imageBitCodeOne.repaint();
                		System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
                	
                        }
            });
                bitcodeimageTwo.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent arg0) {
            		long startTime=System.currentTimeMillis(); //calculate runtime
            		BitcodeGenerator b = new BitcodeGenerator();
            		bcTwo =  b.getBitcode(biEyeTwo,eyeTwo.inner.x,eyeTwo.inner.y,eyeTwo.inner.radius,eyeTwo.outer.x,eyeTwo.outer.y,eyeTwo.outer.radius);
               		biBitCodeTwo = bcTwo.getBitCodeImage(768,24,8);
            		iconBitCodeTwo.setImage(biBitCodeTwo);
                    imageBitCodeTwo.setIcon(iconBitCodeTwo);
                    imageBitCodeTwo.repaint();
            		
            		System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
            		compare.setEnabled(true);
            	
                    }
        });
                compare.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		long startTime=System.currentTimeMillis(); //calculate runtime
                		double hd = BitCode.hammingDistance(bcOne,bcTwo);
                		hamming_result.setText("Hamming Distance "+hd);
                		hamming_result.setEnabled(true);
                		
                		System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
                	
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
        File f = new File(new File("./images/").getCanonicalPath());
        filedialog.setCurrentDirectory(f);
    } catch (IOException e) {
    }

        filedialog.showOpenDialog(this.getContentPane());
        //System.out.println(filedialog.getSelectedFile().getPath());
        ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        return icon;    
    }
}

    