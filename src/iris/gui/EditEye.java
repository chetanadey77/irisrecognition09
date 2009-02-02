package iris.gui;

import java.awt.*;
//import java.awt.Event.*;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.image.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import javax.swing.*;
//import javax.swing.event.*;

//import javax.swing.Icon;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSpinner.NumberEditor;

public class EditEye {

}
class EditFrame extends JFrame
{
        static JLabel image = new JLabel();
        static ImageIcon icon = new ImageIcon();
        static JButton setimage = new JButton("Click to send");
        static JPanel inner = new JPanel();
        static JPanel outer = new JPanel();
        static int ix = 180;
        static int iy = 100;
        static int irad = 40;
        static int ox = 180;
        static int oy = 160;
        static int orad = 50;
        static SpinnerNumberModel modelx = new SpinnerNumberModel(ix,0,319,1);
        static SpinnerNumberModel modely = new SpinnerNumberModel(iy,0,279,1);
        static SpinnerNumberModel modelir = new SpinnerNumberModel(irad,0,159,1);
        static SpinnerNumberModel modelor = new SpinnerNumberModel(orad,0,139,1);
        static JSpinner innerx = new JSpinner(modelx);
        static JSpinner innery = new JSpinner(modely);
        static JSpinner innerr = new JSpinner(modelir);
        static JSpinner outerx = new JSpinner(modelx);
        static JSpinner outery = new JSpinner(modely);
        static JSpinner outerr = new JSpinner(modelor);
        
        
        static BufferedImage sbi;
        //static JButton editimageone = new JButton("Edit image");
        //static Icon icon;
        public EditFrame(BufferedImage bi)
        
        {       sbi = bi;
                setTitle("Centre Pupil and Iris");
                setSize(500,600);
                setLayout(new FlowLayout());
                inner.add(new JLabel("Inner Circle"));
                
                inner.add(innerx);
                inner.add(innery);
                inner.add(innerr);
                outer.add(new JLabel("Outer Circle"));
                outer.add(outerx);
                outer.add(outery);
                outer.add(outerr);
               
                Graphics g  = sbi.createGraphics();
                g.drawOval(ix- irad, iy - irad, irad*2, irad*2);
                icon.setImage(sbi);
                image.setIcon(icon);
                image.setPreferredSize(new Dimension(320,280));
                
                getContentPane().add(image);
                getContentPane().add(inner);
                getContentPane().add(outer);
                getContentPane().add(setimage);
                
                


                
                innerx.addChangeListener(new ChangeListener() {
                        

             
                        public void stateChanged(ChangeEvent e) {
                                // TODO Auto-generated method stub
                                System.out.println("innerx button is pressed");
                                ix= ((Integer)innerx.getValue()).intValue();
                                Graphics g  = sbi.createGraphics();
                                g.drawOval(ix- irad, iy - irad, irad*2, irad*2);
                                icon.setImage(sbi);
                                image.setIcon(icon);
                                image.repaint();
                                
                                
                        }

                
                
                });
                
                
                        
        }
}
        /*
        public ImageIcon gtImage() {
                JFileChooser filedialog = new JFileChooser();
                filedialog.showOpenDialog(this.getContentPane());
                System.out.println(filedialog.getSelectedFile().getPath());
                ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
                return icon;    
        }
                
                
*/