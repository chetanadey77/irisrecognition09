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

public class GridSearch {
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
	static int findDistanceCount;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int id=0;
		try {
			id  = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Argument not integer");
			System.exit(1);
		}
		//System.out.println(id);
		
		findDistanceCount=0;
		
		// TODO Auto-generated method stub
		//System.out.println("Loading Images");
		String directory = "/homes/en108/workspace/IrisRecognition/images/automatic/";
		eyes =new BufferedImage[300];
		names = new String[300];
		count = load_images(eyes,names,directory);
		match = new boolean[count][count];
		for(int i=0;i<count;i++)
			for(int j=0;j<count;j++)
				if (names[i].substring(0,3).compareTo(names[j].substring(0,3))==0) match[i][j] =true;
				else match[i][j]=false;
		eyed = find_pupils(eyes,count);
		double hamSol;
		double max_hammSol = -2.0;
		FourDparamSpace maxSol =null;
		double lambda=0.0;
		int bits_sol=0;
		for (bits = 1;bits <=2;bits++)
		{
			for(int boxmin=3;boxmin<25;boxmin++)
			{
				for(int boxmax=boxmin+2;boxmax<45;boxmax++)
				{
					lambda = (double)id /100.0 +0.8;
					{
						for(double scale = 0.9;scale<1.1;scale+=0.1)
						{
							FourDparamSpace sol = new FourDparamSpace(boxmin,boxmax,lambda,scale * boxmax / (double) boxmin);
							hamSol = findDistance(sol, bits);
							if (hamSol>max_hammSol)
							{
								max_hammSol=hamSol;
								maxSol = sol.copy();
								bits_sol=bits;
							}
						}
					}
				}
			}
		}
		
		
	System.out.println("Bits "+bits_sol+" "+maxSol.toString() + "  calculations "+findDistanceCount+ "   result  "+max_hammSol);
		
	}
	public static FourDparamSpace randomStart()
	{
		FourDparamSpace sol=new FourDparamSpace();
		Random generator = new Random();
		sol._minBox = generator.nextInt(38)+2;
		sol._maxBox = generator.nextInt(45-sol._minBox)+2+sol._minBox;
		sol._lambda = generator.nextDouble() * 4.0 +1.0;
		sol._scale =  generator.nextDouble() * 4.0 +0.750;
		return sol;
		
	}
	
	public static FourDparamSpace nextPoint(FourDparamSpace sol, FourDparamSpace width)
	{
		Random generator = new Random();
		FourDparamSpace c = new FourDparamSpace();
		FourDparamSpace delta = new FourDparamSpace();
		while (!FourDparamSpace.isValid(c) ||
				( delta._minBox==0 && delta._maxBox==0 && delta._lambda==0.0 && delta._scale==0))
		{
			delta._minBox = generator.nextInt(width._minBox*2+1) - width._minBox;
			delta._maxBox = generator.nextInt(width._maxBox*2+1) - width._maxBox;
			delta._lambda = generator.nextDouble()*width._lambda*2 - width._lambda;
			delta._scale  = generator.nextDouble()*width._scale*2 - width._scale;
			c = FourDparamSpace.add(sol,delta);
		}
		return delta;
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
		//System.out.println(directory);
		File folder = new File(directory);
		//System.out.println(folder);
		File[] listOfFiles = folder.listFiles();
		int count=0;
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].isFile())
			{
				names[count] = listOfFiles[i].getName();
				//System.out.println(names[count]);
				bi[count] = isl.loadImageAbPath(directory,names[count]);
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
		if (!FourDparamSpace.isValid(p)) return -2.0;
		findDistanceCount++;
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
