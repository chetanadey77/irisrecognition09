package unittest.ImageToBitcode;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.UnWrapper;

import java.awt.image.BufferedImage;

public class TestBitCodeGenerator {

	public static void main(String[] args) {
		long startTime=System.currentTimeMillis(); //calculate runtime
		ImageSaverLoader isl = new ImageSaverLoader(); 
		BufferedImage eyeball1a = isl.loadImage("001_1_1.gif");
		BufferedImage eyeball1b = isl.loadImage("001_2_1.gif");
		BufferedImage eyeball2a = isl.loadImage("002_1_1.gif");
		BufferedImage eyeball2b = isl.loadImage("002_2_1.gif");
		BitcodeGenerator bg = new BitcodeGenerator();		

		BitCode b1 = bg.getBitcode(eyeball1a,182,135,38,182,135,98);
		BitCode b2 = bg.getBitcode(eyeball1b,184,122,40,184,122,98);
		BitCode b3 = bg.getBitcode(eyeball2a,195,132,50,195,132,110);
		BitCode b4 = bg.getBitcode(eyeball2b,195,134,51,195,134,109);
		//isl.saveImage(eyeball1, "eyeball1.jpg");
		
		if(false){
			for (int i=0; i < b1.getBitcodeSize()/32; i++)
			{
				System.out.println(i + ".: " + b1.get(i*32, (i+1)*32-1).toString() );
				System.out.println(i + ".: " + b2.get(i*32, (i+1)*32-1).toString() );
			}
		}
		System.out.println("Runtime: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
		System.out.println("1a vs 1b (similar): " + BitCode.hammingDistance(b1, b2));
		System.out.println("2a vs 2b (similar): " + BitCode.hammingDistance(b3, b4));
		System.out.println("1a vs 2a (not similar): " + BitCode.hammingDistance(b1,b3));
		System.out.println("1b vs 2b (not similar): " + BitCode.hammingDistance(b2,b4));
		System.out.println("1a vs 2b (not similar): " + BitCode.hammingDistance(b1,b4));
		System.out.println("1b vs 2a (not similar): " + BitCode.hammingDistance(b2,b3));
		
		isl.saveImage( UnWrapper.unWrap(eyeball1a,182,135,38,182,135,98,180,360),"uw1.jpg");
		isl.saveImage( UnWrapper.unWrap(eyeball1b,184,122,40,184,122,98,180,360),"uw2.jpg");
		isl.saveImage( UnWrapper.unWrap(eyeball2a,195,132,50,195,132,110,180,360),"uw3.jpg");
		isl.saveImage( UnWrapper.unWrap(eyeball2b,195,134,51,195,134,109,180,360),"uw4.jpg");
	}
}
