package iris.imageToBitcode;

public class coordConverter {
	
	int xp, yp, rp, xi, yi, ri;
	int xDiff, yDiff;
	
	public coordConverter(int xPup, int yPup, int rPup, int xIris, int yIris, int rIris)
	{
		xp = xPup;
		yp = yPup;
		rp = rPup;
		xi = xIris;
		yi = yIris;
		ri = rIris;
		xDiff = xp-xi;
		yDiff = xp-xi;
	}
	
	public int getX(float r, float theta)
	{
		return 0;
	}
	
	public int getY(float r, float theta)
	{
		return 0;
	}
	
	
}
