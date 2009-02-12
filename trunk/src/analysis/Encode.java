
public class Encode {

	int[][] irisCode;
	int nRows;
	int nCols;

	/*
	 * Do nothing in constructor for the moment, this can be changed later, the
	 * idea is that once an object is created, the irisCode array is generated
	 * by calling the object's calcIrisCode method.
	 */
	public Encode(){}
	
	/*
	 * Calculate iris code from the convolvedImage array where
	 * convolvedImage[nRows][nCols*nScales] by looking at each 
	 * element in turn and assigning 0 or 1 accordingly.
	 * 
	 * The irisCode[nRows][nCols*nScales*2] is an array of
	 * integers and is built by filling the first half of it
	 * with the result of quantizing the real part of the
	 * complex-valued convolvedImage array, and then filling 
	 * the second part with the result of quantizing the 
	 * imaginary part.
	 */
	public void bitCode(Complex[][] convolvedImage){
				
		nRows = convolvedImage.length;
		nCols = convolvedImage[0].length;
	
		irisCode = new int[nRows][nCols*2];
		
		for(int i=0;i<nRows;i++){
			for(int j=0;j<nCols;j++){
				if(convolvedImage[i][j].getRe()>0) irisCode[i][j] = 1;
				else irisCode[i][j] = 0;
			}
		}
		for(int i=0;i<nRows;i++){
			for(int j=0;j<nCols;j++){
				if(convolvedImage[i][j].getIm()>0) irisCode[i][nCols+j] = 1;
				else irisCode[i][nCols+j] = 0;
			}
		}
	}	
}				