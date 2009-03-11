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
	static int findDistanceCount;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		findDistanceCount=0;
		
		// TODO Auto-generated method stub
		System.out.println("Loading Images");
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
		Random generator = new Random();
		bits = generator.nextInt(2)+1;
		FourDparamSpace[] orthog = new FourDparamSpace[4];
		orthog[0] = new FourDparamSpace(1,0,0.0,0.0);
		orthog[1] = new FourDparamSpace(0,1,0.0,0.0);
		orthog[2] = new FourDparamSpace(0,0,0.000001,0.0);
		orthog[3] = new FourDparamSpace(0,0,0.0,0.000001);
			
		FourDparamSpace sol = randomStart();//new FourDparamSpace(9,30,1.75,2.9);
		FourDparamSpace width = new FourDparamSpace(4,4,0.4,0.4);
		double hamSol = findDistance(sol, bits);
		System.out.println("Starting point :-");
		System.out.println(sol.toString() + "   result     "+hamSol);
		findDistanceCount=0;
		for (int mu = 0;mu<5;mu++)//number of times to reduce width
		{
			for (int repeat=0;repeat<19;repeat++)//number of times to try again on a failed improvement
			{
				boolean improved_solution = true;
				while (improved_solution)
				{
					improved_solution=false;
					FourDparamSpace delta = nextPoint(sol,width);
					FourDparamSpace newsol = FourDparamSpace.add(sol,delta);
					double hamNewSol = findDistance(newsol,bits);
					FourDparamSpace init_sol = sol.copy();
					if (hamNewSol>hamSol)//if new point is better try to extrapolate it
					{
						improved_solution = true;
						while ( hamNewSol>hamSol)
						{
							hamSol = hamNewSol;
							sol = newsol.copy();
							delta = FourDparamSpace.multiply(delta, 2);
							newsol    = FourDparamSpace.add(init_sol,delta);
							hamNewSol = findDistance(newsol, bits);
						}
					} else 
					{
						newsol = FourDparamSpace.subtract(sol,delta);
						hamNewSol = findDistance(newsol, bits);
						if (hamNewSol>hamSol)//reflect new point & try to extrapolate it
						{
							improved_solution = true;
							while (hamSol < hamNewSol)
							{
								hamSol = hamNewSol;
								sol = newsol.copy();
								delta = FourDparamSpace.multiply(delta, 2);
								newsol    = FourDparamSpace.subtract(init_sol,delta);
								hamNewSol = findDistance(newsol, bits);
							}
						}else
						{					//try and interpolate
							for (int i =4;i>0;i--)
							{
							//	delta = FourDparamSpace.divide(delta, 2);
								newsol = FourDparamSpace.subtract(sol,FourDparamSpace.divide(delta, (int) Math.pow((double)i,2.0)));
								hamNewSol = findDistance(newsol, bits);
								if (hamNewSol>hamSol) 
									{
									improved_solution = true;
									hamSol = hamNewSol;
									sol=newsol.copy();
								}
								else break;
							}
							if (!improved_solution)
							{
								for (int i =0;i<4;i++)
								{
									newsol = FourDparamSpace.add(init_sol,orthog[i]);
									hamNewSol = findDistance(newsol, bits);
									if (hamNewSol>hamSol) 
									{
										improved_solution = true;
										hamSol = hamNewSol;
										sol=newsol.copy();
									}
									newsol = FourDparamSpace.subtract(init_sol,orthog[i]);
									hamNewSol = findDistance(newsol, bits);
									if (hamNewSol>hamSol) 
									{
										improved_solution = true;
										hamSol = hamNewSol;
										sol=newsol.copy();
									}
								}
							}
						}
			
			
					}
					System.out.println("Bits "+bits+" "+sol.toString() + "  calculations "+findDistanceCount+ "   result  "+hamSol);
				}//end while improved
				//System.out.println(sol.toString() + "  calculations "+findDistanceCount+ "   result     "+hamSol);
			}// next repeat
			width = FourDparamSpace.divide(width,2);
		}// next mu
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



