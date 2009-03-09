package unittest.ImageToBitcode;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.GaborParameters;
import iris.imageToBitcode.LocateIris;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;

public class ParameterScanRandomBig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	

	DecimalFormat _1dp = new DecimalFormat("0.0");
	DecimalFormat _3dp = new DecimalFormat("0.000");
	System.out.println("Loading Images");
	ImageSaverLoader isl = new ImageSaverLoader(); 
	String directory = "images/automatic/";
	String isl_load_path = "/"+ directory;
	File folder = new File(directory);
	File[] listOfFiles = folder.listFiles();
	BufferedImage[] eye =new BufferedImage[listOfFiles.length];
	EyeDataType[] ed= new EyeDataType[listOfFiles.length];
	String[] names = new String[listOfFiles.length];
	int count=0;
	for (int i = 0; i < listOfFiles.length; i++)
	{
		if (listOfFiles[i].isFile())
		{
			names[count] = listOfFiles[i].getName();
			eye[count] = isl.loadImage(isl_load_path,names[count]);
			ed[count] = LocateIris.find_iris(eye[count]);
			//c1.inner.radius+=1;
			count++;
		}
	}
	
	int bits=2;
	int sm_box=9,bg_box=29;
	double lambda = 1.9, scale = 2.4;
	GaborParameters abPar= new GaborParameters((double)sm_box,(double)bg_box,3);
	GaborParameters wPar = new GaborParameters(lambda/(2.0*(double)sm_box),lambda/(2.0*(double)sm_box*scale),3);
	GaborParameters x0Par= new GaborParameters((double)bg_box, 360-(double)bg_box , (int) (360.0 - (double)bg_box*2));
	GaborParameters y0Par= new GaborParameters((double)sm_box, (double)bg_box, 3);
	
	
	
	System.out.println("Loaded");
	double lowest_fail=1.0,weakest_match=0.0;
	double highest = 0.0,lowest=1.0;
	double hamm;
	Random generator = new Random();
	BitCode[] bc = new BitCode[count];
	boolean[][] match = new boolean[count][count];
	for(int i=0;i<count;i++)
		for(int j=0;j<count;j++)
			if (names[i].substring(0,3).compareTo(names[j].substring(0,3))==0) match[i][j] =true;
			else match[i][j]=false;
	while (true)
	{
		lowest_fail=1.0;weakest_match=0.0;
		highest = 0.0;lowest=1.0;
		bits=generator.nextInt(2)+1;
		sm_box=generator.nextInt(40)+2;
		bg_box=generator.nextInt(47-sm_box)+sm_box+2;
		lambda = generator.nextDouble()*5.0+1.0;
		scale = generator.nextDouble()*(2.0*(double)bg_box/(double)sm_box - 0.7)+0.7;
System.out.print(bits+", "+sm_box+", "+bg_box+", "+_3dp.format(lambda)+", "+_3dp.format(scale));
		abPar= new GaborParameters((double)sm_box,(double)bg_box,3);
		wPar = new GaborParameters(lambda/(2.0*(double)sm_box),lambda/(2.0*(double)sm_box*scale),3);
		x0Par= new GaborParameters((double)bg_box, 360-(double)bg_box , (int) (360.0 - (double)bg_box*2));
		y0Par= new GaborParameters((double)sm_box, (double)bg_box, 3);
		BitcodeGenerator b1 = new BitcodeGenerator(wPar, abPar, x0Par, y0Par, 360,100,bits);
	//b1.initialiseParams(wPar, abPar, x0Par, y0Par, 360,100,bits);
		for(int i =0;i<count;i++) bc[i]=b1.getFastBitcode(eye[i],ed[i]);
	//stem.out.println("Calculating Hamming Numbers");

	for (int i=0;i<count;i++)
	{
		for (int j=i+1;j<count;j++)
		{			
			hamm = BitCode.hammingDistance(bc[i],bc[j]);
			//if (names[i].compareTo(names[j])!=0)
			//{---
				if (hamm>highest) highest=hamm;
				if (hamm<lowest) lowest=hamm;
				if (match[i][j])
				{
					if (hamm>weakest_match) weakest_match = hamm;
		//			HammingMatch[(int)(100.0*hamm)]++;
			//		System.out.println(names[i]+"  "+names[j]+"   "+_3dp.format(hamm));
				} else
				{
					if (hamm<lowest_fail) lowest_fail = hamm;
				//	HammingNoMatch[(int)(100.0*hamm)]++;
				}
				
			//}
			//if (display_table) text.append(_3dp.format(hamm)+"  ");
		}
	}
	
	System.out.println(", "+_3dp.format(weakest_match) + ", "+_3dp.format(lowest_fail)+",   "+_3dp.format(lowest_fail-weakest_match));
		
	}
}
}
