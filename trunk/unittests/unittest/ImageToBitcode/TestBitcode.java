package unittest.ImageToBitcode;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import java.awt.image.BufferedImage;

public class TestBitcode {

	public static void main(String[] args) {
		long startTime=System.currentTimeMillis(); //calculate runtime
		ImageSaverLoader isl = new ImageSaverLoader(); 
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BitcodeGenerator b = new BitcodeGenerator();
		
		BitCode bitcode = b.getBitcode(eyeball,182,134,37,182,134,50);
		for (int i=0; i < bitcode.getBitcodeSize()/32; i++)
		{
			System.out.println(i + ".: " + bitcode.get(i*32, (i+1)*32-1).toString() );
		}
		System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
	}
}
