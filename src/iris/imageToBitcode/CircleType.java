package iris.imageToBitcode;
/**
 * Defines a cirlce location type, ie a centre (x,y) and a radius 
 * @author Edmund Noon
 * @version 1.0
 */
public class CircleType {
	public int x;
	public int y;
	public int radius;
	public double gradient; // for use in the iris location
	public CircleType(int nx, int ny , int nradius){x=nx;y=ny;radius=nradius;}
	public CircleType(int nx, int ny , int nradius,double grad){x=nx;y=ny;radius=nradius;gradient=grad;}
	public CircleType(){}
}
