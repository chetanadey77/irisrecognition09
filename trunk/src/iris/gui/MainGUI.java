package iris.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
	static JLabel imageEyeOne = new JLabel();
	static JLabel imageEyeTwo = new JLabel();
	static JLabel imageIrisOne = new JLabel();
	static JLabel imageIrisTwo = new JLabel();

    static JButton getimageOne = new JButton("Click to load image");
    static JButton getimageTwo = new JButton("Click to load image");
    static JButton editimageOne = new JButton("Edit image");
    static JButton editimageTwo = new JButton("Edit image");
    static JButton bitcodeimageOne = new JButton("Bitcode image");
    static JButton bitcodeimageTwo = new JButton("Bitcode image");
    
    static JButton compare = new JButton("Click to compare eyes");

    static ImageIcon iconEyeOne = new ImageIcon();
    static ImageIcon iconEyeTwo = new ImageIcon();
    static ImageIcon iconIrisOne = new ImageIcon();
    static ImageIcon iconIrisTwo = new ImageIcon();
    
    static BufferedImage biEyeOne;
    static BufferedImage biEyeTwo;
    static BufferedImage biIrisOne;
    static BufferedImage biIrisTwo;
    
    static EditDialog editdialogOne = null;
    static EditDialog editdialogTwo = null;
    
    
    static EyeData eyeOne;
    static EyeData eyeTwo;
    
    static final int FRAME_WIDTH = 900;
    static final int FRAME_HEIGHT = 750;
    
    public MainFrame()
        
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
                iconEyeOne.setImage(biEyeOne);
                iconEyeTwo.setImage(biEyeTwo);
                iconIrisOne.setImage(biIrisOne);
                iconIrisTwo.setImage(biIrisTwo);
                imageEyeOne.setIcon(iconEyeOne);
                imageEyeTwo.setIcon(iconEyeTwo);
                imageIrisOne.setIcon(iconIrisOne);
                imageIrisTwo.setIcon(iconIrisTwo);
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
                compare.setEnabled(false);
                getContentPane().add(compare);
                getContentPane().add(paneleyeTwo);
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
                        if (editdialogOne == null) editdialogOne = new EditDialog(MainFrame.this,biEyeOne);
                            
                            editdialogOne.setVisible(true);
                        	//System.out.println("returned from edit");
                        	copyIris(biEyeOne,biIrisOne,editdialogOne.getEyeData());
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
                    if (editdialogTwo == null) editdialogTwo = new EditDialog(MainFrame.this,biEyeTwo);
                        
                        editdialogTwo.setVisible(true);
                    	//System.out.println("returned from edit");
                    	copyIris(biEyeTwo,biIrisTwo,editdialogTwo.getEyeData());
                    	 iconIrisTwo.setImage(biIrisTwo);
                         imageIrisTwo.setIcon(iconIrisTwo);
                         imageIrisTwo.repaint();
                         bitcodeimageTwo.setEnabled(true);
                        }
        });
        }
    private static void copyIris(BufferedImage bifrom, BufferedImage bito, EyeData ed )
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
        JFileChooser filedialog = new JFileChooser();
        filedialog.showOpenDialog(this.getContentPane());
        //System.out.println(filedialog.getSelectedFile().getPath());
        ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        return icon;    
    }
}

              