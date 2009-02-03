package unittest.ImageToBitcode;

import static org.junit.Assert.assertEquals;
import iris.imageToBitcode.BitcodeGenerator;

import java.awt.image.BufferedImage;
import java.io.Console;

public class TestBitcode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime=System.currentTimeMillis();
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BitcodeGenerator b = new BitcodeGenerator(182,134,37,182,134,100);
		
		int[] intarr = b.getBitcode(eyeball);
		
		for (int i=0; i < 2048/32; i++)
		{
			System.out.println(i + ".: " + Integer.toBinaryString(intarr[i]) + " :: " + intarr[i] );
		}
		System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
	}
	

	

}
