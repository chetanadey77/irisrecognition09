package iris.imageToBitcode;

import java.awt.image.BufferedImage;

/**
 * Takes in an image and returns a bitcode which can be used to identify
 * an individual.
 * 
 * @author Arnar B. Jonsson
 * @version 1.0
 */
public class ImageToBitcode {
	
	byte[] bitcode;
	
	public ImageToBitcode()
	{
		bitcode = new byte[256];
	}
	
	public byte[] getBitcode(BufferedImage img, int xp, int yp, int rp, int xi, int iy, int ri)
	{
		return bitcode;
	}
	
	private void generateBitcode()
	{
		
	}
	

}
