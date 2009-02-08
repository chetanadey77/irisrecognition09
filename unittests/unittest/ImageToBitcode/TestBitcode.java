package unittest.ImageToBitcode;

import iris.imageToBitcode.BitcodeGenerator;
import java.awt.image.BufferedImage;

public class TestBitcode {

	public static void main(String[] args) {
		long startTime=System.currentTimeMillis(); //calculate runtime
		ImageSaverLoader isl = new ImageSaverLoader(); 
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BitcodeGenerator b = new BitcodeGenerator();
		
		int[] intarr = b.getBitcode(eyeball,182,134,37,182,134,100).getBitCode();
		
		for (int i=0; i < intarr.length; i++)
		{
			System.out.println(i + ".: " + Integer.toBinaryString(intarr[i]) + " :: " + intarr[i] );
		}
		System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
	}
}
