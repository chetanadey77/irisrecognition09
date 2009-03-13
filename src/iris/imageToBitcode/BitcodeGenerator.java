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
	int bitsPerBox;		//2 is default, 1 means no real bit is saved
	GaborParameters wPar, abPar, x0Par, y0Par;
	UnWrapper uw;
	int[][] intensityArr;
	int[][][] gaborReal;
	int[][][] gaborImaginary;

	int xp, yp, rp, xi, yi, ri;

	int unwrWidth, unwrHeight;

	double a, b, w, a2, b2, x0, y0;	// alpha, beta and omega

	public BitcodeGenerator()
	{		
		// width and height of the unwrapped image. Up those numbers => more detail and longer runtime
		int _unwrWidth = 360;
		int _unwrHeight = 100;
		//bitsPerBox=2;
		// set ranges and number of steps for omega, alpha, beta, x0 and y0
		
//		GaborParameters _wPar = new GaborParameters(0.19, 0.15, 3);
//		GaborParameters _abPar = new GaborParameters(10, 15, 3);
//		GaborParameters _x0Par = new GaborParameters(_abPar.upLim, _unwrWidth-_abPar.upLim , (int) (_unwrWidth-_abPar.upLim*2) );
//		GaborParameters _y0Par = new GaborParameters(_abPar.lowLim, _abPar.upLim, 3);
		
		GaborParameters _wPar = new GaborParameters((1.0/17.0),(1.0/38.0) , 3);
		GaborParameters _abPar = new GaborParameters(17, 38, 3);
		GaborParameters _x0Par = new GaborParameters(_abPar.upLim, _unwrWidth+_abPar.upLim , (int) (_unwrWidth) );
		//GaborParameters _x0Par = new GaborParameters(_abPar.upLim, _unwrWidth+_abPar.upLim, (int) (_unwrWidth-_abPar.upLim*2) );
		GaborParameters _y0Par = new GaborParameters(_abPar.lowLim, _abPar.upLim, 3);

		this.initialiseParams(_wPar, _abPar, _x0Par, _y0Par, _unwrWidth, _unwrHeight,2);
	}

	public BitcodeGenerator(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight)
	{
		this.initialiseParams(_wPar, _abPar, _x0Par, _y0Par, _unwrWidth, _unwrHeight,2);
	}
	
	public BitcodeGenerator(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight,int bits)
	{
		this.initialiseParams(_wPar, _abPar, _x0Par, _y0Par, _unwrWidth, _unwrHeight,bits);
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
		intensityArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, unwrHeight, unwrWidth,(int) (2.0 * abPar.upLim +1.0)); 
		//unWrapped = uw.unWrapWithGuides(eyeImage, xp, yp, rp, xi, yi, ri, unwrHeight, unwrWidth);
		

		for (int x0I=0; x0I < x0Par.numVals-1; x0I++) {
			for (int i=0; i < bitcodeShiftNum; i++ )	{
				// set parameters
				a = abPar.get_StepN(i);
				b = abPar.get_StepN(i);
				w = wPar.get_StepN(i);
				//w = wPar.lowLim/(2.0*a);
				x0 = x0Par.get_StepN(x0I);
				y0 = y0Par.get_StepN(i);	

				// apply filter for given set of parameters
				this.gaborFilter2D();
			} 
		}  
		bitcode.setShiftNum(bitcodeShiftNum*bitsPerBox);
		return bitcode;

	}
	public void initialiseParams(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight)
	{
		initialiseParams( _wPar,_abPar,  _x0Par, _y0Par,  _unwrWidth, _unwrHeight,2);
	}

	public void initialiseParams(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight,int _bitsPerBox)
	{
		unwrWidth = _unwrWidth;
		unwrHeight = _unwrHeight;
		bitcodeShiftNum = 3;
		wPar = _wPar;
		abPar = _abPar;
		x0Par = _x0Par;
		y0Par = _y0Par;
		if (_bitsPerBox==1 || _bitsPerBox==2) bitsPerBox = _bitsPerBox;
		else bitsPerBox =2;
		
		
		
		gaborReal = new int[bitcodeShiftNum][(int)abPar.upLim*2+1][(int)abPar.upLim*2+1];
		gaborImaginary= new int[bitcodeShiftNum][(int)abPar.upLim*2+1][(int)abPar.upLim*2+1];
		double k,tmpVal;
		for(int series=0;series<bitcodeShiftNum;series++)
		{
			int ab= (int) abPar.get_StepN(series);
			double lw = wPar.get_StepN(series);
			int ab2 = ab*ab;
			for(int x = -ab; x <= ab; x++)
				for(int y =-ab; y <=ab; y++)
				{
					
				//e^(-pi((x-x0)^2/a^2 + (y-y0)^2/b^2) 
				k = Math.exp( -Math.PI * (Math.pow( x , 2) / ab2 + Math.pow( y , 2) / ab2) );
				//sin(-2*pi*w(x-x0 + y-y0))
				tmpVal =  -lw * 2 * Math.PI * ( x ); 
				
				
				//cos(-2*pi*w(x-x0 + y-y0))
				gaborReal[series][x+ab][y+ab] = (int)(65536.0 * k * Math.cos( tmpVal));// * wPar.upLim);
				gaborImaginary[series][x+ab][y+ab] = (int)(65536.0 * k * Math.sin( tmpVal));
			//these are 65536 times too big, but we only care about the sign!
				}
			
				
		}	
				
		
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
		//System.out.println("x "+xmin+" to "+xmax+"  y "+ymin+" to "+ymax);
		//double lambda =2.0;
		//w = lambda /(a*2);
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{

				//imgVal = (double)intensityArr[(int) x][(int) y];

				//Color c = new Color(unWrapped.getRGB((int)x, (int)y));
				
				//imgVal = (c.getRed() + c.getGreen() + c.getBlue())/3;
				imgVal = intensityArr[(int)x][(int)y];
				//e^(-pi((x-x0)^2/a^2 + (y-y0)^2/b^2) 
				k = Math.exp( -Math.PI * (Math.pow( x - x0, 2) / a2 + Math.pow( y - y0, 2) / b2) );
				//sin(-2*pi*w(x-x0 + y-y0))
				tmpVal =  -w * 2 * Math.PI * ( x-x0 ); 
				
				imPart = Math.sin( tmpVal );
				//cos(-2*pi*w(x-x0 + y-y0))
				rePart = Math.cos( tmpVal);// * wPar.upLim);

				sumRe +=  (double) imgVal * k * rePart;
				sumIm +=  (double) imgVal * k * imPart; 
			//System.out.println(rePart+" "+k*rePart);//+imPart);
			}
		}

		if (bitsPerBox==2) bitcode.addBit(sumRe >= 0.0);
		bitcode.addBit(sumIm >= 0.0);
		//System.out.println(sumRe+" "+sumIm);
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
			bitcode.addBit(sumRe >= 0.0);
			bitcode.addBit(sumIm >= 0.0);
		}
	}
	public BitCode getFastBitcode(BufferedImage eyeImage,EyeDataType eye)
	{
		xp = eye.inner.x; yp = eye.inner.y; rp = eye.inner.radius;
		xi = eye.outer.x; yi = eye.outer.y; ri = eye.outer.radius;
		uw = new UnWrapper();
		bitcode = new BitCode();

		// array of intensity values (used rather than BufferedImage for speed)
		intensityArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, unwrHeight, unwrWidth,(int) (2.0 * abPar.upLim +1.0)); 
		int imgVal;
		int[] ab = new int[bitcodeShiftNum];
		for(int i =0; i< bitcodeShiftNum;i++) ab[i] = (int) abPar.get_StepN(i); 
		int ab_max = (int) abPar.upLim;
		int sumRe,sumIm;
		for (int x0 =ab_max; x0 < unwrWidth+ab_max; x0++)
		{
			for (int i=0; i < bitcodeShiftNum; i++ )
			{
				sumRe = 0;
				sumIm = 0;
				for(int x = -ab[i]; x <= ab[i]; x++)
				{
					for(int y =-ab[i]; y <=ab[i]; y++)
					{	
						imgVal = intensityArr[x0 +x][y+ab[i]];
						sumRe += imgVal*gaborReal[i][x+ab[i]][y+ab[i]];
						sumIm += imgVal*gaborImaginary[i][x+ab[i]][y+ab[i]];
					}
				}
				if (bitsPerBox==2) bitcode.addBit(sumRe >= 0);
				bitcode.addBit(sumIm >= 0);
			}
		}
		bitcode.setShiftNum(bitcodeShiftNum*bitsPerBox);
		return bitcode;
	}

	

	public BufferedImage drawWavelet(BufferedImage eyeImage,int x0,int y0,int step)
	{
		
		int h=100, width= 360;
		BufferedImage bimage = new BufferedImage(width,h*5,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<width;i++)
			for(int j=0;j<h*5;j++)
				bimage.setRGB(i, j, 0);
		a = abPar.get_StepN(step);
		b = abPar.get_StepN(step);
		w = wPar.get_StepN(step);
		//w = wPar.lowLim/(2.0*a);
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
		int colour=0;
		//System.out.println("x "+xmin+" to "+xmax+"  y "+ymin+" to "+ymax);
		for(double x = xmin; x <= xmax; x++)
		{
			for(double y =ymin; y <=ymax; y++)
			{	
				k = Math.exp( -Math.PI * (Math.pow( x - x0, 2) / a2 + Math.pow( y - y0, 2) / b2) );
				tmpVal =  -w * 2 * Math.PI * ( x-x0 ); 
				imPart = Math.sin( tmpVal );
				rePart = Math.cos( tmpVal );
				bimage.setRGB((int)x,(int) y, 0x10101* (int)(255.0*k));
				bimage.setRGB((int)x,(int) y+h, 0x10101* (int)(127.0*imPart+128.0));
				bimage.setRGB((int)x,(int) y+2*h, 0x10101* (int)(127.0*rePart+128.0));
				if (imPart<0) colour = -0x10000 *(int)(255.0*k*imPart);
				else colour = (int)(255.0*k*imPart);
				bimage.setRGB((int)x,(int) y+3*h, colour);
				if (rePart<0) colour = -0x10000 *(int)(255.0*k*rePart);
				else colour = (int)(255.0*k*rePart);
				bimage.setRGB((int)x,(int) y+4*h, colour);
			}
		}
		
		return bimage;
	}
}
