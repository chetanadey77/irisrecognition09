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
//import javax.swing.JSpinner.NumberEditor;

//public class EditEye {

//}
/*
class EyewithCircle extends JComponent
{
	public EyewithCircle(BufferedImage bi, int x, int y, int r)
	{
		
	}
	public void paintComponent(Graphics g) 
	{
		if (image == null) return;
		g.drawImage(image,0,0,null);
		g.drawOval(x- r, y -r, r*2, r*2);
	}
	private Image image
}*/
class EditFrame extends JFrame
{
        static JLabel image = new JLabel();
        static ImageIcon icon = new ImageIcon();
        static JButton setimage = new JButton("Click to send");
        static JPanel inner = new JPanel();
        static JPanel outer = new JPanel();
        static int ix = 159;
        static int iy = 141;
        static int irad = 45;
        static int ox = 167;
        static int oy = 144;
        static int orad = 99;
        static SpinnerNumberModel modelix = new SpinnerNumberModel(ix,0,319,1);
        static SpinnerNumberModel modeliy = new SpinnerNumberModel(iy,0,279,1);
        static SpinnerNumberModel modelox = new SpinnerNumberModel(ix,0,319,1);
        static SpinnerNumberModel modeloy = new SpinnerNumberModel(iy,0,279,1);
        static SpinnerNumberModel modelir = new SpinnerNumberModel(irad,0,159,1);
        static SpinnerNumberModel modelor = new SpinnerNumberModel(orad,0,139,1);
        static JSpinner innerx = new JSpinner(modelix);
        static JSpinner innery = new JSpinner(modeliy);
        static JSpinner innerr = new JSpinner(modelir);
        static JSpinner outerx = new JSpinner(modelox);
        static JSpinner outery = new JSpinner(modeloy);
        static JSpinner outerr = new JSpinner(modelor);
        
        
        static BufferedImage sbi;
        static BufferedImage fixedsbi; //holds initial copy
        //static JButton editimageone = new JButton("Edit image");
        //static Icon icon;
        public EditFrame(BufferedImage bi,BufferedImage bi2)
        
        {       sbi = bi;
        		
        		fixedsbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        		for(int ty=0;ty<bi.getHeight();ty++)
        		{
        			for(int tx=0;tx<bi.getWidth();tx++)
        			{
        				int black = bi.getRGB(tx,ty);
        				fixedsbi.setRGB(tx,ty,black);
        			}
                			
        		}
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
                class DrawCircleAction implements ChangeListener
                {	
                	private boolean isinner;
                	public DrawCircleAction(boolean inner)
                	{
                		isinner = inner;
                	}
                	public void stateChanged(ChangeEvent e) {
                		int x,y,r;
                         // TODO Auto-generated method stub
                    //     System.out.println("innerx button is pressed");
                		if (isinner) { 
                			x= ((Integer)innerx.getValue()).intValue();
                			y= ((Integer)innery.getValue()).intValue();
                			r= ((Integer)innerr.getValue()).intValue();
                		}else{
                			x= ((Integer)outerx.getValue()).intValue();
                			y= ((Integer)outery.getValue()).intValue();
                			r= ((Integer)outerr.getValue()).intValue();
                		}
                		sbi = new BufferedImage(fixedsbi.getWidth(),fixedsbi.getHeight(),fixedsbi.getType());
                		for(int ty=0;ty<fixedsbi.getHeight();ty++)
                		{
                			for(int tx=0;tx<fixedsbi.getWidth();tx++)
                			{
                				int black = fixedsbi.getRGB(tx,ty);
                				sbi.setRGB(tx,ty,black);
                			}
                        			
                		}
                         Graphics g  = sbi.createGraphics();
                         Graphics2D g2 = (Graphics2D) g;
                         g2.drawOval(x- r, y -r, r*2, r*2);
                         icon.setImage(sbi);
                         image.setIcon(icon);
                         image.repaint();
                         
                         
                 }
                }
                
                DrawCircleAction innerAction = new DrawCircleAction(true);
                DrawCircleAction outerAction = new DrawCircleAction(false);
                innerx.addChangeListener(innerAction);
                innery.addChangeListener(innerAction);
                innerr.addChangeListener(innerAction);
                
                outerx.addChangeListener(outerAction);
                outery.addChangeListener(outerAction);
                outerr.addChangeListener(outerAction);
                
                inner.add(innery);
                inner.add(innerr);
                outer.add(new JLabel("Outer Circle"));
                outer.add(outerx);
                outer.add(outery);
                outer.add(outerr);
                
                

                /*
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
                
                */
                        
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