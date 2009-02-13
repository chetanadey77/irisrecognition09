package analysis;


/* This filtering is not complete.
 */

public class Gabor {

	// Attributes can be changed.
	double[][] rectI;
	int numCols;
	int numRows;

	
	Gabor(double[][] _rectI,int _numRows,int _numCols)

	{
		rectI = _rectI;
		numCols = _numCols;
		numRows = _numRows;		
		
	}

	
	public int[] irisCode()
	{
		int[][] irisRe = new int[numRows][numCols];
		int[][] irisIm = new int[numRows][numCols];
		int[] iris = new int[2*numRows*numCols]; // collect real and imaginary parts above for output.

		for(int n=0;n<numRows;n++)
		{
			for(int m=0;m<numCols;m++)
			{
				if(doImIntegral(n,m)>=0) irisIm[n][m] = 1;
				else irisIm[n][m] = 0;
				if(doReIntegral(n,m)>=0) irisRe[n][m] = 1;
				else irisRe[n][m] = 0;	
			}
		}

		// bring re and im parts together and put into iris.
		return iris;
	}


	private double doImIntegral(int n, int m)
	{
		double dr = 1/numRows;
		double dtheta = Math.toRadians(360)/numCols;
		double sum = 0;
		double alpha = 1;
		double beta = 1;
		double a_sq = alpha*alpha;
		double b_sq = beta*beta;

		for(int i=0;i<numRows;i++)
		{
			for(int j=0;j<numCols;j++)
			{
				sum = sum + Math.sin((n-j)*dtheta) * Math.exp(((m-i)*dr)/a_sq) * Math.exp(((n-j)*dtheta)/b_sq) * i * rectI[i][j]; 
			}
		}
		return sum; // don't need to multiply by dr^2dtheta factor since we only want to know if it's positive or not.
	}	


	private double doReIntegral(int n, int m)
	{
		double dr = 1/numRows;
		double dtheta = Math.toRadians(360)/numCols;
		double sum = 0;
		double alpha = 1;
		double beta = 1;
		double a_sq = alpha*alpha;
		double b_sq = beta*beta;

		for(int i=0;i<numRows;i++)
		{
			for(int j=0;j<numCols;j++)
			{
				sum = sum + Math.cos((n-j)*dtheta) * Math.exp(((m-i)*dr)/a_sq) * Math.exp(((n-j)*dtheta)/b_sq) * i * rectI[i][j]; 
			}
		}
		return sum; // don't need to multiply by dr^2dtheta factor since we only want to know if it's positive or not.
	}	
}
