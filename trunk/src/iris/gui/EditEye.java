package iris.gui;

import java.awt.*;
//import java.awt.Event.*;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

class EditDialog extends JDialog
{
        JLabel image = new JLabel();
         ImageIcon icon = new ImageIcon();
         JButton setimage = new JButton("Click to send");
         JPanel inner = new JPanel();
         JPanel outer = new JPanel();
         int ix = 181;  //Defaults for image001_01_01
         int iy = 135;
         int irad = 39;
         int ox = 178;
         int oy = 139;
         int orad = 97;
         SpinnerNumberModel modelix = new SpinnerNumberModel(ix,0,319,1);
         SpinnerNumberModel modeliy = new SpinnerNumberModel(iy,0,279,-1);
         SpinnerNumberModel modelox = new SpinnerNumberModel(ix,0,319,1);
         SpinnerNumberModel modeloy = new SpinnerNumberModel(iy,0,279,-1);
         SpinnerNumberModel modelir = new SpinnerNumberModel(irad,0,159,1);
         SpinnerNumberModel modelor = new SpinnerNumberModel(orad,0,139,1);
         JSpinner innerx = new JSpinner(modelix);
         JSpinner innery = new JSpinner(modeliy);
         JSpinner innerr = new JSpinner(modelir);
         JSpinner outerx = new JSpinner(modelox);
         JSpinner outery = new JSpinner(modeloy);
         JSpinner outerr = new JSpinner(modelor);
        
        //static MainFrame callingFrame;
         BufferedImage sbi;
         BufferedImage fixedsbi; //holds initial copy
    
        public EditDialog(JFrame owner, BufferedImage bi)
        
        {      
        	super(owner, "Find the pupil and iris",true);
        		//bi = gaussian_blur(bi,3);
        		sbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        		
        		fixedsbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        		for(int ty=0;ty<bi.getHeight();ty++)
        		{
        			for(int tx=0;tx<bi.getWidth();tx++)
        			{
        				int black = bi.getRGB(tx,ty);
        				fixedsbi.setRGB(tx,ty,black);
        				sbi.setRGB(tx,ty,black);
        			}
                			
        		}
                setTitle("Centre Pupil and Iris");
                setSize(500,600);
                setLayout(new FlowLayout());
                
                inner.add(new JLabel("Inner Circle"));
                inner.add(innerx);
                inner.add(innery);
                inner.add(innerr);
                
                //outer.add(new JLabel("Outer Circle"));
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
                
                setimage.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent arg0) {
                            // TODO Auto-generated method stub
                            //System.out.println("set image button is pressed");
                            ix= ((Integer)innerx.getValue()).intValue();
                			iy= ((Integer)innery.getValue()).intValue();
                			irad= ((Integer)innerr.getValue()).intValue();
                			ox= ((Integer)outerx.getValue()).intValue();
                			oy= ((Integer)outery.getValue()).intValue();
                			orad= ((Integer)outerr.getValue()).intValue();
                            setVisible(false);
                           
                            
                          
                    }
            });
                        
        }
        public EyeData getEyeData()
        {
        	EyeData ed = new EyeData(ix,iy,irad,ox,oy,orad);
        	return ed;
        }
        public BufferedImage gaussian_blur(BufferedImage bi,int pixels)
        {
        	float[] matrix = new float[(pixels*2+1)*(pixels*2+1)] ;
        	double sigma = (double) pixels / 3.0;
        	float normalise=0.0f;
        	double sigma_sq = sigma*sigma;
        	for (int x = -pixels; x<=pixels;x++)
        		for (int y = -pixels; y<=pixels;y++)
        		{
        			matrix[x+pixels+(y+pixels)*(2*pixels+1)]=(float)Math.exp(-(double)(x*x+y*y)/(sigma_sq*2.0));
        			normalise += matrix[x+pixels+(y+pixels)*(2*pixels+1)];   	
        		}
        	for (int x = -pixels; x<=pixels;x++)
        		for (int y = -pixels; y<=pixels;y++)
        		{
        			matrix[x+pixels+(y+pixels)*(2*pixels+1)]= matrix[x+pixels+(y+pixels)*(2*pixels+1)] / normalise;   	
        		}
        	BufferedImageOp op = new ConvolveOp(new Kernel(2*pixels+1, 2*pixels+1,matrix));
        	BufferedImage nbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        	op.filter(bi,nbi);
        	return nbi;
        	
        }
        public CircleType find_circle(BufferedImage bi, int pixel_blur, Bounds bounds)
        {
        	for (int radius= bounds.rmin;radius<=bounds.rmax;radius++)
        		for (int x =bounds.xmin + radius;x<=bounds.xmax-radius;x++)
        			for (int y =bounds.ymin + radius;y<=bounds.ymax-radius;y++)
        			{
        				if ()
        			}
                			
        	CircleType c = new CircleType();
        	return c;	
        }
}
// controls how much of the image will be searched for a circle
// in particular used to find pupil (which we know is inside iris)
class Bounds
{
	public int xmin;
	public int xmax;
	
	public int ymin;
	public int ymax;
	
	public int rmin;
	public int rmax;
	
	public int thetamin;
	public int thetamax;
}

       