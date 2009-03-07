package unittest.ImageToBitcode;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.UnWrapper;

import java.awt.image.BufferedImage;

public class TestFastGabor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ImageSaverLoader isl = new ImageSaverLoader(); 
		BufferedImage eyeball1a = isl.loadImage("001_1_1.gif");
		//BufferedImage eyeball1b = isl.loadImage("001_1_1.gif");
		//BufferedImage eyeball2a = isl.loadImage("002_1_1.gif");
		//BufferedImage eyeball2b = isl.loadImage("/images/automatic/","002_2_1.gif");
		EyeDataType ed_1a = new EyeDataType(182,135,38,182,135,98);
		//EyeDataType ed_1b = new EyeDataType(183,136,38,183,136,98);
		//EyeDataType ed_2a = new EyeDataType(195,132,50,195,132,110);
		//EyeDataType ed_2b = new EyeDataType(195,134,51,195,134,109);
		
		BitcodeGenerator bg = new BitcodeGenerator();	
		long startTime=System.currentTimeMillis(); //calculate runtime
		BitCode b_1a=null;
		BitCode b_1aa=null;
		BitCode b_1aaa=null;
		for(int i=0;i<2;i++)
			b_1a = bg.getBitcode(eyeball1a,ed_1a);
		System.out.println("Runtime (standard): " + (float)(System.currentTimeMillis() - startTime)/2000 + " seconds");
		startTime=System.currentTimeMillis(); //calculate runtime
		for(int i=0;i<250;i++)
			bg = new BitcodeGenerator();
			b_1aa = bg.getFastBitcode(eyeball1a,ed_1a);
		System.out.println("Runtime (fast): " + (float)(System.currentTimeMillis() - startTime)/250000 + " seconds");
		startTime=System.currentTimeMillis(); //calculate runtime
		for(int i=0;i<250;i++)
			b_1aaa = bg.getFastBitcode(eyeball1a,ed_1a);
		System.out.println("Runtime (no constructor fast): " + (float)(System.currentTimeMillis() - startTime)/250000 + " seconds");
		
		
		
		System.out.println("1a vs 1aa (similar): " + BitCode.hammingDistance(b_1a, b_1aa));
		System.out.println("1a vs 1aaa (similar): " + BitCode.hammingDistance(b_1a, b_1aaa));
		
		
		System.out.println(b_1a.toString());
		System.out.println(b_1aa.toString());
		System.out.println(b_1aaa.toString());
		
	}

}
