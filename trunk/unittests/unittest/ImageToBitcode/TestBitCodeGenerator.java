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
		BufferedImage eyeball1b = isl.loadImage("001_1_1.gif");
		BufferedImage eyeball2a = isl.loadImage("002_1_1.gif");
		BufferedImage eyeball2b = isl.loadImage("/images/automatic/","002_2_1.gif");
		BitcodeGenerator bg = new BitcodeGenerator();		

		BitCode b_1a = bg.getBitcode(eyeball1a,182,135,38,182,135,98);
		BitCode b_1b = bg.getBitcode(eyeball1a,183,136,38,183,136,98);
		//BitCode b_1b = bg.getBitcode(eyeball1b,184,122,40,184,122,98);
		BitCode b_2a = bg.getBitcode(eyeball2a,195,132,50,195,132,110);
		BitCode b_2b = bg.getBitcode(eyeball2b,195,134,51,195,134,109);
		
		if(false){
			for (int i=0; i < b_1a.getBitcodeSize()/32; i++)
			{
				System.out.println(i + ".: " + b_1a.get(i*32, (i+1)*32-1).toString() );
				System.out.println(i + ".: " + b_1b.get(i*32, (i+1)*32-1).toString() );
			}
		}
		System.out.println("Runtime: " + (float)(System.currentTimeMillis() - startTime)/1000 + " seconds");
		System.out.println("1a vs 1b (similar): " + BitCode.hammingDistance(b_1a, b_1b));
		System.out.println("2a vs 2b (similar): " + BitCode.hammingDistance(b_2a, b_2b));
		System.out.println("1a vs 2a (not similar): " + BitCode.hammingDistance(b_1a,b_2a));
		System.out.println("1b vs 2b (not similar): " + BitCode.hammingDistance(b_1b,b_2b));
		System.out.println("1a vs 2b (not similar): " + BitCode.hammingDistance(b_1a,b_2b));
		System.out.println("1b vs 2a (not similar): " + BitCode.hammingDistance(b_1b,b_2a));
		
		System.out.println(b_1a.toString());
		System.out.println(b_2a.toString());
		System.out.println(b_1b.toString());
		System.out.println(b_2b.toString());
		
		isl.saveImage( UnWrapper.unWrap(eyeball1a,182,135,38,182,135,98,100,360),"uw1.jpg");
		isl.saveImage( UnWrapper.unWrap(eyeball1b,184,122,40,184,122,98,100,360),"uw2.jpg");
		isl.saveImage( UnWrapper.unWrap(eyeball2a,195,132,50,195,132,110,100,360),"uw3.jpg");
		isl.saveImage( UnWrapper.unWrap(eyeball2b,195,134,51,195,134,109,100,360),"uw4.jpg");
	}
}
