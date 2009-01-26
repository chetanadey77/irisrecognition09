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
import java.util.Vector;

import javax.imageio.ImageIO;

public class IrisFinder {
	
	BufferedImage eye;
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
	
	/** Returns an array of points lying on a circle with the given center and radius */
    public Vector<int[]> circlePoints(int xCenter, int yCenter, int radius)
    {
    	Vector<int[]> v = new Vector<int[]>();
        int x = 0;
        int y = radius;
        int p = (5 - radius*4)/4;
        v.addAll(circleSymmetricPoints(xCenter, yCenter, x, y));
        
        while (x < y) {
            x++;
            if (p < 0) {
                p += 2*x+1;
            } else {
                y--;
                p += 2*(x-y)+1;
            }
            v.addAll(circleSymmetricPoints(xCenter, yCenter, x, y));
        }
        
        return v;
    }

    private Vector<int[]> circleSymmetricPoints(int cx, int cy, int x, int y)
    {      
    	
    	Vector<int[]> v = new Vector<int[]>();
    	
        if (x == 0) { 
            v.add(new int[] {cx,cy+y} );
            v.add(new int[] {cx, cy - y} );
            v.add(new int[] {cx + y, cy} );
            v.add(new int[] {cx - y, cy} );
        } else 
        if (x == y) {
            v.add(new int[] {cx + x, cy + y} );
            v.add(new int[] {cx - x, cy + y} );
            v.add(new int[] {cx + x, cy - y} );
            v.add(new int[] {cx - x, cy - y} );
        } else 
        if (x < y) {
            v.add(new int[] {cx + x, cy + y} );
            v.add(new int[] {cx - x, cy + y} );
            v.add(new int[] {cx + x, cy - y} );
            v.add(new int[] {cx - x, cy - y} );
            v.add(new int[] {cx + y, cy + x} );
            v.add(new int[] {cx - y, cy + x} );
            v.add(new int[] {cx + y, cy - x} );
            v.add(new int[] {cx - y, cy - x} );
        }
        
        return v;
    }


	


}
 
