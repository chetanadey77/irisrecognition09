package iris.imageToBitcode;

public class GaborParameters {
	

	public double lowLim, upLim, step; 
	public int numVals; 

	public GaborParameters()
	{}
	
	public GaborParameters(double _lowLim, double _upLim, int _numVals)
	{
		this.set(_lowLim, _upLim, _numVals);
	}
	
	public void set(double _lowLim, double _upLim, int _numVals)
	{
		lowLim = _lowLim;
		upLim = _upLim;
		numVals = _numVals;
		step = this.calcStep(_lowLim, _upLim, _numVals);
	}
	
	public double get_StepN(int n)
	{
		return lowLim + n*step;
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
