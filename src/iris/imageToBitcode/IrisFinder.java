/**
 * A class that takes as input an image and extracts the 
 * parameters which constrain the iris
 * 
 * @author Arnar B. Jonsson
 * @version 1.0
 */
package iris.imageToBitcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IrisFinder {
	
	BufferedImage eye;
	int x,y,r1,r2;
	
	/**	@param bimg		image of a human eye */
	public IrisFinder(BufferedImage bimg)
	{	
		eye = bimg;
	}
	
	/** @return			the x value of eyeball center */
	public int getX()	{ return x; }
	
	/** @return			the y value of eyeball center */
	public int getY()	{ return y;	}
	
	/** @return			the radius of iris inner boundary (pupil) */
	public int getR1()	{ return r1; }
	
	/** @return			the radius of iris outer boundary (???) */
	public int getR2()	{ return r2; }


}
