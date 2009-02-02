package iris.imageToBitcode;

import java.awt.image.BufferedImage;

import iris.bitcodeMatcher.BitCode;


/**
 * A class that uses Gabor filters to generate a bitcode from an image of an iris
 * 
 * @author Arnar B. Jonsson, Mark Howe
 * @version 1.0
 */
public class BitcodeGenerator {

	BitCode bitcode;

	double ab_lowLim; // alpha and beta lower limit
	double ab_upLim; // alpha and beta upper limit
	double ab_numSteps; // alpha and beta number of steps
	double ab_step; // alpha and beta size of one step
	
	double w_lowLim; // omega lower limit
	double w_upLim; // omega upper limit
	double w_numSteps; // omega number of steps
	double w_step; // omega size of one step
	
	public BitcodeGenerator()
	{	
		bitcode = new BitCode(2048);
		ab_lowLim = 0.15;
		ab_upLim = 1.2;
		ab_numSteps = 8;
		ab_step = (ab_upLim - ab_lowLim)/ab_numSteps;
		
		w_lowLim = 1/ab_upLim;
		w_upLim  = 1/ab_lowLim;
		w_numSteps = 8;
		w_step = (w_upLim - w_lowLim)/w_numSteps;
	}
	
	public int[] getBitcode(BufferedImage eyeImage)
	{
		for (double a = ab_lowLim; a < ab_upLim; a += ab_step  )
		{
			for (double b = ab_lowLim; b < ab_upLim; b += ab_step )
			{
				for (double w = w_lowLim; w < w_upLim; w += w_step)
				{
					bitcode.addBit(imIntegral(eyeImage,a,b,w));
				}
			}
		}
		
		return bitcode.getBitCode();
	}
	
	private boolean imIntegral(BufferedImage eyeImage, double a, double b, double w)
	{
		double sum = 0;
		double theta_0 = 0;
		double r_0 = 0;
		double a2 = a*a;
		double b2 = b*b; 
		
		coordConverter c = new coordConverter(160,160,80,140,140,120);
		
		for(double r=0; r<1; r+= 0.1)
		{
			for(double theta=0; theta < 360; theta += 1)
			{
				int rgb = eyeImage.getRGB(c.getX(r,theta), c.getY(r,theta));
				double k = rgb * Math.exp(-Math.pow(r - r_0, 2) / a2 ) * Math.exp(-Math.pow(theta-theta_0,2) / b2 );
				sum += k*Math.cos(-w*(theta-theta_0)); 
			}
		}
		
		return (sum >= 0);
	}
	
	private double reIntegral(BufferedImage eyeImage)
	{
		return 0;
	}
	
	
}
