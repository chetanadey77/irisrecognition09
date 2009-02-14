package iris.bitcodeMatcher;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.BitSet;

/**
 * A class that stores a bitcode and makes it easy to add one bit at a time
 * 
 * @author Arnar B. Jonsson
 * @version 1.0
 */
public class BitCode extends BitSet {

	int bitCount;
	
	public BitCode(int numBits)
	{
		super(numBits);
		bitCount = 0;
	}
	
	public BitCode()
	{
		super();
		bitCount = 0;
	}
	
	/**
	 * Add one bit to the bitcode
	 * @param val bit value (0 or 1)
	 */
	public void addBit(int val)
	{
		this.addBit(val==1);
	}
	
	/**
	 * Add one bit to the bitcode
	 * @param val bit value (0 or 1)
	 */
	public void addBit(byte val)
	{
		this.addBit(val==1);
	}
	
	/**
	 * Add one bit to the bitcode
	 * @param val bit value (true or false)
	 */
	public void addBit(boolean val)
	{
		this.set(bitCount,val);
		bitCount++;
	}
	
	/**
	 * @return Bitcode size (number of bits) 
	 */
	public int getBitcodeSize()
	{
		return bitCount;
	}
	
	/**
	 * Return an image 
	 * @param width
	 * @param height
	 * @param number_of_bits_high
	 * @return
	 */
	public BufferedImage getBitCodeImage(int width, int height, int number_of_bits_high)
	{
		int total_bits = this.getBitcodeSize();
		BufferedImage bi= new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		Graphics g  = bi.createGraphics();
        Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.white);
		int w = width * number_of_bits_high / total_bits;
		int h = height / number_of_bits_high;
		for (int i=0;i< total_bits;i++)
			if (this.get(i)) 
				g2.fillRect(w * (i % (total_bits /  number_of_bits_high)),h* i * number_of_bits_high / total_bits , w, h);
		return bi;
	}
	
	public static double hammingDistance(BitCode ba, BitCode bb)
	{	final int rotation = 10; //number of degrees (from -rotation to +rotation)
		//rotation =0 should compare with no rotation
		//need to work out where to get the mask from
		//it is likely to be either part of the bit code for the eye
		//or we can pass another bitset object  for each of the eyes
		
		//bit set should return a double between 0.0 and 1.0
		// calculated by cardinality(a xor b and maska and maskb)/cardinality (maska and maskb)
		
		//I think that we should do the shifting in this function 
		
		//In the paper from 2004 below 0.32 showed a very high chance of non independence
		
		BitSet bsa = (BitSet) ba.clone();
		//BitSet bsma = (BitSet) maska.clone();
		double min_hamming=1.0;
		double hc;
		//doesn't seem to be a bitset shift function
		for (int i = - ba.size()*rotation/360;i<= ba.size()*rotation/360; i++)
		{
			bsa.clear();
			//bsma.clear();
			for(int j =0;j<ba.size();j++)
			{
				if (ba.get((i+ba.size()+j)%ba.size())) bsa.set(j,true);
				//need the same for mask a
			}

			bsa.xor(bb);
			System.out.println(bsa.size() +"  "+bsa.cardinality());
			hc = (double) (bsa.cardinality())/(double)(bsa.size()) ;
			if (min_hamming>hc) min_hamming=hc;
			//need to include the masks in the above
		}
		
		bsa.xor(bb);
		return min_hamming;
	}
}
