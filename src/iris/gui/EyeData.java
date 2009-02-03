package iris.gui;

public class EyeData {
	   public CircleType inner;
       public CircleType outer;
       
	public EyeData(int ix, int iy, int irad, int ox, int oy, int orad) {
		inner = new CircleType();
	    outer = new CircleType();
		inner.x=ix;
 	   	inner.y=iy;
 	   	inner.radius=irad;
 	   	outer.x=ox;
 	   	outer.y=oy;
 	   	outer.radius = orad;
	}

	public EyeData()
	{
		inner = new CircleType();
	    outer = new CircleType();
	}

}
