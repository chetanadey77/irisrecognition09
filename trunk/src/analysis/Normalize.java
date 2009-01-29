package analysis;


/* Involves the following equations:
 * 
 * x(r,theta) = (1-r)*xp(theta) + r*xi(theta)
 * y(r,theta) = (1-r)*yp(theta) + r*yi(theta)
 * 
 * where xp(theta) = xp + r*cos(theta), is the pupil x-coordinate
 * yp(theta) = yp + r*sin(theta), is the y-coordinate, and r is the 
 * position along (r goes from 0 to 1, 0 at iris inner boundary and 
 * 1 at iris outer boundary), and similarly for the iris (xi,yi).
 * 
 * Also, there is the re-scaling formula:
 * 
 * rPrime = beta*sqrt(alpha) +/- sqrt(alpha*beta*beta-alpha-irisR).
 * 
 * Method (?) needs to be verified but seems to be: 
 * 
 * For a given angle theta, calculate rPrime, the distance from the 
 * centre of the pupil to the outer iris boundary, taking the (grid)
 * coordinates  = (xi,yi) in the above formula, linearly interpolate
 * between this point and (xp,yp), get the image intensity corresponding
 * to this interpolated position (nearest integer coordinates on image 
 * grid and map this value to the output rectangle (r,theta).
 * 
 * Questions:
 * 
 * Well, is this right? Does the idea of finding the nearest integer 
 * coordinates make sense? If not, then how can you deal with the fact that at 
 * different angles different iris thicknesses are involved? Surely some kind
 * of averaging would need to be done?
 */

public class Normalize {
	
	public double[][] unwrap(double[][] image,int rowMax,int colMax,int pupilX,int pupilY,int pupilR,int irisX,int irisY,int irisR)
	{
		int numR = 8; // number of rows in output rectangle = number of radial divisions.
		int numC = 128; // number of columns in output rectangle = number of angular divisions.
		double[][] rectImage = new double[numR][numC]; // the output rectangular image.	

		double ox = pupilX - irisX; // used in re-scaling formula, measure the offset of the two centres.
		double oy = pupilY - irisY;
		
		double pi = Math.PI;
		
		double tanSlope; // tanSlope = tan(oy/ox) 
		// separate out the special case case where ox = 0, which makes atan = pi/2.
		if(ox!=0) tanSlope = Math.atan(oy/ox);
		else tanSlope = pi/2;
		
		int sign; // separate out + and - cases in re-scaling formula.		
		if(ox<=0) sign = -1;
		else sign = 1;
		if(ox==0 && oy>0) sign = 1;

		double alpha, beta; // used in re-scaling formula.
		alpha = ox*ox + oy*oy;
		double sralpha = Math.sqrt(alpha);
		double irissq = irisR*irisR;
		
		// outer loop is over angles (columns in output array).
		for(int theta=0;theta<numC;theta++)
		{
			double radians = 2*pi*theta/numC; // current angular value in radians.
			double cos = Math.cos(radians); // current cosine of this angle.
			double sin = Math.sin(radians);
				
			beta = Math.cos(pi-tanSlope-radians);
			/* rPrime is as in the re-scaling formula, the idea (?) is to re-scale 
			 * the points around the outer iris boundary according to their angle 
			 * around the circle.
			 */ 
			double rPrime;
			rPrime = sralpha*beta + sign*Math.sqrt(alpha*beta*beta - alpha - irissq);
			
			for(int r=0;r<numR;r++)
			{
				double rfrac = r/numR;
				int x = (int)(((1-rfrac)*(pupilX+pupilR*cos)) + rfrac*(irisX+irisR*cos));
				int y = (int)(((1-rfrac)*(pupilY+pupilR*sin)) + rfrac*(irisY+irisR*sin));		
				rectImage[r][theta] = image[x][y];
			}
		}
		return rectImage;
	}			
}		
	