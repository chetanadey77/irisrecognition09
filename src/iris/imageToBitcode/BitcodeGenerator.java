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

	BufferedImage eImage;
	BitCode bitcode;
	CoordConverter c;
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
	
	public BitcodeGenerator()
	{	
		bitcode = new BitCode(2048);
		c = new CoordConverter(182,134,37,182,134,100);
		double divider = 50;
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
		eImage = new BufferedImage(eyeImage.getWidth(),eyeImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = eImage.getGraphics();
		g.drawImage(eyeImage,0,0,null);
		
		for (double i=0; i<ab_numSteps; i++)
		{
			for (double j=0; j<ab_numSteps; j++ )
			{
				for (double k=0; k<w_numSteps; k++)
				{
					a = ab_lowLim + i*ab_step;
					b = ab_lowLim + j*ab_step;
					w = w_lowLim + k*w_step;
					this.integrate();
					
					//System.out.println(a + "-" + b + "-" + w);
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
		
		double sumRe = 0;
		double sumIm = 0;
		double r_step = 0.1;
		double th_step = 2;
		Color col;
		
		for(double r=0; r<1; r+= r_step)
		{
			for(double theta=0; theta < 360; theta += th_step)
			{
				double th = Math.toRadians(theta);
				int rgb = eImage.getRGB(c.getX(r,theta), c.getY(r,theta));
				
				col = new Color(rgb);
				double intensity = (col.getBlue() + col.getGreen() + col.getRed()) / 3;
				
				double k1 = Math.exp( -Math.pow( r - r_0, 2) / a2 );
				double k2 = Math.exp( -Math.pow( th - theta_0, 2) / b2 );
				double imPart = Math.sin( -w * ( th - theta_0) );
				double rePart = Math.cos( -w * ( th - theta_0) );
				
				sumRe += r_step * th_step * intensity * k1 * k2 * imPart;
				sumIm += r_step * th_step * intensity * k1 * k2 * rePart; 
			}
		}
		
		bitcode.addBit(sumRe >= 0);
		bitcode.addBit(sumIm >= 0);
	}
	
	
	
}
