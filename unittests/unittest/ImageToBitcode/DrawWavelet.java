package unittest.ImageToBitcode;

import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.GaborParameters;

import java.awt.image.BufferedImage;


public class DrawWavelet {
	public static void main(String[] args) {
		ImageSaverLoader isl = new ImageSaverLoader(); 
		String load_path = "/images/automatic/";
		BufferedImage eye1 = isl.loadImage(load_path,"005_2_1.gif");
		BitcodeGenerator b1 = new BitcodeGenerator();
		double sm_box=10,bg_box=30;
		double lambda=1.5;
		
		GaborParameters abPar= new GaborParameters(sm_box,bg_box,3);
		GaborParameters wPar = new GaborParameters(lambda,2*lambda,3);
		GaborParameters x0Par= new GaborParameters(bg_box, 360-bg_box ,(int)( 360- bg_box*2));
		GaborParameters y0Par= new GaborParameters(sm_box, bg_box, 3);
		b1.initialiseParams(wPar, abPar, x0Par, y0Par, 360,100);
		int step=2;
		BufferedImage res = b1.drawWavelet(eye1, 50, (int)abPar.get_StepN(step),step);
		isl.saveImage(res, "wavelet.gif");
		
	}
}
