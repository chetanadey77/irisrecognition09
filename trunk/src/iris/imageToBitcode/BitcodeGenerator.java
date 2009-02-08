package iris.imageToBitcode;

import java.awt.image.BufferedImage;

import iris.bitcodeMatcher.BitCode;

/**
 * A class that uses Gabor filters to generate a bitcode from an image of an iris
 * The basic idea is to create different Gabor wavelets, defined by the constants
 * a,b,w,x0 and y0 and filter the unwrapped iris with those wavelets. The total 
 * number of bits returned is the total number of different combinations of those
 * constants * 2 (since a Gabor wavelet has real and complex parts).
 * 
 * @author Arnar B. Jonsson, Mark Howe
 * @version 1.0
 */
public class BitcodeGenerator {

	BufferedImage eImage, unWrapped;
	BitCode bitcode;
	CoordConverter c;
	UnWrapper uw;
	int[][] intensityArr;
	
	int xp, yp, rp, xi, yi, ri;
	int imgWidth, imgHeight;
	int unwrappedWidth, unwrappedHeight;
	
	double a, b, w, a2, b2, x0, y0;	// alpha, beta and omega
	double ab_lowLim, ab_upLim, ab_step; // alpha and beta range
	int ab_numSteps; 
	double w_lowLim, w_upLim, w_step; // omega range
	int w_numSteps; 
	double x0_lowLim, x0_upLim, x0_step; // x0 range
	int x0_numSteps;
	double y0_lowLim, y0_upLim, y0_step; // y0 range
	int y0_numSteps;
	
	public BitcodeGenerator()
	{			
		// set ranges for Gabor filter constants
		ab_lowLim = 50;
		ab_upLim = 50;
		ab_numSteps = 1;
		
		w_lowLim = 0.01;
		w_upLim  = 0.05;
		w_numSteps = 16;
		
		x0_numSteps = 8;
		x0_lowLim = 0;
		x0_upLim = 1.0;
		
		y0_numSteps = 8;
		y0_lowLim = 0;
		y0_upLim = 1.0;
		
		// width and height of the unwrapped image. Up those numbers => more detail and longer runtime
		unwrappedWidth = 180;
		unwrappedHeight = 50;
		
		// length of bitcode determined by the number of 
		bitcode = new BitCode(ab_numSteps*2 * w_numSteps * x0_numSteps * y0_numSteps);
	}
	
	/**
	 * Generate bitcode
	 * @param eyeImage original image of an eye
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 * @return generated bitcode
	 */
	public BitCode getBitcode(BufferedImage eyeImage,int xPup, int yPup, int rPup, int xIris, int yIris, int rIris)
	{
		xp = xPup; yp = yPup; rp = rPup;
		xi = xIris; yi = yIris; ri = rIris;
		c = new CoordConverter(xp,yp,rp,xi,yi,ri);
		uw = new UnWrapper();
		
		intensityArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, unwrappedHeight, unwrappedWidth); //the unwrapped iris pixels
		imgWidth = intensityArr.length;
		imgHeight = intensityArr[0].length;
		
		ab_step = (ab_upLim - ab_lowLim)/ab_numSteps;
		w_step = (w_upLim - w_lowLim)/w_numSteps;
		x0_step = imgWidth * (x0_upLim - x0_lowLim)/ (x0_numSteps-1);
		y0_step = imgWidth * (y0_upLim - y0_lowLim)/ (y0_numSteps-1);
		
		for (float aI=0; aI < ab_numSteps; aI++)
		{
			for (float bI=0; bI < ab_numSteps; bI++ )
			{
				for (float wI=0; wI < w_numSteps; wI++)
				{
					for (float x0I=0; x0I < x0_numSteps; x0I++)
					{
						for (float y0I=0; y0I < y0_numSteps; y0I++)
						{
							a = ab_lowLim + aI*ab_step;
							b = ab_lowLim + aI*ab_step;
							w = w_lowLim + wI*w_step;
							x0 = x0_lowLim + x0I*x0_step;
							y0 = y0_lowLim + y0I*y0_step;
							//System.out.println(a+"::"+b+"::"+w+"::"+x0+"::"+y0);
							this.gaborFilter();
						}
					}
				}
			}
		}
		
		return bitcode;
	}
	
	private void gaborFilter()
	{
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal;
		double sumRe = 0;
		double sumIm = 0;		
		
		for(double x = 0; x < imgWidth-1; x++)
		{
			for(double y=0; y < imgHeight-1; y++)
			{
				//e^(-pi((x-x0)^2/a^2 + (y-y0)^2/b^2) 
				k = Math.exp( -Math.PI * (Math.pow( x - x0, 2) / a2 + Math.pow( y - y0, 2) / b2) );
				//sin(-2*pi*w(x-x0 + y-y0))
				tmpVal =  -w * 2 * Math.PI * ( x-x0 + y-y0); 
				imPart = Math.sin( tmpVal );
				//cos(-2*pi*w(x-x0 + y-y0))
				rePart = Math.cos( tmpVal );
				
				sumRe += (double)intensityArr[(int) x][(int) y] * k * imPart;
				sumIm += (double)intensityArr[(int) x][(int) y] * k * rePart; 
			}
		}
		//System.out.println((float)sumRe + " ::: " + (float)sumIm);
		bitcode.addBit(sumRe >= 0);
		bitcode.addBit(sumIm >= 0);
	}
	
	
	
}
