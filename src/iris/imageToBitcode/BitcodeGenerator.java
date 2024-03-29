package iris.imageToBitcode;

import java.awt.image.BufferedImage;

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
	int[][][] gaussFD;
	int[][][] gaussSD;

	int xp, yp, rp, xi, yi, ri;

	int unwrWidth, unwrHeight;

	double a, b, w, a2, b2, x0, y0;	// alpha, beta and omega

	public BitcodeGenerator()
	{		
		// width and height of the unwrapped image. Up those numbers => more detail and longer runtime
		int _unwrWidth = 360;
		int _unwrHeight = 100;
		
		// set ranges and number of steps for omega, alpha, beta, x0 and y0
		GaborParameters _wPar = new GaborParameters((1.62/16.0),(1.62/36.0) , 3);
		GaborParameters _abPar = new GaborParameters(8, 18, 3);
		GaborParameters _x0Par = new GaborParameters(_abPar.upLim, _unwrWidth+_abPar.upLim , (int) (_unwrWidth) );
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
	 * Generate bitcode
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
	
	/**
	 * set the Gabor parameter ranges
	 * @param _wPar omega
	 * @param _abPar alpha and beta 
	 * @param _x0Par x_0
	 * @param _y0Par y_0
	 * @param _unwrWidth width of unwrapped image
	 * @param _unwrHeight height of image
	 */
	public void initialiseParams(GaborParameters _wPar,GaborParameters _abPar, GaborParameters _x0Par, GaborParameters _y0Par, int _unwrWidth, int _unwrHeight)
	{
		initialiseParams( _wPar,_abPar,  _x0Par, _y0Par,  _unwrWidth, _unwrHeight,2);
	}
	
	/**
	 * set the Gabor parameter ranges
	 * @param _wPar omega
	 * @param _abPar alpha and beta 
	 * @param _x0Par x_0
	 * @param _y0Par y_0
	 * @param _unwrWidth width of unwrapped image
	 * @param _unwrHeight height of image
	 * @param _bitsPerBox 1 for one and 2 for two (1 ignores the real part)
	 */
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
		gaussFD = new int[bitcodeShiftNum][(int)abPar.upLim*2+1][(int)abPar.upLim*2+1];
		gaussSD = new int[bitcodeShiftNum][(int)abPar.upLim*2+1][(int)abPar.upLim*2+1];
		double k,tmpVal;

		for(int series=0;series<bitcodeShiftNum;series++)
		{
			int cos_bias=0;
			int cos_pos=0;
			int cos_neg=0;
			int cos_bias_count=0;
			int gFD_bias = 0;
			int gSD_bias = 0;
			int ab= (int) abPar.get_StepN(series);
			double lw = wPar.get_StepN(series);
			double gauss_scale = 0.5 + wPar.lowLim;
			double gauss=0.0;
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
					if (gaborReal[series][x+ab][y+ab] >0) cos_pos += gaborReal[series][x+ab][y+ab];
					else cos_neg -= gaborReal[series][x+ab][y+ab];
					cos_bias  += gaborReal[series][x+ab][y+ab];
					cos_bias_count++;
					gaborImaginary[series][x+ab][y+ab] = (int)(65536.0 * k * Math.sin( tmpVal));
					//these are 65536 times too big, but we only care about the sign!
					gauss = Math.exp(-Math.PI * gauss_scale *(x*x +y*y)/ab2);
					gaussFD[series][x+ab][y+ab] = 
						(int)(-65536.0 * gauss * Math.PI * 2.0*gauss_scale * x/ab2);
					gaussSD[series][x+ab][y+ab] = 
						(int)(65536.0 *( gauss * 4.0 * Math.PI* Math.PI* gauss_scale * gauss_scale * x * x/(ab2*ab2) - 
								2*Math.PI*gauss_scale*gauss/ab2));
					gFD_bias += gaussFD[series][x+ab][y+ab];
					gSD_bias += gaussSD[series][x+ab][y+ab];
					
				}
			//need to make the real term average to zero
			for(int x = -ab; x <= ab; x++)
				for(int y =-ab; y <=ab; y++)
				{
					//first one is for subtracting the average
					//second one scales the negative
					gaborReal[series][x+ab][y+ab] =gaborReal[series][x+ab][y+ab] - cos_bias/cos_bias_count;
					//if(gaborReal[series][x+ab][y+ab]<0) gaborReal[series][x+ab][y+ab] *= cos_pos/cos_neg;
					gaussFD[series][x+ab][y+ab] = gaussFD[series][x+ab][y+ab] - gFD_bias/cos_bias_count;
					gaussSD[series][x+ab][y+ab] = gaussSD[series][x+ab][y+ab] - gSD_bias/cos_bias_count;
				}	
		}	
	}

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
			}
		}

		if (bitsPerBox==2) bitcode.addBit(sumRe >= 0.0);
		bitcode.addBit(sumIm >= 0.0);
		//System.out.println(sumRe+" "+sumIm);
	}
	
	/**
	 * Does the same as getBitcode, but faster.Uses integer arithmetic
	 * @param eyeImage image of eye to be converted
	 * @param eye data for cetre of pulil and iris
	 * @return Bitcode
	 */
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
	/**
	 * This is for comparison, it doesn't use Gabor filters, but the first and 
	 * second derivative of the Gaussian instead
	 * @param eyeImage
	 * @param eye
	 * @return
	 */
	public BitCode getFastGaussianCode(BufferedImage eyeImage,EyeDataType eye)
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
						sumRe += imgVal*gaussFD[i][x+ab[i]][y+ab[i]];
						sumIm += imgVal*gaussSD[i][x+ab[i]][y+ab[i]];
					}
				}
				if (bitsPerBox==2) bitcode.addBit(sumRe >= 0);
				bitcode.addBit(sumIm >= 0);
			}
		}
		bitcode.setShiftNum(bitcodeShiftNum*bitsPerBox);
		return bitcode;
	}
	
}
