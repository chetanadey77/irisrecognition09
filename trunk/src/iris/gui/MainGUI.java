package iris.gui;
import iris.gui.*;/**
 * 
 */

import java.awt.*;
import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author en108
 *
 */
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
        static JLabel image = new JLabel("Image will go here");
        static JButton getimageone = new JButton("Click to get image");
        static JButton editimageone = new JButton("Edit image");
        static ImageIcon icon;
        static EyeData topeye;
        public MainFrame()
        
        {       
                setTitle("Iris Recognition");
                setSize(500,600);
                setLayout(new FlowLayout());
                editimageone.setEnabled(false);
                image.setPreferredSize(new Dimension(320,280));
                getContentPane().add(getimageone);
                getContentPane().add(image);
                getContentPane().add(editimageone);
                
                getimageone.addActionListener(new ActionListener() {
                        

                  
                        public void actionPerformed(ActionEvent arg0) {
                                // TODO Auto-generated method stub
                                System.out.println("get image button is pressed");
                                icon = gtImage();
                                image.setIcon(icon);
                                image.setEnabled(true);
                                image.setVisible(true);
                                
                                editimageone.setEnabled(true);
                        }
                });
                editimageone.addActionListener(new ActionListener() {
                        

                    
                        public void actionPerformed(ActionEvent arg0) {
                                // TODO Auto-generated method stub
                                System.out.println("edit image button is pressed");
                                final BufferedImage eye = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY) ; //= new BufferedImage(null, null, rootPaneCheckingEnabled, null);
                                //Image tempimage = image;
                                eye.getGraphics().drawImage( icon.getImage(),0,0,null);
                                
                                
                                EventQueue.invokeLater(new Runnable()
                                {
                                                public void run()
                                                {
                                                        EditFrame editframe = new EditFrame(eye,eye);
                                                        //editframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                        editframe.setVisible(true);
                                                }
                                });                     }
                });
                
                        
        }
        public ImageIcon gtImage() {
                JFileChooser filedialog = new JFileChooser();
                filedialog.showOpenDialog(this.getContentPane());
                System.out.println(filedialog.getSelectedFile().getPath());
                ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
                return icon;    
        }
        public void setEyePos(EyeData ed) {
        	topeye = ed;
        	
        	
        }   
        public void setEyePos(int ix,int iy, int irad, int ox,int oy, int orad){
        	topeye.inner.x = ix;
        	topeye.inner.y = iy;
        	topeye.inner.radius = irad;
        	topeye.outer.x = ox;
        	topeye.outer.y = oy;
        	topeye.outer.radius = orad;
        	
        }
     
                
        
}
