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
}
