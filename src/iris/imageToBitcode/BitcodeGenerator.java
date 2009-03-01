package iris.imageToBitcode;

import java.awt.image.BufferedImage;
import iris.gui.EyeDataType;
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

	int number_of_passes;
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
		ab_lowLim = 1000;
		ab_upLim = 1000;
		ab_numSteps = 1;

		w_lowLim = 0.01;
		w_upLim  = 0.5;
		w_numSteps = 8;

		x0_numSteps = 2;
		x0_lowLim = 0.5;
		x0_upLim = 0.8;

		y0_numSteps = 2;
		y0_lowLim = 0.5;
		y0_upLim = 0.8;
		
		number_of_passes=3;

		
		//if pupil has a radius of often about 35 pixels and if most of the information is near to the 
		//pupil edge then perhaps 250 would be a better unwrapped width
		// width and height of the unwrapped image. Up those numbers => more detail and longer runtime
		unwrappedWidth = 360;
		unwrappedHeight = 100;
	}
	public BitCode getBitcode2(BufferedImage eyeImage,int xPup, int yPup, int rPup, int xIris, int yIris, int rIris)
	{
		xp = xPup; yp = yPup; rp = rPup;
		xi = xIris; yi = yIris; ri = rIris;
		c = new CoordConverter(xp,yp,rp,xi,yi,ri);
		uw = new UnWrapper();
		int boxsize[] = {10,20,40};//the last box must be the largest
		assert (boxsize.length == number_of_passes);

		// length of bitcode determined by the number of combinations of constants
		//is the width of unwrapped image * number of passes * 2
		bitcode = new BitCode(unwrappedWidth*number_of_passes*2);

		int[][] tmpArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, unwrappedHeight, unwrappedWidth); //the unwrapped iris pixels
		//we need to extend the image by the width of the largest box 
		//so that the image wraps around
		imgWidth = tmpArr.length;
		imgHeight = tmpArr[0].length;
		intensityArr = new int[imgWidth+boxsize[number_of_passes-1]][imgHeight];
		for (int x=0;x<imgWidth;x++)
			for (int y=0;y<imgHeight;y++)
			{
				intensityArr[x][y] = tmpArr[x][y];
				if (x<boxsize[number_of_passes-1])
					intensityArr[x+imgWidth][y] = tmpArr[x][y];
			}
		imgWidth = intensityArr.length;
		imgHeight = intensityArr[0].length;
		
		ab_step = (ab_upLim - ab_lowLim)/ab_numSteps;
		w_step = (w_upLim - w_lowLim)/w_numSteps;
		x0_step = imgWidth * (x0_upLim - x0_lowLim)/ x0_numSteps;
		y0_step = imgWidth * (y0_upLim - y0_lowLim)/ y0_numSteps;

		for (int x=0; x < imgWidth; x++)
		{
			for (int n = 0;n<number_of_passes;n++)
			{
				//need to change these so that they fit into one box 
				a = ab_lowLim ;
				b = ab_lowLim ;
				w = w_lowLim ;
				x0 = x0_lowLim ;
				y0 = y0_lowLim ;
				
				//also not sure of the direction of y in intensity array
							
				this.gaborFilter1DBox((double)x,0,(double)(x + boxsize[n]),(double)(boxsize[n]));
						
					
				
			}
		}

		return bitcode;
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

		// length of bitcode determined by the number of combinations of constants
		bitcode = new BitCode(ab_numSteps*2 * w_numSteps * x0_numSteps * y0_numSteps);

		intensityArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, unwrappedHeight, unwrappedWidth); //the unwrapped iris pixels
		imgWidth = intensityArr.length;
		imgHeight = intensityArr[0].length;

		ab_step = (ab_upLim - ab_lowLim)/ab_numSteps;
		w_step = (w_upLim - w_lowLim)/w_numSteps;
		x0_step = imgWidth * (x0_upLim - x0_lowLim)/ x0_numSteps;
		y0_step = imgWidth * (y0_upLim - y0_lowLim)/ y0_numSteps;

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

							this.gaborFilter1D();
			
						}
					}
				}
			}
		}

		return bitcode;
	}
	//EyeDataType has the location and radius of the pupil and outer edge of the iris
	//In time it might have the eyelid too
	public BitCode getBitcode(BufferedImage eyeImage,EyeDataType eye)
	{
		return this.getBitcode2(eyeImage, eye.inner.x,eye.inner.y,eye.inner.radius,eye.outer.x,eye.outer.y,eye.outer.radius);

	}
	private void gaborFilter2D()
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

	private void gaborFilter1D()
	{
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal;
		double sumRe, sumIm;
		
		for(double x = 0; x < imgWidth-1; x++)
		{
			sumRe = sumIm = 0;
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
			bitcode.addBit(sumRe >= 0);
			bitcode.addBit(sumIm >= 0);
		}

	}
	// convolve gabor filter over a small box with diagonal corners (x1,y1) and (x2,y2)
	//x1<x2 and y1<y2 and add the result to the bitcode
	private void gaborFilter1DBox(double x1,double y1,double x2, double y2)
	{
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal;
		double sumRe, sumIm;
		
		for(double x = x1; x < x2; x++)
		{
			sumRe = sumIm = 0;
			for(double y=y1; y < y2; y++)
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
			bitcode.addBit(sumRe >= 0);
			bitcode.addBit(sumIm >= 0);
		}

	}



}
