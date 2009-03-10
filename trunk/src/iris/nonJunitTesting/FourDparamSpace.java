package iris.nonJunitTesting;

public class FourDparamSpace {
	int _minBox;
	int _maxBox;
	double _lambda;
	double _scale;
	
	public FourDparamSpace(int minB,int maxB,double lambda,double scale){
		_minBox = minB;
		_maxBox = maxB;
		_lambda = lambda;
		_scale = scale;
	}
	public FourDparamSpace(){
		_minBox = 0;
		_maxBox = 0;
		_lambda = 0.0;
		_scale = 0.0;
	}
	public FourDparamSpace add(FourDparamSpace a, FourDparamSpace b)
	{
		FourDparamSpace c = new FourDparamSpace();
		c._minBox = a._minBox + b._maxBox;
		c._maxBox = a._maxBox + b._maxBox;
		c._lambda = a._lambda + b._lambda;
		c._scale =  a._scale  + b._scale;
		return c;
	}
	public static boolean  isValid(FourDparamSpace a)
	{
		if (a._minBox<2) return false;
		if (a._maxBox>45) return false;
		if ((a._maxBox-a._minBox)<2) return false;
		return true;
	}
	public FourDparamSpace subtract(FourDparamSpace a, FourDparamSpace b)
	{
		FourDparamSpace c = new FourDparamSpace();
		c._minBox = a._minBox - b._maxBox;
		c._maxBox = a._maxBox - b._maxBox;
		c._lambda = a._lambda - b._lambda;
		c._scale =  a._scale  - b._scale;
		return c;
	}
	public FourDparamSpace divide(FourDparamSpace a, int n)
	{
		FourDparamSpace c = new FourDparamSpace();
		c._minBox = a._minBox / n;
		c._maxBox = a._maxBox / n;
		c._lambda = a._lambda / (double) n;
		c._scale =  a._scale / (double) n;
		return c;
	}

}
