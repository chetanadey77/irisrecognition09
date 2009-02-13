package iris.bitcodeMatcher;



public class Hamming {
	
	
	/* I am not sure about some of these attributes i.e. whether in fact they should be attributes at all. It
	 * won't affect the answers but from a strict oo point of view I'm not sure if it's good practice.
	 */
	int[] irisCode1;
	int[] mask1;
	int[] irisCode2;
	int[] mask2;
	int[] hdArray;
	int numShifts; // Shift bit pattern this number of times in both a clockwise and anti-clockwise direction.
	int shiftSize; // For each shift, move the pattern by this number of bits.
	int length;
	
	
	public Hamming(int[] _irisCode1,int[] _irisCode2,int[] mask1,int[] mask2,int _numShifts,int _shiftSize)
	{
		irisCode1 = _irisCode1;
		mask1 = _mask1;
		irisCode2 = _irisCode2;
		mask2 = _mask2;
		numShifts = _numShifts;
		shiftSize = _shiftSize;
		length = _irisCode1.length;
		hdArray = new int[2*numShifts+1];
		for(int i=0;i<numShifts;i++) hdArray[i] = 0;
	}
	
	
	public void distance()
	{
		int[] irisCode1Shifted = new int[length];  // Array to store shifted bit pattern.
		int[] mask1Shifted = new int[length]; // Stores shifted mask. 
		int[] maskCombined = new int[length]; // Stores 1 if corresponding bits are invalid, 0 otherwise.
		
		/* Loop over various shift values, calculating and storing the Hamming distance each time. The
		 * formula is hd = num (valid) bits that disagree / num (valid) bits
		 *               = SUMi(XOR(t1[i],t2[i])AND(m1[i])AND(m2[i])) / (Num bits - num invalid bits)
		 */
		int iteration=0;
		for(int s=-numShifts;s<=numShifts;s++)
		{
			irisCode1Shifted = shiftBits(irisCode1,s);
			mask1Shifted = shiftBits(mask1,s);
			/* 
			 * Actually use a combination of the shifted mask and mask2 since we should only consider 
			 * comparing corresponding bits which are actually valid according to both masks. Will assume
			 * that 1 in mask array means that this bit is not valid for matching and 0 if it is. 
			 */
			for(int i=0; i<length; i++)
			{	// If either is invalid, then invalid.
				if((mask1Shifted[i]==1 || mask2[i]==1) maskCombined[i] = 1;
				else maskCombined[i] = 0; // Otherwise valid.
			}
			// The total number of invalid bits.
			int numMaskBits=0;
			for(int i=0;i<length;i++) if(maskCombined[i] == 1) numMaskBits +=1;
			// The total number of valid bits. 		
			int totalBits = length - numMaskBits;
			// Perform exclusive or operation on the two iriscodes.
			int[] xor = new int[length]; 
			for(int i=0;i<length;i++) xor[i] = irisCode1Shifted[i] ^ irisCode2[i];
			// Now include in Hamming distance sum, only those bits which are valid.
			int sum = 0;
			for(int i=0; i<length; i++) if(xor[i] == 1 && maskCombined[i] == 0) sum += 1;				
			sum = sum/totalBits; // Divide by total number of valid bits.
			hd[iteration++] = sum;
		}
		return 	min(hd);
	}


	
	// Get the minimum Hamming distance.
	public double min(int[] t) 
	{
		int minimum = t[0];
	    for (int i=1; i<t.length; i++) if (t[i] < minimum) minimum = t[i];      
	    return minimum;
	}

	
	/* The first shift*shiftSize bits are shifted. In order to avoid an array
     * out of bounds require that shift*shiftSize < array length.
     */ 
	public int[] shiftBits(int[] array,int shift,int shiftSize)
	{

		if(shift!=0)
		{
			int[] ans = new int[length];
			int absShift = Math.abs(shift);
			int p = length-absShift*shiftSize;
			int i;

			if(shift<0) // Shift to the left.
			{
				try{
					for(i=0;i<p;i++) shifted[i] = irisCode[s+i];
			    		for(i=p;i<length;i++) shifted[i] = irisCode[i-p];
				}catch (ArrayOutOfBoundsException ex){
					System.out.println(ex);
				}
			}
			else // Shift to the right.
			{
				try{
					for(i=s;i<length;i++) shifted[i] = irisCode[i-s];
					for(i=0;i<s;i++) shifted[i] = irisCode[i+p];
				}catch(ArrayOutOfBoundsException){
					System.out.Println(ex);
				}		
			}
			return ans;
		}
		return irisCode;
	}	
}