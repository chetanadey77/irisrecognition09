package iris.imageToBitcode;

/**
 * Defines a datatype to be used to reference characteristics of an eye.
 * 
 * Currently it only has two CircleTypes one for the pupil and one for the outer iris boundary
 * 
 * In the future it might have eyelid location and possibly eye lash location and specularity
 * locations
 * 
 * @author Edmun Noon
 * @version 1.0
 */
public class EyeDataType {
	   public CircleType inner;
       public CircleType outer;
       
	public EyeDataType(int ix, int iy, int irad, int ox, int oy, int orad) {
		inner = new CircleType();
	    outer = new CircleType();
		inner.x=ix;
 	   	inner.y=iy;
 	   	inner.radius=irad;
 	   	outer.x=ox;
 	   	outer.y=oy;
 	   	outer.radius = orad;
	}

	public EyeDataType()
	{
		inner = new CircleType();
	    outer = new CircleType();
	}

}
