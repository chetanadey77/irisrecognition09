package iris.bitcodeMatcher;

public class Hamming {



	public double hammingDistance(int[] irisCode1,int irisCode2[],int[] mask1,int[] mask2, int scales)
	{
		int length = irisCode1.length;
		int[] irisCode1Shifted = new int[length];
		int[] mask1Shifted = new int[length];
		int[] maskCombined = new int[length];
		int numShifts = 8; // ? this will have to be calculated (?) based on how the 
		// integral was done. It's related to one of those filter bank parameters.
		double[] hd = new double[2*numShifts+1]; // array to hold Hamming distance values;
		
		// Loop over the number of shifts, calculating and storing the Hamming distance each time.
		for(int s=-numShifts;s<=numShifts;s++)
		{
			irisCode1Shifted = shiftBits(irisCode1,s,scales);
			mask1Shifted = shiftBits(mask1,s,scales);
			/* Actually use a combination of the shifted mask and mask2 since we should only consider 
			 * comparing corresponding bits which are actually valid according to both masks. Will assume
			 * that 1 in mask array means that this bit is not valid for matching and 0 if it is. 
			 */
			for(int i=0; i<length; i++)
			{
				//if((mask1Shifted[i] && mask2[i]) maskCombined[i] = 1;
				//else maskCombined[i] = 0;
			}
			// The total number of invalid bits.
			int numMaskBits=0;
			for(int i=0;i<length;i++) if(maskCombined[i] == 1) numMaskBits +=1;
			// The total number of valid bits. 		
			int totalBits = length - numMaskBits;
			// Perform exclusive or operation on the two codes.
			int[] xor = new int[length]; 
			for(int i=0;i<length;i++) xor[i] = irisCode1Shifted[i] ^ irisCode2[i];
			// Now include in Hamming distance sum, only those bits which are valid.
			int sum = 0;
			for(int i=0; i<length; i++) if(xor[i] == 1 && maskCombined[i] != 0) sum += 1;				
			sum = sum/totalBits; // Divide by total number of valid bits.
			//hd[numShifts+i] = sum;
		}
		return 1;
		//return 	min(hd);
	}


	
	// Get the minimum Hamming distance.
	public double min(int[] t) 
	{
		int minimum = t[0];
	    for (int i=1; i<t.length; i++) if (t[i] < minimum) minimum = t[i];      
	    return minimum;
	}



	// shift function/method.
	public int[] shiftBits(int[] irisCode,int shift,int scale)
	{
		int length = irisCode.length;
		int[] shifted = new int[length];

		if(shift==0) return irisCode;
		else
		{
			int s = 2*scale*Math.abs(shift); // Re-check this is correct.
			int p = length-s;
			int i;

			if(shift<0) // Shift to the left.
			{
				// Need to check this is again.
				for(i=0;i<p;i++) shifted[i] = irisCode[s+i];
			    for(i=p;i<length;i++) shifted[i] = irisCode[i-p];
			}

			else
			{
				for(i=s;i<length;i++) shifted[i] = irisCode[i-s];
				for(i=0;i<s;i++) shifted[i] = irisCode[i+p];		
			}
		}
		return shifted;
	}	

	

	

	

}