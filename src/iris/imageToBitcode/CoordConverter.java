package iris.imageToBitcode;



/**
 * A class that converts from r-theta cooridinates into x-y space given the centerpoint
 * and radii of pupil and iris.
 * Involves the following equations:
 * 
 * x(r,theta) = (1-r)*xp(theta) + r*xi(theta).........(1)
 * y(r,theta) = (1-r)*yp(theta) + r*yi(theta).........(2)
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
		//r=rescale(r,theta);
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
		
		//r=rescale(r,theta); see comment below.
		return (int) ((1-r)*ypTheta + r*yiTheta);
	}
	
	/*
	 * The idea (which I got from a paper I still have) is that in addition to the equations marked (1) and (2) in the comment 
	 * at the top of this file, the radius variable r should, somehow, also have a rescaling factor applied to it so as to take 
	 * into account the fact that the iris and pupil are not concentric. I am not sure what they are getting at here but it might 
	 * be worth getting clarification on. It might make a difference.
	 */
	private double rescale(double _r,double _theta)
	{	
		double ox = xp - xi; // used in re-scaling formula, measure the offset of the two centres.
		double oy = yp - yi;
		
		double pi = Math.PI;
		
		double tanSlope; // tanSlope = tan(oy/ox) 
		// separate out the special case case where ox = 0, which makes atan = pi/2.
		if(ox!=0) tanSlope = Math.atan(oy/ox);
		else tanSlope = pi/2;
		
		int sign; // separate out + and - cases in re-scaling formula.		
		if(ox<=0) sign = -1;
		else sign = 1;
		if(ox==0 && oy>0) sign = 1;
		double alpha, beta; // used in re-scaling formula
		double radians = 2*pi*_theta;			
		beta = Math.cos(pi-tanSlope-radians);	
		alpha = ox*ox + oy*oy;
		double sralpha = Math.sqrt(alpha);
		double irissq = ri*ri;
		
		double rScale;
		rScale = sralpha*beta + sign*Math.sqrt(alpha*beta*beta - alpha - irissq);
		
		return rScale*_r;
	}
	
}
