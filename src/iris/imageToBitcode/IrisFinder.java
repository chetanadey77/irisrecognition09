package iris.imageToBitcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

/**
 * A class that takes as input an image and extracts the 
 * parameters which constrain the iris
 * 
 * @author Arnar B. Jonsson
 * @version 1.0
 */
public class IrisFinder {
	
	BufferedImage eye, blurredEye;
	int xPupil,yPupil,rPupil;
	int xIris,yIris,rIris;
	
	/**	@param eyeImage	image of a human eye */
	public IrisFinder(BufferedImage eyeImage)
	{	
		eye = eyeImage;
		this.findIrisCenterAndRadius();
		this.findPupilCenterAndRadius();
	}
	
	/** @return			the x value of pupil center */
	public int getPupilX()	{ return xPupil; }
	
	/** @return			the y value of pupil center */
	public int getPupilY()	{ return yPupil;	}
	
	/** @return			the radius of iris inner boundary (pupil) */
	public int getPupilR()	{ return rPupil; }
	
	/** @return			the x value of iris center */
	public int getIrisX()	{ return xIris; }
	
	/** @return			the y value of iris center */
	public int getIrisY()	{ return yIris;	}
	
	/** @return			the radius of iris outer boundary (sclera) */
	public int getIrisR()	{ return rIris; }
	
	/** Finds and sets the centerpoint and radius of pupil */
	void findPupilCenterAndRadius()
	{
		
	}
	
	/** Finds and sets the centerpoint and radius of pupil */
	void findIrisCenterAndRadius()
	{
		
	}
	
	/** Finds and sets the centerpoint and radius of pupil */
	private BufferedImage getBlurredEyeImage(BufferedImage bimg)
	{
		
		return bimg;
	}
	

}
 
