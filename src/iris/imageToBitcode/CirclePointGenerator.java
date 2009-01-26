package iris.imageToBitcode;

import java.util.Vector;

/**
 * Returns all points on a circle defined by the input centerpoint and radius
 * 
 * @author Arnar B. Jonsson
 * @version 1.0
 */
public class CirclePointGenerator {

	Vector<int[]> circlePoints;

	/**
	 * 
	 * @param xCenter x center point of circle
	 * @param yCenter y center point of circle
	 * @param r radius of circle
	 */
	public CirclePointGenerator(int xCenter, int yCenter, int r)
	{
		circlePoints = this.findCirclePoints(xCenter, yCenter, r);
	}

	/** @return a vector of all points lying of the given circle */
	public Vector<int[]> getCirclePoints() { return this.circlePoints; } 

	/** Returns an array of points lying on a circle with the given center and radius */
	private Vector<int[]> findCirclePoints(int xCenter, int yCenter, int radius)
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

	/**
	 * A function that uses the symmetry of a circle to output points that lie on the given circle
	 * @param cx x center point of circle
	 * @param cy y center point of circle
	 * @param x current x value
	 * @param y current y value
	 * @return vector of all symmetrical points
	 */
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
