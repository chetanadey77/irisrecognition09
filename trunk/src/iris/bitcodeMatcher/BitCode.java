package iris.bitcodeMatcher;

/**
 * A class that stores a bitcode and makes it easy to add one bit at a time
 * 
 * @author Arnar B. Jonsson
 * @version 1.0
 */
public class BitCode {

	int currentByte, bitCount;
	int[] bitArray;
	
	public BitCode(int numBits)
	{
		bitArray = new int[(int)Math.ceil(numBits/32)];
		currentByte = bitCount = 0;
	}
	
	public void addBit(int val)
	{
		bitArray[currentByte] = bitArray[currentByte] << 1;
		bitCount += 1;
		bitArray[currentByte] += val;
		if (bitCount == 32)
		{
			currentByte += 1;
			bitCount = 0;			
		}
	}
	
	public void addBit(byte val)
	{
		this.addBit((int)val);
	}
	
	public void addBit(boolean val)
	{
		if (val) 
			this.addBit(1);
		else 
			this.addBit(0);
	}
	
	public int[] getBitCode()
	{
		return bitArray;
	}
}
