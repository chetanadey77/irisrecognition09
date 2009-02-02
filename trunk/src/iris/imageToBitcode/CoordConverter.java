package iris.imageToBitcode;

/**
 * A class that converts from r-theta cooridinates into x-y space given the centerpoint
 * and radii of pupil and iris.
 * 
 * Involves the following equations:
 * 
 * x(r,theta) = (1-r)*xp(theta) + r*xi(theta)
 * y(r,theta) = (1-r)*yp(theta) + r*yi(theta)
 * 
 * where xp(theta) = xp + r*cos(theta), is the pupil x-coordinate
 * yp(theta) = yp + r*sin(theta), is the y-coordinate, and r is the 
 * position along (r goes from 0 to 1, 0 at iris inner boundary and 
 * 1 at iris outer boundary), and similarly for the iris (xi,yi).
 * 
 * @author Arnar B. J�nsson, Mark Howe
 * @version 1.0
 */
public class CoordConverter {
	
	double xp, yp, rp, xi, yi, ri;
	
	/**
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 */
	public CoordConverter(int xPup, int yPup, int rPup, int xIris, int yIris, int rIris)
	{
		xp = xPup;
		yp = yPup;
		rp = rPup;
		xi = xIris;
		yi = yIris;
		ri = rIris;
	}
	
	/**
	 *  @param r Radius in the rang of [0,1]
	 *  @param Theta angle in the range of [0,360]
	 *  @return Calculated x-value in original image
	 */
	public int getX(double r, double theta)
	{
		theta = Math.toRadians(theta);
		double xpTheta = xp + rp*Math.cos(theta);
		double xiTheta = xi + ri*Math.cos(theta);
		return (int) Math.round(((1-r)*xpTheta + r*xiTheta));
	}
	
	/**
	 *  @param r Radius in the rang of [0,1]
	 *  @param theta Angle in the range of [0,360]
	 *  @return Calculated y-value in original image
	 */
	public int getY(double r, double theta)
	{
		theta = Math.toRadians(theta);
		double ypTheta = yp + rp*Math.sin(theta);
		double yiTheta = yi + ri*Math.sin(theta);
		return (int) ((1-r)*ypTheta + r*yiTheta);
	}
	
}
