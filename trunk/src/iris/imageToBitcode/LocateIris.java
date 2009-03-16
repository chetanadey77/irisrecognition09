package iris.imageToBitcode;


import iris.nonJunitTesting.CircleList;

import java.awt.image.BufferedImage;
/**
 * This class locates the pupil and outer iris boundary. It is currently 80% successful 
 * on the set of images it has been tested with. 
 * 
 * 
 * @author en108 
 *
 */
public class LocateIris {
	/**
	 * Blurs an images using a standard convolution method with the width being 3 SD
	 * and pixels wide
	 * @param bi
	 * @param pixels
	 * @return new image
	 */
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
    	double total=0.0;
    	BufferedImage nbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
    	for (int x = pixels; x<(bi.getWidth()-pixels);x++)
    		for (int y = pixels; y<bi.getHeight()-pixels;y++)
    		{
    			total=0.0;
    			for(int r=0;r<(pixels*2+1)*(pixels*2+1);r++)
    			{
    				total+= matrix[r] * (double)(bi.getRGB((r % (2*pixels+1))-pixels +x, (r / (2*pixels+1))-pixels+y)&255);
    			}
    			nbi.setRGB(x, y, (int)(total/normalise) *0x10101);
    		}
    	return nbi;
    	
    }
	/**
	 * This is not a Hough transform, more a Sobel filter in just the x axis
	 * @param bi taken a input and the 
	 * @return is another BufferedImage
	 */
	public static BufferedImage houghx(BufferedImage bi)
    {
    	int[] matrix = {-1,0,1,-2,0,2,-1,0,1} ;
    	int shift = 1024, scale =1,c;
    	BufferedImage nbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
    	for (int x=1; x<bi.getWidth()-1;x++)
    		for (int y=1;y<bi.getHeight()-1;y++)
    		{
    			c = matrix[0] * (bi.getRGB(x-1,y-1) & 255) +
					matrix[1] * (bi.getRGB(x,y-1) & 255) +
					matrix[2] * (bi.getRGB(x+1,y-1) & 255) +
					matrix[3] * (bi.getRGB(x-1,y) & 255) +
					matrix[4] * (bi.getRGB(x,y) & 255) +
					matrix[5] * (bi.getRGB(x+1,y) & 255) +
					matrix[6] * (bi.getRGB(x-1,y+1) & 255) +
    				matrix[7] * (bi.getRGB(x,y+1) & 255) +
    				matrix[8] * (bi.getRGB(x+1,y+1) & 255);
    			c = Math.abs(c) / scale;
    			if (c>255) c=255;
    			nbi.setRGB(x, y, c*0x10101);
    		}
    	return nbi;
    	
    }
	/**
	 * This is not a Hough transform, more a Sobel filter in just the y axis
	 * @param bi taken a input and the 
	 * @return is another BufferedImage
	 */
public static BufferedImage houghy(BufferedImage bi)
    {
    	int[] matrix = {-1,-2,-1,0,0,0,1,2,1} ;
    	int shift = 1024, scale =1,c;
    	BufferedImage nbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
    	for (int x=1; x<bi.getWidth()-1;x++)
    		for (int y=1;y<bi.getHeight()-1;y++)
    		{
    			c = 	matrix[0] * (bi.getRGB(x-1,y-1) & 255) +
				matrix[1] * (bi.getRGB(x,y-1) & 255) +
				matrix[2] * (bi.getRGB(x+1,y-1) & 255) +
				matrix[3] * (bi.getRGB(x-1,y) & 255) +
				matrix[4] * (bi.getRGB(x,y) & 255) +
				matrix[5] * (bi.getRGB(x+1,y) & 255) +
				matrix[6] * (bi.getRGB(x-1,y+1) & 255) +
    				matrix[7] * (bi.getRGB(x,y+1) & 255) +
    				matrix[8] * (bi.getRGB(x+1,y+1) & 255);
    			c = Math.abs(c) / scale;
    			if (c>255) c=255;
    			nbi.setRGB(x, y, c*0x10101);
    		}
    	return nbi;
    	
    }
/**
 * edge detection in both directions, though at the moment the code is modified so that only the x axis is considered 
 * as this is used for the the finding the edge of the outer edge of the iris
 * @param bi
 * @return
 */
public static BufferedImage edgeDetection(BufferedImage bi)
{
	bi= gaussian_blur(bi,4);
	int[] matrixX = {-1,0,1,-2,0,2,-1,0,1} ;
	int[] matrixY = {-1,-2,-1,0,0,0,1,2,1} ;
	int shift = 1024, scale =1,c,d;
	BufferedImage nbi = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
	for (int x=1; x<bi.getWidth()-1;x++)
		for (int y=1;y<bi.getHeight()-1;y++)
		{
			c = matrixX[0] * (bi.getRGB(x-1,y-1) & 255) +
				matrixX[1] * (bi.getRGB(x,y-1) & 255) +
				matrixX[2] * (bi.getRGB(x+1,y-1) & 255) +
				matrixX[3] * (bi.getRGB(x-1,y) & 255) +
				matrixX[4] * (bi.getRGB(x,y) & 255) +
				matrixX[5] * (bi.getRGB(x+1,y) & 255) +
				matrixX[6] * (bi.getRGB(x-1,y+1) & 255) +
				matrixX[7] * (bi.getRGB(x,y+1) & 255) +
				matrixX[8] * (bi.getRGB(x+1,y+1) & 255);
			
			d = matrixY[0] * (bi.getRGB(x-1,y-1) & 255) +
				matrixY[1] * (bi.getRGB(x,y-1) & 255) +
				matrixY[2] * (bi.getRGB(x+1,y-1) & 255) +
				matrixY[3] * (bi.getRGB(x-1,y) & 255) +
				matrixY[4] * (bi.getRGB(x,y) & 255) +
				matrixY[5] * (bi.getRGB(x+1,y) & 255) +
				matrixY[6] * (bi.getRGB(x-1,y+1) & 255) +
				matrixY[7] * (bi.getRGB(x,y+1) & 255) +
				matrixY[8] * (bi.getRGB(x+1,y+1) & 255);
			
			//c = Math.abs(c) / scale;
			d = c*c;
			if (d>525) c=0; else c=255;
			nbi.setRGB(x, y, c*0x10101);
		}
	return nbi;
	
}
/**
 * This returns the best circle it can find in the image bi, given pixel_blur. 
 * It only looks at the part of the circle specified by octant
 * @param bi
 * @param pixel_blur
 * @param octant
 * @param bounds constrains the image search space
 * @return
 */
    public static CircleType find_circle(BufferedImage bi, int pixel_blur, char octant,Bounds bounds)
    {
    	BufferedImage bigb;
    	
    	if (octant == (char) 102)  bigb= edgeDetection(bi);
    	else bigb= gaussian_blur(bi,pixel_blur);
    
    	int[][] array_image = new int[bigb.getWidth()][bigb.getHeight()];
    	for (int x=0;x<bigb.getWidth();x++)
    		for (int y=0;y<bigb.getHeight();y++)
    			array_image[x][y] = bigb.getRGB(x,y) & 255;
    	
    	CircleList cl = new CircleList(50);
    	CircleType c = new CircleType();
    	double[][][] hist = new double[bounds.rmax+1 - bounds.rmin][bounds.xmax +1 - bounds.xmin][bounds.ymax +1 - bounds.ymin];
    	double max_diff = 0.0;
    	double diff = 0.0;
    	int xo=0,yo=0,ro=0;
    	int r_min = bounds.rmin;
    	int x_min = bounds.xmin;
    	int y_min = bounds.ymin;
    	for (int radius= r_min;radius<=bounds.rmax;radius++)
    	{
    		for (int x = Math.max(radius,bounds.xmin);x<Math.min(bi.getWidth()-radius, bounds.xmax);x++)
    		{
    			for (int y =Math.max(radius, bounds.ymin) ;y<Math.min(bi.getHeight()-radius,bounds.ymax);y++)
    			{
    				hist[radius-r_min][x-x_min][y-y_min] = loop_integral(array_image,x,y,radius,octant);
    				if (radius>r_min)
    				{
    					diff = Math.abs(hist[radius-r_min][x-x_min][y-y_min] - hist[radius-1-r_min][x-x_min][y-y_min]);
    					if (diff>max_diff) 
    					{
    						max_diff = diff;//cl.add_circle(new CircleType(x,y,radius,diff));
    						xo=x; yo=y; ro=radius;
    					}
    				}
    			}
    		}
    	} 
    	c.x = xo;
        c.y = yo;
        c.radius = ro;
    	return c;	
    }
    /**
     * Returns the sum over a partial loop centred on (cenx,ceny)
     * of radius r and octant parts
     * @param array_bi integer array of the smae size as the orriginal buffered image
     * @param cenx
     * @param ceny
     * @param r
     * @param octant
     * @return
     */
    public static double loop_integral(int[][] array_bi, int cenx, int ceny, int r, char octant)
    {
    	int total=0;
    	int count =0;
    	//Algorthim modified from Bresenham 1977
    	int x=0;
    	int y=r;
    	double d =5.0/ 4.0 - (double) r;
    	//normally will do 1/8th of the circle and then test 8
    	//versions but first and last only get tested 4 times
    	if ((octant & 129)>0) {total+=array_bi[cenx+x][ceny+y];count++;}
    	if ((octant & 6)>0) {total+=array_bi[cenx+y][ceny+x];count++;}
    	if ((octant & 24)>0) {total+=array_bi[cenx+x][ceny-y];count++;}
    	if ((octant & 96)>0) {total+=array_bi[cenx-y][ceny+x];count++;}
    	while(y>x)
    	{
    		if (d<0) d+= 2.0*(double)x+3.0;
    		else
    		{
    			d += 2.0*(double)(x-y)+5.0;
    			y--;
    		}
    		x++;
    		if ((octant & 1)>0) {total+=array_bi[cenx+x][ceny+y];count++;}
    		if ((octant & 2)>0) {total+=array_bi[cenx+y][ceny+x];count++;}
    		if ((octant & 4)>0) {total+=array_bi[cenx+y][ceny-x];count++;}
    		if ((octant & 8)>0) {total+=array_bi[cenx+x][ceny-y];count++;}
    		if ((octant & 16)>0) {total+=array_bi[cenx-x][ceny-y];count++;}
    		if ((octant & 32)>0) {total+=array_bi[cenx-y][ceny-x];count++;}
    		if ((octant & 64)>0) {total+=array_bi[cenx-y][ceny+x];count++;}
    		if ((octant & 128)>0) {total+=array_bi[cenx-x][ceny+y];count++;}
    		
    	}
    	if(y==x)
    	{
    		if ((octant & 3)>0) {total+=array_bi[cenx+x][ceny+x];count++;}
    		if ((octant & 12)>0) {total+=array_bi[cenx+x][ceny-x];count++;}
    		if ((octant & 48)>0) {total+=array_bi[cenx-x][ceny-x];count++;}
    		if ((octant & 192)>0) {total+=array_bi[cenx-x][ceny+x];count++;}
    	}
    	
    	
    	if (count>0) return (double) (total) /(double)count;
    	else return 0;
    }
    /**
     * This is used mainly by testing routines to draw thei eye image with the 
     * circles drawn on them to show that the pupil location methods have worked
     * @param bi
     * @param cenx
     * @param ceny
     * @param r
     * @param octant
     * @param colour
     * @return
     */
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
    /**
     * The main function, an image is passed and the centre details are returned
     * @param bi image containing an eye
     * @return centre information
     */
    public static EyeDataType find_iris(BufferedImage bi)
    {
    	EyeDataType ed = new EyeDataType();
    	Bounds b = new Bounds();
        int blur=5;
        b.rmin = 29;// specification is iris must be at least 70 pixels in diameter
        //b.rmin=45;//just for testing
        b.xmin = 110;//bi.getWidth()*3/8;
        b.ymin = 106;//bi.getHeight()*3 / 8;
        b.xmax = 212;//bi.getWidth()*5 /8;
        b.ymax = 191;//bi.getHeight() * 5 /8;
        b.rmax = 71;//(bi.getHeight())/2;
        
        
        CircleType c = find_circle(bi,blur,(char)126,b);
        ed.inner = c;
        
        blur=10;
        b.rmin = 75;// specification is iris must be at least 70 pixels in diameter
        b.rmax = 120;
        
        b.xmin = c.x - b.rmin / 4;
        b.ymin = c.y - b.rmin /5;
        b.xmax = c.x + b.rmin / 4;
        b.ymax = c.y + b.rmin /5;
        
        c = find_circle(bi,10,(char)102,b);
        
        ed.outer = c;
        return ed;
    }
    
}
/**
 * A small class to hold information to restrict the search area of the pupils and outer iris edges
 * @author en108
 *
 */
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

