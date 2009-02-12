public class Convolve {

	Complex[][] convolvedImage;
	
	public Convolve(){}
	
	/*
	 * Use the log-Gabor function g(w;s) = exp(-log(w/w0)^2/2*log(k/w0)^2) 
	 * to convolve the input image[nRows][nCols] and produce as output a 
	 * complex-valued convolvedImage[nRows][nCols*nScales] so that the 
	 * convolution is done separately for each scale. w is the frequency
	 * scale that the function takes values over, w0 the central frequency
	 * and k/w0 a constant.
	 */
	public void convolveImage(double[][] unWrappedImage,int nScales,double minWave,double multFact,double constTerm){
				
		int nRows = unWrappedImage.length;
		int nCols = unWrappedImage.length;
		/*
		 * The output array.
		 */
		Complex[][] ans = new Complex[nRows][nCols*nScales];
		/*
		 * FFT assumes that array is a power of 2, so get rid of one column if it is not.
		 */
		if(nCols % 2 != 0) nCols -= 1; 

		double[] logGabor = new double[nCols];
		for(int i=0;i<nCols;i++) logGabor[i] = 0.0;

		//double[] result = new double[nCols];
		//for(int i=0;i<nCols;i++) result[i] = 0.0;
		
		/*
		 * Convolution is done along a row, so the column j is the "position" variable.
		 * w is then some function of j. It seems to be the casr that the logGabor function
		 * is is sampled up to the midway point along the image and set to 0 for the other half.
		 * If this is so, then just create an array for the values of w which goes from 0 to nCols/2.
		 */
		double[] w = new double[nCols/2];
		/*
		 * According to Masek, w should range from 0 at the edge of the image to 0.5
		 * at the boundary and because 0 will give trouble in the log function it gets
		 * replaced later by 1.
		 */
		for(int i=1;i<nCols/2;i++) w[i] = i/nCols;
		w[0] = 1; // This is compensated for later in (*).
		
		double wavelength = minWave;
		for(int s=1;s<=nScales;s++){
			double w0 = 1/wavelength;
			w0 = 0.5*w0; // Normalized w0.
			/*
			 * Sample the filter up to half-way? Not sure about this though.
			 */
			for(int i=1;i<nCols/2+1;i++) logGabor[i] = Math.exp(-(Math.log(w[i]/w0))^2/2*Math.log(constTerm^2));
			logGabor[0] = 0; // (*)
			/*
			 * Convolve each row. Convolution theorem says that the result of the convolution
			 * will be, row-by-row, ifft(fft(image)*logGabor), this is stated in Daugman's
			 * notes and in a paper showing that for a log-Gabor filter an fft does not need to
			 * be taken. Some understanding of why the fft does not need to be taken would be nice.
			 */
			for(int r=0;r<nRows;r++){
				/*
				 * First get the fft of the current row of the image.
				 */
				/* There may be a quicker way to do this but for now assume that
				 * the fft will take in a complex-valued array, so put the real-valued
				 * image array into a complex-valued array.
				 */				
				Complex[] imageRow = new Complex[nCols];
				for(int i=0;i<nCols;i++) imageRow[i] = new Complex(unWrappedImage[3][i],0.0);
		
				Complex[] fftImage = new Complex[nCols];
				fftImage = fft(imageRow); 
				/*
				 * Multiply this array into log-Gabor function.
				 */
				Complex[] product = new Complex[nCols];
				for(int i=0;i<nCols;i++) product[i] = imageRow[i].mult(logGabor[i]);
				/*
				 * Take the inverse fft of this product to get the desired result.
				 */	
				ans[r] = ifft(product);
			}

			wavelength = wavelength*multFact;
		}
	}
	
	
	/*
	 * FFT to go here...
	 */
	public Complex[] fft(Complex[] array){
		return array;
	}
	/*
	 * IFFT...
	 */
	public Complex[] ifft(){
		return array;
	}
	
	
	
	
	
	
	
	
}