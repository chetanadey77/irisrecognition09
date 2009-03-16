package iris.imageToBitcode;
/**
* A class to define and contain a range of parameters for creating Gabor wavelets
* @author Arnar B. Jónsson
* @version 1.0
*/

public class GaborParameters {
	
	public double lowLim, upLim, step; 
	public int numVals; 

	public GaborParameters()
	{}
	
	public GaborParameters(double _lowLim, double _upLim, int _numVals)
	{
		this.set(_lowLim, _upLim, _numVals);
	}
	
	/**
	 * Set parameter range
	 * @param _lowLim Lower limit of range
	 * @param _upLim Upper limit of range
	 * @param _numVals number of values in range
	 */
	public void set(double _lowLim, double _upLim, int _numVals)
	{
		lowLim = _lowLim;
		upLim = _upLim;
		numVals = _numVals;
		step = this.calcStep(_lowLim, _upLim, _numVals);
	}
	
	/**
	 * Get step N from the current range
	 * @param n Step number
	 * @return Value of step N
	 */
	public double get_StepN(int n)
	{
		return lowLim + n*step;
	}
	
	/**
	 * Generate a new set of parameters by adding two parameter sets together
	 * @param gp Input Gabor parameter set
	 * @param scale Multiplier for addition
	 * @return A new Gabor parameter set, resulting from the addition
	 */
	public GaborParameters add(GaborParameters gp,double scale)
	{
		GaborParameters temp = new GaborParameters(lowLim + (scale *gp.lowLim),upLim + (scale * gp.upLim),numVals);
		return temp;
	}
	
	private double calcStep(double lowLim, double upLim, int numVals)
	{
		if (numVals == 1) {
			return 0;
		} else {
			return (upLim - lowLim)/(double)(numVals - 1);
		}
	}
	

}
