package iris.imageToBitcode;


import iris.gui.CircleType;
import iris.gui.EyeDataType;
import unittest.ImageToBitcode.ImageSaverLoader;

import java.awt.Font;
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
    	CircleList cl = new CircleList(50);
    	CircleType c = new CircleType();
    	double max_diff = 0.0;
    	double diff = 0.0;
    	int xo=0,yo=0,ro=0;
    	for (int radius= bounds.rmin;radius<=bounds.rmax;radius++)
    	{
    		for (int x = Math.max(radius,bounds.xmin);x<Math.min(bi.getWidth()-radius, bounds.xmax);x++)
    		{
    			for (int y =Math.max(radius, bounds.ymin) ;y<Math.min(bi.getHeight()-radius,bounds.ymax);y++)
    			{
    				diff = Math.abs(loop_integral(bigb,x,y,radius,octant)- loop_integral(bigb,x,y,radius-1,octant));
    				if (diff>max_diff) 
    				{
    					max_diff = diff;cl.add_circle(new CircleType(x,y,radius,diff));
    					xo=x; yo=y; ro=radius;
    				}
    			}
    		}
    	} 
    	//for(int q=0; q<cl.get_size();q++)
    	//	 bi = draw_part_circle(bi, cl.get_circle(q).x, cl.get_circle(q).y,cl.get_circle(q).radius, octant, 0xFFFFFF);
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
    		if ((octant & 16)>0) {total+=bi.getRGB(cenx-x,ceny-y)&255;count++;}
    		if ((octant & 32)>0) {total+=bi.getRGB(cenx-y,ceny-x)&255;count++;}
    		if ((octant & 64)>0) {total+=bi.getRGB(cenx-y,ceny+x)&255;count++;}
    		if ((octant & 128)>0) {total+=bi.getRGB(cenx-x,ceny+y)&255;count++;}
    		
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
    
    public static BufferedImage draw_part_circle(BufferedImage bi, int cenx, int ceny, int r, char octant,int colour)
    {
    	int total=0;
    	int count =0;
    	//Algorthim modified from Bresenham 1977
    	int x=0;
    	int y=r;
    	double d =5.0/ 4.0 - (double) r;
    	//normally will do 1/8th of the circle and then test 8
    	//versions but first and last only get tested 4 times
    	if ((octant & 129)>0) {bi.setRGB(cenx+x,ceny+y,colour);}
    	if ((octant & 6)>0) {bi.setRGB(cenx+y,ceny+x,colour);}
    	if ((octant & 24)>0) {;bi.setRGB(cenx+x,ceny-y,colour);}
    	if ((octant & 96)>0) {;bi.setRGB(cenx-y,ceny+x,colour);}
    	while(y>x)
    	{
    		if (d<0) d+= 2.0*(double)x+3.0;
    		else
    		{
    			d += 2.0*(double)(x-y)+5.0;
    			y--;
    		}
    		x++;
    		if ((octant & 1)>0) {bi.setRGB(cenx+x,ceny+y,colour);}
    		if ((octant & 2)>0) {bi.setRGB(cenx+y,ceny+x,colour);}
    		if ((octant & 4)>0) {bi.setRGB(cenx+y,ceny-x,colour);}
    		if ((octant & 8)>0) {bi.setRGB(cenx+x,ceny-y,colour);}
    		if ((octant & 16)>0) {bi.setRGB(cenx-x,ceny-y,colour);}
    		if ((octant & 32)>0) {bi.setRGB(cenx-y,ceny-x,colour);}
    		if ((octant & 64)>0) {bi.setRGB(cenx-y,ceny+x,colour);}
    		if ((octant & 128)>0) {bi.setRGB(cenx-x,ceny+y,colour);}
    		
    	}
    	if(y==x)
    	{
    		if ((octant & 3)>0) {bi.setRGB(cenx+x,ceny+x,colour);}
    		if ((octant & 12)>0) {bi.setRGB(cenx+x,ceny-x,colour);}
    		if ((octant & 48)>0) {bi.setRGB(cenx-x,ceny-x,colour);}
    		if ((octant & 192)>0) {bi.setRGB(cenx-x,ceny+x,colour);}
    	}
    	
    	
    	return bi;
    }
    public static EyeDataType find_iris(BufferedImage bi)
    {
    	EyeDataType ed = new EyeDataType();
    	Bounds b = new Bounds();
        int blur=12;
        b.rmin = 29;// specification is iris must be at least 70 pixels in diameter
        //b.rmin=45;//just for testing
        b.xmin = 110;//bi.getWidth()*3/8;
        b.ymin = 106;//bi.getHeight()*3 / 8;
        b.xmax = 212;//bi.getWidth()*5 /8;
        b.ymax = 191;//bi.getHeight() * 5 /8;
        b.rmax = 71;//(bi.getHeight())/2;
        
        
        CircleType c = find_circle(bi,blur,(char)126,b);
        ed.inner = c;
        
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

