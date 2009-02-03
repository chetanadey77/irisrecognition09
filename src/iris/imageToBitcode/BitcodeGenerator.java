package iris.imageToBitcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import sun.org.mozilla.javascript.internal.UintMap;
import unittest.ImageToBitcode.ImageSaverLoader;

import iris.bitcodeMatcher.BitCode;


/**
 * A class that uses Gabor filters to generate a bitcode from an image of an iris
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
	
	double a,b,w;	// alpha, beta and omega
	double ab_lowLim; // alpha and beta lower limit
	double ab_upLim; // alpha and beta upper limit
	int ab_numSteps; // alpha and beta number of steps
	double ab_step; // alpha and beta size of one step
	
	double w_lowLim; // omega lower limit
	double w_upLim; // omega upper limit
	int w_numSteps; // omega number of steps
	double w_step; // omega size of one step
	
	double a2, b2, theta_0, r_0;
	
	public BitcodeGenerator(int xPup, int yPup, int rPup, int xIris, int yIris, int rIris)
	{	
		xp = xPup; yp = yPup; rp = rPup;
		xi = xIris; yi = yIris; ri = rIris;
		
		bitcode = new BitCode(2048);
		c = new CoordConverter(xp,yp,rp,xi,yi,ri);
		uw = new UnWrapper();
		
		double divider = 100;
		double divider2 = 50;
		
		ab_lowLim = 0.15/divider;
		ab_upLim = 1.2/divider;
		ab_numSteps = 8;
		ab_step = (ab_upLim - ab_lowLim)/ab_numSteps;
		
		w_lowLim = 1/ab_upLim;
		w_upLim  = 1/ab_lowLim;
		w_numSteps = 16;
		w_step = (w_upLim - w_lowLim)/w_numSteps;
		
	}
	
	public int[] getBitcode(BufferedImage eyeImage)
	{
		//unWrapped = uw.unWrap(eyeImage,xp,yp,rp,xi,yi,ri,50,180);
		intensityArr = uw.unWrapByteArr(eyeImage, xp, yp, rp, xi, yi, ri, 8, 128);
		
		for (float i=0; i<ab_numSteps; i++)
		{
			for (float j=0; j<ab_numSteps; j++ )
			{
				for (float k=0; k<w_numSteps; k++)
				{
					a = ab_lowLim + i*ab_step;
					b = ab_lowLim + j*ab_step;
					w = w_lowLim + k*w_step;
					this.integrate();
				}
			}
		}
		
		return bitcode.getBitCode();
	}
	
	private void integrate()
	{
		theta_0 = 0;
		r_0 = 0;
		a2 = a*a;
		b2 = b*b; 
		double k1,k2,imPart,rePart;
		double sumRe = 0;
		double sumIm = 0;		
		double theta, r;
		
		int numThetaPixels = intensityArr.length;
		int numRpixels = intensityArr[0].length;
		
		for(int i = 0; i < numThetaPixels-1; i++)
		{
			for(int j=0; j < numRpixels-1; j++)
			{
				r = j/(double)numRpixels;
				theta = Math.toRadians(360*i/(double)numThetaPixels);
				
				k1 = Math.exp( -Math.pow( r - r_0, 2) / a2 );
				k2 = Math.exp( -Math.pow( theta - theta_0, 2) / b2 );
				imPart = Math.sin( -w * ( theta - theta_0) );
				rePart = Math.cos( -w * ( theta - theta_0) );
				
				sumRe += (double)intensityArr[i][j] * k1 * k2 * imPart;
				sumIm += (double)intensityArr[i][j] * k1 * k2 * rePart; 
			}
		}
		System.out.println((float)sumRe + " ::: " + (float)sumIm);
		bitcode.addBit(sumRe >= 0);
		bitcode.addBit(sumIm >= 0);
	}
	
	
	
}
