package iris.bitcodeMatcher;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

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
		bitArray = new int[(int)Math.ceil((float)numBits/32)];
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
	
	public int getSize()
	{
		return bitArray.length;
	}
	
	public boolean getBit(int bit_number)
	{ 	
		int total_bits = currentByte*32 + bitCount;
		if (bit_number>total_bits) System.out.println("Error accessing bit");
		if ((bitArray[(bit_number/32)] & (2<<(31-(bit_number % 32)))) >0) return true;
		else return false;
	}
	
	public BufferedImage getBitCodeImage(int width, int height, int number_of_bits_high)
	{
		int total_bits = currentByte*32 + bitCount;
		BufferedImage bi= new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		Graphics g  = bi.createGraphics();
        Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.white);
		int w = width * number_of_bits_high / total_bits;
		int h = height / number_of_bits_high;
		for (int i=0;i< total_bits;i++)
			if (getBit(i)) 
				g2.fillRect(w * (i % (total_bits /  number_of_bits_high)),h* i * number_of_bits_high / total_bits , w, h);
		return bi;
	}
}
