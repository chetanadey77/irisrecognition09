package iris.imageToBitcode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import iris.bitcodeMatcher.BitCode;

/**
 * A class that uses Gabor filters to generate a bitcode from an image of an iris
 * The basic idea is to create different Gabor wavelets, defined by the constants
 * a,b,w,x0 and y0 and filter the unwrapped iris with those wavelets. The total 
 * number of bits returned is the total number of different combinations of those
 * constants * 2 (since a Gabor wavelet has real and complex parts).
 * 
 * @author Arnar B. Jonsson, Mark Howe, Edmund Noon
 * @version 1.0
 */
public class BitcodeGenerator {

	BufferedImage unWrapped;
	BitCode bitcode;
	int bitcodeShiftNum;
	GaborParameters wPar, abPar, x0Par, y0Par;
	UnWrapper uw;
	int[][] intensityArr;

	int xp, yp, rp, xi, yi, ri;

	int unwrWidth, unwrHeight;

	double a, b, w, a2, b2, x0, y0;	// alpha, beta and omega

	public BitcodeGenerator()
	{			
		// width and height of the unwrapped image. Up those numbers => more detail and longer runtime
		int _unwrWidth = 360;
		int _unwrHeight = 100;

		// set ranges and number of steps for omega, alpha, beta, x0 and y0
		
//		GaborParameters _wPar = new GaborParameters(0.19, 0.15, 3);
//		GaborParameters _abPar = new GaborParameters(10, 15, 3);
//		GaborParameters _x0Par = new GaborParameters(_abPar.upLim, _unwrWidth-_abPar.upLim , (int) (_unwrWidth-_abPar.upLim*2) );
//		GaborParameters _y0Par = new GaborParameters(_abPar.lowLim, _abPar.upLim, 3);

		
		GaborParameters _wPar = new GaborParameters(0.20, 0.1, 3);
		GaborParameters _abPar = new GaborParameters(15, 20, 3);
		GaborParameters _x0Par = new GaborParameters(_abPar.upLim, _unwrWidth-_abPar.upLim , (int) (_unwrWidth-_abPar.upLim*2) );
		GaborParameters _y0Par = new GaborParameters(_abPar.lowLim, _abPar.upLim, 3);

		this.initialiseParams(_wPar, _abPar, _x0Par, _y0Par, _unwrWidth, _unwrHeight);
	}

	public BitcodeGenerator(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight)
	{
		this.initialiseParams(_wPar, _abPar, _x0Par, _y0Par, _unwrWidth, _unwrHeight);
	}

	/**
	 * 
	 * @param eyeImage original image of an eye
	 * @param eye iris boundary information
	 * @return generated bitcode
	 */
	public BitCode getBitcode(BufferedImage eyeImage,EyeDataType eye)
	{
		return this.getBitcode(eyeImage, eye.inner.x,eye.inner.y,eye.inner.radius,eye.outer.x,eye.outer.y,eye.outer.radius);

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
		uw = new UnWrapper();
		bitcode = new BitCode();

		// array of intensity values (used rather than BufferedImage for speed)
		intensityArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, unwrHeight, unwrWidth); 
		unWrapped = uw.unWrapWithGuides(eyeImage, xp, yp, rp, xi, yi, ri, unwrHeight, unwrWidth);
		bitcodeShiftNum = 3;

		for (int x0I=0; x0I < x0Par.numVals-1; x0I++) {
			for (int i=0; i < bitcodeShiftNum; i++ )	{
				// set parameters
				a = abPar.get_StepN(i);
				b = abPar.get_StepN(i);
				w = wPar.get_StepN(i);
				x0 = x0Par.get_StepN(x0I);
				y0 = y0Par.get_StepN(i);	

				// apply filter for given set of parameters
				this.gaborFilter2D();
			} 
		}  
		bitcode.setShiftNum(bitcodeShiftNum);
		return bitcode;

	}

	private void initialiseParams(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight)
	{
		unwrWidth = _unwrWidth;
		unwrHeight = _unwrHeight;

		wPar = _wPar;
		abPar = _abPar;
		x0Par = _x0Par;
		y0Par = _y0Par;
	}

	// -----------------------------------
	// Gabor filters
	// -----------------------------------
	private void gaborFilter2D()
	{
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal, imgVal;
		double sumRe = 0;
		double sumIm = 0;
		double xmin, xmax,ymin,ymax;
		xmin = Math.floor(x0-a);
		xmax = Math.ceil(x0+a);
		ymin = Math.floor(y0-b);
		ymax = Math.ceil(y0+b);
		
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{

				//imgVal = (double)intensityArr[(int) x][(int) y];

				Color c = new Color(unWrapped.getRGB((int)x, (int)y));
				imgVal = (c.getRed() + c.getGreen() + c.getBlue())/3;

				//e^(-pi((x-x0)^2/a^2 + (y-y0)^2/b^2) 
				k = Math.exp( -Math.PI * (Math.pow( x - x0, 2) / a2 + Math.pow( y - y0, 2) / b2) );
				//sin(-2*pi*w(x-x0 + y-y0))
				tmpVal =  -w * 2 * Math.PI * ( x-x0 ); 
				imPart = Math.sin( tmpVal );
				//cos(-2*pi*w(x-x0 + y-y0))
				rePart = Math.cos( tmpVal );

				sumRe +=  imgVal * k * imPart;
				sumIm += imgVal * k * rePart; 
			}
		}

		bitcode.addBit(sumRe >= 0);
		bitcode.addBit(sumIm >= 0);
	}

	private void gaborFilter1D()
	{
		a2 = a*a;
		b2 = b*b; 
		double k,imPart,rePart,tmpVal;
		double sumRe, sumIm;

		for(double x = 0; x < unwrWidth-1; x++)
		{
			sumRe = sumIm = 0;
			for(double y=0; y < unwrHeight-1; y++)
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
