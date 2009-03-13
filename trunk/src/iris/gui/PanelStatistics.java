package iris.gui;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.GaborParameters;
import iris.imageToBitcode.LocateIris;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import unittest.ImageToBitcode.ImageSaverLoader;

public class PanelStatistics extends JPanel implements ActionListener{
	static JLabel 	imageGraph = new  JLabel();

	static JButton	drawTable = new JButton("Table");
	static JButton	drawGraph = new JButton("Graph");
	
	static JTextArea text = new JTextArea();
	static ImageIcon iconGraph = new ImageIcon();

	static JTextField description = new JTextField("In each case click the button and then chose a folder containing some eye images. It is not recomended to have more than 10 if you are drawing the chart.");
	PanelStatistics(int Width, int Height)
	{	
		
		add(description);
		add(drawTable);
		add(drawGraph);
		text.setEnabled(false);
		add(text);
		add(imageGraph);
		drawTable.addActionListener(this);
		drawGraph.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ev)
    {	
    	//System.out.println( ev.getActionCommand()+"  "+ev.getClass()+" "+ev.getSource());
    	if (ev.getActionCommand()=="Table") displayTable();
    	else if (ev.getActionCommand()=="Graph") displayGraph();
    	
    
    }
	public void displayTable()
	{
		int sign = 1;
		double sm_box=9.0,bg_box=22.0;
		double lambda = 2.2, scale = 2.05;
		double w_min=lambda/(2.0* sm_box), w_max=lambda/(2.0*bg_box);
		GaborParameters abPar= new GaborParameters(sm_box,bg_box,3);
		GaborParameters wPar = new GaborParameters(lambda/(2.0*sm_box),lambda/(2.0*sm_box*scale),3);
		GaborParameters x0Par= new GaborParameters(bg_box, 360-bg_box , (int) (360.0 - bg_box*2));
		GaborParameters y0Par= new GaborParameters(sm_box, bg_box, 3);
		
		ImageSaverLoader isl = new ImageSaverLoader(); 
		String directory = "images/automatic/smalltest/";
		String isl_load_path = "/"+ directory;
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		BufferedImage[] eye =new BufferedImage[listOfFiles.length];
		EyeDataType[] ed= new EyeDataType[listOfFiles.length];
		String[] names = new String[listOfFiles.length];
		BitCode[] bc = new BitCode[listOfFiles.length];
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
		testParam( wPar, abPar,x0Par,y0Par, names,eye, ed,true, count,sign);
		text.setEnabled(true);
	}
		/*
		DecimalFormat _1dp = new DecimalFormat("0.0");
		DecimalFormat _3dp = new DecimalFormat("0.000");
		
		double lowest_fail=1.0,weakest_match=0.0;
		double highest = 0.0,lowest=1.0;
		ImageSaverLoader isl = new ImageSaverLoader(); 
		String directory = "images/automatic/smalltest/";
		String isl_load_path = "/"+ directory;
		
		
		double hamm_base;
		
		GaborParameters wPar, abPar, x0Par, y0Par;
		//As I read the docs, "setFileFilter(FileFilter)" allows you to "filter out files from the user's view", i.e. to control what the user sees, and "setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)" allows the user to "just select directories".
		File folder = new File(directory);
		//File folder2 = new File(directory);
		double sm_box=17.0,bg_box=38.0;
		double lambda = 2.0;
		double w_min=lambda/(2.0* sm_box), w_max=lambda/(2.0*bg_box);
		abPar= new GaborParameters(sm_box,bg_box,3);
		wPar = new GaborParameters(lambda/(2.0*sm_box),lambda/(2.0*bg_box),3);
		x0Par= new GaborParameters(bg_box, 360-bg_box , (int) (360.0 - bg_box*2));
		y0Par= new GaborParameters(sm_box, bg_box, 3);
		
		
		File[] listOfFiles = folder.listFiles();
		BufferedImage[] eye =new BufferedImage[listOfFiles.length];
		EyeDataType[] ed= new EyeDataType[listOfFiles.length];
		String[] names = new String[listOfFiles.length];
		BitCode[] bc = new BitCode[listOfFiles.length];
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
		
		for(int sign = 2;sign>0;sign--)
			for (int amin=5;amin<30;amin+=4)
				for (int amax=Math.max(amin+6,14);amax<45;amax+=7)
					for( lambda=1.9;lambda<3.0;lambda+=0.3)
						for(double scale = 1.0;scale<(double)amax/(double)amin;scale+=0.35)
						{
							abPar.set(amin,amax,3);
							wPar.set(lambda/(2.0*amin),lambda/(2.0*amin*scale),3);
							x0Par.set(amax, 360+amax , 360 );
							y0Par.set(amin,amax, 3);
							System.out.print(sign+", "+amin+", "+amax+" ,"+_3dp.format(lambda)+", "+_3dp.format(scale)+",    ");
							hamm_base = testParam( wPar, abPar,x0Par,y0Par, names,eye, ed,false, count,sign);
							System.out.println(",    "+_3dp.format(hamm_base));
						}
		
		
	}*/
public double testParam(GaborParameters wPar,GaborParameters abPar,
		GaborParameters x0Par,GaborParameters y0Par, String[] names,
		BufferedImage[] eye,EyeDataType[] ed,boolean display_table,int count,int bits)
	{

	double lowest_fail=1.0,weakest_match=0.0;
	double highest = 0.0,lowest=1.0;
	double hamm;
	BitcodeGenerator b1 = new BitcodeGenerator(wPar, abPar, x0Par, y0Par, 360,100,bits);
	//b1.initialiseParams(wPar, abPar, x0Par, y0Par, 360,100,bits);
	BitCode[] bc = new BitCode[count];
	for(int i =0;i<count;i++) bc[i]=b1.getFastBitcode(eye[i],ed[i]);
	DecimalFormat _1dp = new DecimalFormat("0.0");
	DecimalFormat _3dp = new DecimalFormat("0.000");
	
	if (display_table) text.append("Names \t");
	for (int i=0;i<count;i++)
		if (display_table) text.append(names[i].substring(0,5)+"  ");
	if (display_table) text.append("\n");
	for (int i=0;i<count;i++)
	{
		if (display_table) text.append(names[i]+"\t");
		for (int j=0;j<count;j++)
		{			
			hamm = BitCode.hammingDistance(bc[i],bc[j]);
			if (names[i].compareTo(names[j])!=0)
			{
				if (hamm>highest) highest=hamm;
				if (hamm<lowest) lowest=hamm;
				if (names[i].substring(0,3).compareTo(names[j].substring(0,3))==0)
				{
					if (hamm>weakest_match) weakest_match = hamm;
				} else
				{
					if (hamm<lowest_fail) lowest_fail = hamm;
				}
				
			}
			if (display_table) text.append(_3dp.format(hamm)+"  ");
		}
		if (display_table) text.append("\n");
	}
	if (display_table) text.append("Lowest h "+_3dp.format(lowest)+" highest "+_3dp.format(highest)+"\n");
	if (display_table) text.append("Worst match "+_3dp.format(weakest_match)+" worst miss "+_3dp.format(lowest_fail)+"\n");
	if (!display_table)text.append(_3dp.format(lowest)+", "+_3dp.format(highest)+", " + _3dp.format(weakest_match)+", "+_3dp.format(lowest_fail)+"\n");
	return (lowest_fail-weakest_match);
}

public void displayGraph()
{
	DecimalFormat _1dp = new DecimalFormat("0.0");
	DecimalFormat _3dp = new DecimalFormat("0.000");
	System.out.println("Loading Images");
	int bits=1;
	double sm_box=9.0,bg_box=26.0;
	double lambda = 1.552, scale = 1.892;
	GaborParameters abPar= new GaborParameters(sm_box,bg_box,3);
	GaborParameters wPar = new GaborParameters(lambda/(2.0*sm_box),lambda/(2.0*sm_box*scale),3);
	GaborParameters x0Par= new GaborParameters(bg_box, 360-bg_box , (int) (360.0 - bg_box*2));
	GaborParameters y0Par= new GaborParameters(sm_box, bg_box, 3);
	
	ImageSaverLoader isl = new ImageSaverLoader(); 
	String directory = "images/automatic/";
	String isl_load_path = "/"+ directory;
	int HammingMatch[] = new int[100];
	int HammingNoMatch[] = new int[100];
	for(int i =0;i<100;i++)
	{
		HammingMatch[i] =0;
		HammingNoMatch[i]=0;
	}
	File folder = new File(directory);
	File[] listOfFiles = folder.listFiles();
	//BufferedImage[] eye =new BufferedImage[listOfFiles.length];
	//EyeDataType[] ed= new EyeDataType[listOfFiles.length];
	BitcodeGenerator b1 = new BitcodeGenerator(wPar, abPar, x0Par, y0Par, 360,100,bits);
	BitCode[] bc = new BitCode[listOfFiles.length];
	BufferedImage eye ;
	EyeDataType ed;
	String[] names = new String[listOfFiles.length];
	int count=0;
	for (int i = 0; i < listOfFiles.length; i++)
	{
		if (listOfFiles[i].isFile())
		{
			names[count] = listOfFiles[i].getName();
			System.out.println( count + "  "+names[count]);
			eye = isl.loadImage(isl_load_path,names[count]);
			ed = LocateIris.find_iris(eye);
			bc[count]=b1.getFastBitcode(eye,ed);
			//c1.inner.radius+=1;
			count++;
		}
	}
	double lowest_fail=1.0,weakest_match=0.0;
	double highest = 0.0,lowest=1.0;
	double hamm;

	System.out.println("Bitcodes already calculated");
	//BitcodeGenerator b1 = new BitcodeGenerator(wPar, abPar, x0Par, y0Par, 360,100,bits);
	//b1.initialiseParams(wPar, abPar, x0Par, y0Par, 360,100,bits);
	
	//for(int i =0;i<count;i++) bc[i]=b1.getFastBitcode(eye[i],ed[i]);
	System.out.println("Calculating Hamming Numbers");

	for (int i=0;i<count;i++)
	{
		for (int j=i+1;j<count;j++)
		{			
			hamm = BitCode.hammingDistance(bc[i],bc[j]);
			//if (names[i].compareTo(names[j])!=0)
			//{
				if (hamm>highest) highest=hamm;
				if (hamm<lowest) lowest=hamm;
				if (names[i].substring(0,3).compareTo(names[j].substring(0,3))==0)
				{
					if (hamm>weakest_match) weakest_match = hamm;
					HammingMatch[(int)(100.0*hamm)]++;
					System.out.println(names[i]+"  "+names[j]+"   "+_3dp.format(hamm));
				} else
				{
					if (hamm<lowest_fail) lowest_fail = hamm;
					HammingNoMatch[(int)(100.0*hamm)]++;
				}
				
			//}
			//if (display_table) text.append(_3dp.format(hamm)+"  ");
		}
	}
	System.out.println("Creating Graph");

	int imHeight = 600,imWidth=300;
	BufferedImage biGraph = new BufferedImage(imWidth,imHeight,BufferedImage.TYPE_INT_RGB);
	for (int i=0;i<100;i++)
	{
		
		drawORBox(biGraph, i * imWidth/100, Math.max(0,imHeight-1- HammingMatch[i]),(i+1) * imWidth/100 -1, imHeight-1, 0xff0000);
		drawORBox(biGraph, i * imWidth/100, Math.max(0,imHeight-1- HammingNoMatch[i]),(i+1) * imWidth/100 -1, imHeight-1, 0xff);
	}
	Graphics g  = biGraph.createGraphics();
	 	String message = "Highest Match "+ _3dp.format(weakest_match) + " Lowest non match "+_3dp.format(lowest_fail);
	 	//g.setFont(new Font("SansSerif",Font.BOLD,18));
	 	g.drawString(message,10,20);
	isl.saveImage(biGraph, "Hamming_Graph.gif");
	iconGraph.setImage(biGraph);
	imageGraph.setIcon(iconGraph);
			
	
}
private void drawORBox(BufferedImage img,int x1,int y1,int x2,int y2,int colour)
{
	for (int i=x1;i<=x2;i++)
		for (int j=y1;j<=y2;j++)
			img.setRGB(i,j,colour | img.getRGB(i, j) );
}
}
