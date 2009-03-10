package iris.nonJunitTesting;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.GaborParameters;
import iris.imageToBitcode.LocateIris;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;

import unittest.ImageToBitcode.ImageSaverLoader;

public class OneDSimplex_ish {
	static DecimalFormat _1dp = new DecimalFormat("0.0");
	static DecimalFormat _3dp = new DecimalFormat("0.000");
	static BufferedImage[] eyes;
	static EyeDataType[] eyed;
	static String[] names;
	static boolean[][] match;
	static int count;
	static int bits;
	static GaborParameters _abPar;
	static GaborParameters _wPar;
	static GaborParameters _x0Par;
	static GaborParameters _y0Par;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Loading Images");
		String directory = "images/automatic/";
		count = load_images(eyes,names,directory);
		match = new boolean[count][count];
		for(int i=0;i<count;i++)
			for(int j=0;j<count;j++)
				if (names[i].substring(0,3).compareTo(names[j].substring(0,3))==0) match[i][j] =true;
				else match[i][j]=false;
		eyed = find_pupils(eyes,count);
		bits =2;
		FourDparamSpace sol = new FourDparamSpace(9,27,5.7,1.9);
		FourDparamSpace width = new FourDparamSpace(4,4,0.4,0.4);
		int mu = 4;
		
	}
	
	public static FourDparamSpace nextPoint(FourDparamSpace sol, FourDparamSpace width)
	{
		Random generator = new Random();
		FourDparamSpace c = new FourDparamSpace();
		while (!FourDparamSpace.isValid(c))
		{
			c._minBox = sol._minBox + generator.nextInt(width._minBox*2+1) - width._minBox;
			c._maxBox = sol._maxBox + generator.nextInt(width._maxBox*2+1) - width._maxBox;
			c._lambda = sol._lambda + generator.nextDouble()*width._lambda*2 - width._lambda;
			c._scale = sol._scale + generator.nextDouble()*width._scale*2 - width._scale;
		}
		return c;
			
	}
	public static EyeDataType[] find_pupils(BufferedImage[] bi,int count)
	{
		EyeDataType[] ed = new EyeDataType[count];
		for (int i = 0; i < count; i++)
		{
			ed[i] = LocateIris.find_iris(bi[i]);
		
		}
		return ed;
	}
		
	public static int load_images(BufferedImage[] bi,String[] names,String directory)
	{
		ImageSaverLoader isl = new ImageSaverLoader(); 
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		bi =new BufferedImage[listOfFiles.length];
		names = new String[listOfFiles.length];
		int count=0;
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].isFile())
			{
				names[count] = listOfFiles[i].getName();
				bi[count] = isl.loadImage(directory,names[count]);
				count++;
			}
		}
		return count;
	}
	static public void setGaborParameters(FourDparamSpace p, int bits)
	{
		int sm_box=p._minBox,bg_box=p._maxBox;
		double lambda = p._lambda, scale = p._scale;
		_abPar= new GaborParameters((double)sm_box,(double)bg_box,3);
		_wPar = new GaborParameters(lambda/(2.0*(double)sm_box),lambda/(2.0*(double)sm_box*scale),3);
		_x0Par= new GaborParameters((double)bg_box, 360+(double)bg_box , 360 );
		_y0Par= new GaborParameters((double)sm_box, (double)bg_box, 3);
	}
	static public double findDistance(FourDparamSpace p, int bits)
	{
		double lowest_fail=1.0,weakest_match=0.0;
		double highest = 0.0,lowest=1.0;
		double hamm;
		BitCode[] bc = new BitCode[count];
		setGaborParameters(p, bits);
		BitcodeGenerator b1 = new BitcodeGenerator(_wPar, _abPar, _x0Par, _y0Par, 360,100,bits);
		for(int i =0;i<count;i++) bc[i]=b1.getFastBitcode(eyes[i],eyed[i]);
		for (int i=0;i<count;i++)
		{
			for (int j=i+1;j<count;j++)
			{			
				hamm = BitCode.hammingDistance(bc[i],bc[j]);
				if (hamm>highest) highest=hamm;
				if (hamm<lowest) lowest=hamm;
				if (match[i][j])
				{
					if (hamm>weakest_match) weakest_match = hamm;
				} else
				{
					if (hamm<lowest_fail) lowest_fail = hamm;
				}
				
			}
		}
		return lowest_fail-weakest_match;
		
	}
	
}
/*
Random generator = new Random();
BitCode[] bc = new BitCode[count];
//boolean[][] match = new boolean[count][count];
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
*/

