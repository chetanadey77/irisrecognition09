package unittest.ImageToBitcode;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import java.awt.image.BufferedImage;

public class TestBitCodeGenerator {

	public static void main(String[] args) {
		long startTime=System.currentTimeMillis(); //calculate runtime
		ImageSaverLoader isl = new ImageSaverLoader(); 
		BufferedImage eyeball1 = isl.loadImage("eye1.gif");
		BufferedImage eyeball2 = isl.loadImage("eye1.gif");
		BitcodeGenerator bg1 = new BitcodeGenerator();
		BitcodeGenerator bg2 = new BitcodeGenerator();

		BitCode b1 = bg1.getBitcode(eyeball1,182,134,37,182,134,50);
		BitCode b2 = bg2.getBitcode(eyeball2,183,135,38,181,133,49);
		isl.saveImage(b1.getBitCodeImage(100, 100, 2), "eyeCode1.jpg");
		isl.saveImage(b2.getBitCodeImage(100, 100, 2), "eyeCode2.jpg");
		if(true){
			for (int i=0; i < b1.getBitcodeSize()/32; i++)
			{
				System.out.println(i + ".: " + b1.get(i*32, (i+1)*32-1).toString() );
				System.out.println(i + ".: " + b2.get(i*32, (i+1)*32-1).toString() );
			}
			System.out.println("Running time: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
		}
		System.out.println(b1.hammingDistance(b2));
	}
}
