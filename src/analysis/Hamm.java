





public class Hamm {
	
	
	BitCode b1;
	BitCode m1;
	BitCode b2;
	BitCode m2;
	float[] hdArray;
	int numShifts; // Shift bit pattern this number of times.
	int shiftSize; // For each shift, move the pattern by this number of bits.
	int codeLength;
	
	
	public Hamm(BitCode _b1,BitCode _b2,BitCode _m1,BitCode _m2,int _numShifts,int _shiftSize,int _codeLength)
	{
		b1 = (BitCode) _b1.clone();
		m1 = (BitCode) _m1.clone();
		b2 = (BitCode) _b2.clone();
		m2 = (BitCode) _m2.clone();
		numShifts = _numShifts;
		shiftSize = _shiftSize;
		codeLength = _codeLength;
		hdArray = new float[numShifts+1];
	}
	
	
	public float distance()
	{
		BitCode b1s = new BitCode(); // Shifted BitCode.
		BitCode m1s = new BitCode(); // Shifted mask.
		BitCode mc = new BitCode(); // Combined mask.

		/* Loop over various shift values, calculating and storing the Hamming distance each time. The
		 * formula is hd = num (valid) bits that disagree / num (valid) bits
		 *               = SUMi(XOR(t1[i],t2[i])AND(m1[i])AND(m2[i])) / (Num bits - num invalid bits)
		 */
		int iteration=0;
		for(int s=0;s<=numShifts;s++)
		{
			b1s = shiftBits(s); 
			m1s = shiftBits(s);
			
			printBitCode(b1s,codeLength);
			System.out.println(" " + "shift= "+s);
			
			/* 
			 * Actually use a combination of the shifted mask and mask2 since we should only consider 
			 * comparing corresponding bits which are actually valid according to both masks. Will assume
			 * that 1 in mask array means that this bit is not valid for matching and 0 if it is. 
			 */
			for(int i=0; i<codeLength; i++)
			{	// If either is invalid, then invalid.
				
				if(m1s.get(i) || m2.get(i)) mc.set(i,true);
				else mc.set(i,false); // Otherwise valid.
			}
			// The total number of invalid bits.
			int numMaskBits=mc.cardinality();
			// The total number of valid bits. 		
			int totalValidBits = codeLength - numMaskBits;
			// Perform exclusive or operation on the two iriscodes.
			b1s.xor(b2); // b1s now holds the result of the xor operation. 
			/* 
			 * Now include in Hamming distance sum, only those bits which are valid.
			 * Since these bits are represented in the mask by 0s, we flip these bits 
			 * so that valid bits now become 1 and by doing a logical AND with the result 
			 * of the XOR operation we get in the result 1s which represent both bits which 
			 * are valid and bits where the iriscodes differ, which is what we want, we then
			 * take the length of this array and divide by the number of valid bits to get
			 * the Hamming distance, which should be a fracition for meaningful results.
			 */
			mc.flip(0,codeLength-1);  
			b1s.and(mc);
			try{
	//			hdArray[iteration] = b1s.cardinality()/totalValidBits;
			}catch(Exception ex){
				System.out.println(ex);
			}
			
			iteration++;
		}
		return 	min(hdArray);
	}

	public void printHDs()
	{
		try{
			for(int i=0;i<codeLength;i++) System.out.println(hdArray[i]);
		}catch(Exception ex){
			System.out.println(ex);
		}
	
	}
	
	
	public void printBitCode(BitCode b,int length){
		for(int i=0;i<length;i++){
			if(b.get(i)) System.out.print(1);
			else System.out.print(0);
		}
	}
	
	
	// Get the minimum Hamming distance.
	private float min(float[] t) 
	{
		float minimum = t[0];
	    for (int i=1; i<t.length; i++) if (t[i] < minimum) minimum = t[i];      
	    return minimum;
	}

	
	/* 
	 * The first shift*shiftSize bits are shifted. In order to avoid an array
     * out of bounds require that shift*shiftSize < array length. The input
     * gives the size of the shift and the shift is to the left. For example: 
     * choosing shift = 3 in [100|10001] gives [10001|100]. 
     */ 
	 private BitCode shiftBits(int shift){
		 if(shift!=0){
			 BitCode b1s = new BitCode();
			 int s = shiftSize*shift;
			 int i;
			 try{
				for(i=0;i<s;i++) b1s.set(i-s+codeLength,b1.get(i));
				for(i=0;i<codeLength-s;i++) b1s.set(i,b1.get(i+s));
			 }catch(Exception ex){
				System.out.println(ex);
			 } 
			 return b1s;
		 }else return b1;
	 }
}