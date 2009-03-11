package iris.imageToBitcode;

import java.awt.image.BufferedImage;

/* Sobel edge detection. 3-by-3 kernels are convolved with image
 * to obtain an approximation to derivative rates of change in x 
 * and y directions. The magnitude of this rate of change is 
 * calculated and if it lies above a certain threshold then is 
 * considered an edge point otherwise it isn't.
 * 
 * The formula for the convolution C(i,j) for each pixel making up the 
 * image I(i,j) is:
 * 
 * C(i,j) = I(i+k-1,j+l-1) * K(k,l).
 * 
 * K is the m-by-n kernel. Because an average is being taken between
 * nearest neighbours we don't apply the pixels along the perimeter, so in
 * general, given an input image with M rows and N columns and a kernel 
 * with m rows and n columns, the output edgemap has M-m+1 rows and N-n+1
 * columns:
 *   
 *		0000000000
 *      0********0	
 *  	0********0
 *   	0000000000 
 *   
 * There are then two kernels Cx(i,j) and Cy(i,j). So given a threshold t, if  
 *   
 * sqrt(Cx(i,j)^2 + Cy(i,j)^y) >= t, 
 * 
 * then (i,j) will be taken to be an edge.  
 */     
public class LocateIris2 {
    
    /* Convolve image with kernels. */
    private int convolve(int x, int y, int[][] k) {
    	BufferedImage bi = null;
        /* Convolution. */
    	int xx, yy, sum=0;
    	for(xx=-1; xx<=1; xx++)
    		/* Is this the right way to access grayscale value of a pixel? */
    		for(yy=-1; yy<=1; yy++) sum += bi.getRGB(x+xx, y+yy)*k[xx+1][yy+1];
    		/* yes but bi is an instance of BufferedImage, you need to pass it in*/
    	
    	return sum;
    }
    
    
    /* Create Sobel edge map. */
    public BufferedImage map(BufferedImage bi, int threshold){
    	
    	/* Sobel kernels. */
    	int[][] kx = 
    	{ { -1, -2, -1},
    	  {  0, 0,  0},
    	  {  1, 2,  1}};

        int[][] ky = 
    	{ { -1, 0,  1},
    	  { -2, 0,  2},
    	  { -1, 0,  1}};
    	
    	int x, y, sx, sy;
    	/* Edgemap size. */
    	int H = bi.getHeight();
    	int W = bi.getWidth();
    	BufferedImage edgeMap = new BufferedImage(H-2, W-2, BufferedImage.TYPE_INT_RGB);
    	
    	/* Convolve each inside pixel. */
    	for(x=1; x<bi.getWidth()-1; x++){
    		for(y=1; y<bi.getHeight()-1; y++){
    			sx = convolve(x, y, kx);
    			sy = convolve(x, y, ky);
    		    if(Math.sqrt(sx*sx+sy*sy) >= threshold) edgeMap.setRGB(x-1, y-1, 0);
    		    else edgeMap.setRGB(x, y, 255);
    		}
    	}  	
    	return edgeMap;
    }
}
    	
