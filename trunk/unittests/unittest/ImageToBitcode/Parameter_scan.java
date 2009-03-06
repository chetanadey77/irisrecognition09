package unittest.ImageToBitcode;
import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.GaborParameters;
import iris.imageToBitcode.LocateIris;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.Format;

public class Parameter_scan{
	public static void main(String[] args) {
		DecimalFormat _1dp = new DecimalFormat("0.0");
		DecimalFormat _3dp = new DecimalFormat("0.000");
		
		long startTime=System.currentTimeMillis(); //calculate runtime
		ImageSaverLoader isl = new ImageSaverLoader(); 
		String load_path = "/images/automatic/";
		BufferedImage eye1 = isl.loadImage(load_path,"005_2_1.gif");
		EyeDataType c1 = LocateIris.find_iris(eye1);
		//c1.inner.radius+=1;
		BufferedImage eye2 = isl.loadImage(load_path,"107_1_1.gif");
		EyeDataType c2 = LocateIris.find_iris(eye2);
		//c2.inner.radius+=1;
		BufferedImage eye3 = isl.loadImage(load_path,"107_2_1.gif");
		EyeDataType c3 = LocateIris.find_iris(eye3);
		//c3.inner.radius+=1;
		BitcodeGenerator b1 = new BitcodeGenerator();
		double h1,h2;
		GaborParameters wPar, abPar, x0Par, y0Par;
		BitCode bitcode1, bitcode2 ,bitcode3 ;
		for(int sm_box=9;sm_box<25;sm_box+=4)
			for(int bg_box=sm_box+7;bg_box<45;bg_box+=7)
				for(double lambda = 1.7;lambda<3.8;lambda+=0.35)
					//for(double realscale=1.0;realscale<1.4;realscale+=.3)
				{
					abPar= new GaborParameters(sm_box,bg_box,3);
					//wPar = new GaborParameters(lambda,realscale,3);
					wPar = new GaborParameters(lambda/(2.0*sm_box),lambda/(2.0*bg_box),3);
					x0Par= new GaborParameters(bg_box, 360-bg_box , 360- bg_box*2);
					y0Par= new GaborParameters(sm_box, bg_box, 3);
					b1.initialiseParams(wPar, abPar, x0Par, y0Par, 360,100);
					bitcode1 = b1.getBitcode(eye1,c1);
					bitcode2 = b1.getBitcode(eye2,c2);
					bitcode3 = b1.getBitcode(eye3,c3);
					System.out.print(sm_box+" "+bg_box+" "+_1dp.format(lambda)+" "+_1dp.format(1.0)+"  ");
					System.out.print(100 * bitcode1.cardinality()/(bitcode1.getBitcodeSize())+" ");
					System.out.print(100 * bitcode2.cardinality()/(bitcode2.getBitcodeSize())+" ");
					System.out.print(100 * bitcode3.cardinality()/(bitcode3.getBitcodeSize())+" ");
					h1 = BitCode.hammingDistance(bitcode1,bitcode2);
					System.out.print(_3dp.format(h1 )+" ");
					h2 = BitCode.hammingDistance(bitcode2,bitcode3);
					System.out.print(_3dp.format( h2));
					System.out.println("    "+_3dp.format( h1-h2));
					
				}
	}
}
