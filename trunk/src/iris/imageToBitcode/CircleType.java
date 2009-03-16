package iris.imageToBitcode;

public class CircleType {
	public int x;
	public int y;
	public int radius;
	public double gradient; // for use in the iris location
	public CircleType(int nx, int ny , int nradius){x=nx;y=ny;radius=nradius;}
	public CircleType(int nx, int ny , int nradius,double grad){x=nx;y=ny;radius=nradius;gradient=grad;}
	public CircleType(){}
}
