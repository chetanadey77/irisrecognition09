package iris.imageToBitcode;

public class GaborParameters {
	
	double ab_lowLim, ab_upLim, ab_step; // alpha and beta range
	int ab_numVals; 
	double w_lowLim, w_upLim, w_step; // omega range
	int w_numVals; 
	double x0_lowLim, x0_upLim, x0_step; // x0 range
	int x0_numVals;
	double y0_lowLim, y0_upLim, y0_step; // y0 range
	int y0_numVals;
	
	public GaborParameters()
	{}
	
	// omega
	public void set_w(double _w_lowLim, double _w_upLim, int _w_numVals)
	{
		w_numVals = _w_numVals;
		w_lowLim = _w_lowLim;
		w_step = this.calcStep(_w_lowLim, _w_upLim, _w_numVals);
	}
	
	public double get_wStepN(int n)
	{
		return w_lowLim + n*w_step;
	}
	
	public int get_wNumVals() { return w_numVals; }
	
	//alpha and beta
	public void set_ab(double _ab_lowLim, double _ab_upLim, int _ab_numVals)
	{
		ab_numVals = _ab_numVals;
		ab_lowLim = _ab_lowLim;
		ab_step = this.calcStep(_ab_lowLim, _ab_upLim, _ab_numVals);
	}
	
	public double get_abStepN(int n)
	{
		return ab_lowLim + n*ab_step;
	}
	
	public int get_abNumVals() { return ab_numVals; }
	
	// x0
	public void set_x0(double _x0_lowLim, double _x0_upLim, int _x0_numVals)
	{
		x0_numVals = _x0_numVals;
		x0_lowLim = _x0_lowLim;
		x0_step = this.calcStep(_x0_lowLim, _x0_upLim, _x0_numVals);
	}
	
	public double get_x0StepN(int n)
	{
		return x0_lowLim + n*x0_step;
	}
	
	public int get_x0NumVals() { return x0_numVals; }
	
	// y0
	public void set_y0(double _y0_lowLim, double _y0_upLim, int _y0_numVals)
	{
		y0_numVals = _y0_numVals;
		y0_lowLim = _y0_lowLim;
		y0_step = this.calcStep(_y0_lowLim, _y0_upLim, _y0_numVals);
	}
	
	public double get_y0StepN(int n)
	{
		return y0_lowLim + n*y0_step;
	}
	
	public int get_y0NumVals() { return y0_numVals; }
	
	private double calcStep(double lowLim, double upLim, int numVals)
	{
		if (numVals == 1) {
			return 0;
		} else {
			return (upLim - lowLim)/(double)(numVals - 1);
		}
	}
}
