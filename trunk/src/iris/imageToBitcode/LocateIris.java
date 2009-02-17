package iris.imageToBitcode;


import iris.gui.CircleType;
import iris.gui.EyeDataType;
import unittest.ImageToBitcode.ImageSaverLoader;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class LocateIris {
	public static BufferedImage gaussian_blur(BufferedImage bi,int pixels)
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
    public static CircleType find_circle(BufferedImage bi, int pixel_blur, char octant,Bounds bounds)
    {
    	BufferedImage bigb = gaussian_blur(bi,3);
    	CircleList cl = new CircleList(10);
    	CircleType c = new CircleType();
    	double max_diff = 0.0;
    	double diff = 0.0;
    	int xo=0,yo=0,ro=0;
    	for (int radius= bounds.rmin;radius<=bounds.rmax;radius++)
    	{
    		for (int x =bounds.xmin + radius;x<=bounds.xmax-radius;x++)
    		{
    			for (int y =bounds.ymin + radius;y<=bounds.ymax-radius;y++)
    			{
    				diff = Math.abs(loop_integral(bigb,x,y,radius,octant)- loop_integral(bigb,x,y,radius-1,octant));
    				if (diff>max_diff) 
    				{
    					max_diff = cl.add_circle(new CircleType(x,y,radius,diff));
    					//xo=x; yo=y; ro=radius;
    				}
    			}
    		}
    	}
    	 Graphics g  = bi.createGraphics();
    	 for(int q=0; q<cl.get_size();q++)
    		 g.drawOval(cl.get_circle(q).x-cl.get_circle(q).radius, 
    				 cl.get_circle(q).y-cl.get_circle(q).radius,
    				 cl.get_circle(q).radius,cl.get_circle(q).radius);
    	 ImageSaverLoader isl = new ImageSaverLoader();
    	 isl.saveImage(bi, "locateiris.gif");
        c.x = xo;
        c.y = yo;
        c.radius = ro;
    	return c;	
    }
    public static double loop_integral(BufferedImage bi, int cenx, int ceny, int r, char octant)
    {
    	int total=0;
    	int count =0;
    	//Algorthim modified from Bresenham 1977
    	int x=0;
    	int y=r;
    	double d =5.0/ 4.0 - (double) r;
    	//normally will do 1/8th of the circle and then test 8
    	//versions but first and last only get tested 4 times
    	if ((octant & 129)>0) {total+=bi.getRGB(cenx+x,ceny+y)&255;count++;}
    	if ((octant & 6)>0) {total+=bi.getRGB(cenx+y,ceny+x)&255;count++;}
    	if ((octant & 24)>0) {total+=bi.getRGB(cenx+x,ceny-y)&255;count++;}
    	if ((octant & 96)>0) {total+=bi.getRGB(cenx-y,ceny+x)&255;count++;}
    	while(y>x)
    	{
    		if (d<0) d+= 2.0*(double)x+3.0;
    		else
    		{
    			d += 2.0*(double)(x-y)+5.0;
    			y--;
    		}
    		x++;
    		if ((octant & 1)>0) {total+=bi.getRGB(cenx+x,ceny+y)&255;count++;}
    		if ((octant & 2)>0) {total+=bi.getRGB(cenx+y,ceny+x)&255;count++;}
    		if ((octant & 4)>0) {total+=bi.getRGB(cenx+y,ceny-x)&255;count++;}
    		if ((octant & 8)>0) {total+=bi.getRGB(cenx+x,ceny-y)&255;count++;}
    		if ((octant & 16)>0) {total+=bi.getRGB(cenx-x,ceny+y)&255;count++;}
    		if ((octant & 32)>0) {total+=bi.getRGB(cenx-y,ceny+x)&255;count++;}
    		if ((octant & 64)>0) {total+=bi.getRGB(cenx-y,ceny-x)&255;count++;}
    		if ((octant & 128)>0) {total+=bi.getRGB(cenx-x,ceny-y)&255;count++;}
    		
    	}
    	if(y==x)
    	{
    		if ((octant & 3)>0) {total+=bi.getRGB(cenx+x,ceny+x)&255;count++;}
    		if ((octant & 12)>0) {total+=bi.getRGB(cenx+x,ceny-x)&255;count++;}
    		if ((octant & 48)>0) {total+=bi.getRGB(cenx-x,ceny-x)&255;count++;}
    		if ((octant & 192)>0) {total+=bi.getRGB(cenx-x,ceny+x)&255;count++;}
    	}
    	
    	
    	if (count>0) return (double) (total) /(double)count;
    	else return 0;
    }
    public static EyeDataType find_iris(BufferedImage bi)
    {
    	EyeDataType ed = new EyeDataType();
    	Bounds b = new Bounds();
        int blur=4;
        b.rmin = 20;// specification is iris must be at least 70 pixels in diameter
        //b.rmin=45;//just for testing
        b.xmin = blur;
        b.ymin = blur;
        b.xmax = bi.getWidth() -blur;
        b.ymax = bi.getHeight() -blur;
        b.rmax = (bi.getHeight())/2;
        
        
        CircleType c = find_circle(bi,blur,(char)102,b);
        ed.outer = c;
        
        /*blur=2;
        b.xmin = bl;
        b.ymin = blur;
        b.xmax = bi.getWidth() - blur;
        b.ymax = bi.getHeight() - blur;
        b.rmax = b.xmax-b.xmin;
        b.rmin = 30;// specification is iris must be at least 70 pixels in diameter
        
        c = find_circle(bi,10,(char)231,b);
        */
        return ed;
    }
    
}
//controls how much of the image will be searched for a circle
//in particular used to find pupil (which we know is inside iris)
class Bounds
{
public int xmin;
public int xmax;

public int ymin;
public int ymax;

public int rmin;
public int rmax;
}

