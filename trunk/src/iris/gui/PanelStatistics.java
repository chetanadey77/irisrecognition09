package iris.gui;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;
import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.GaborParameters;
import iris.imageToBitcode.LocateIris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import unittest.ImageToBitcode.ImageSaverLoader;

public class PanelStatistics extends JPanel implements ActionListener{
	static JLabel 	imageGraph = new  JLabel();

	static JButton	drawTable = new JButton("Table");
	static JButton	drawGraph = new JButton("Graph");
	

	static ImageIcon iconGraph = new ImageIcon();
	static JTextField description = new JTextField("In each case click the button and then chose a folder containing some eye images. It is not recomended to have more than 10 if you are drawing the chart.");
	PanelStatistics(int Width, int Height)
	{	
		
		add(description);
		add(drawTable);
		add(drawGraph);
		drawTable.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ev)
    {	int n=0;
    	//System.out.println( ev.getActionCommand()+"  "+ev.getClass()+" "+ev.getSource());
    	if (ev.getActionCommand()=="Table") displayTable();
    	else if (ev.getActionCommand()=="Graph") n=1;
    	
    
    }
	public void displayTable()
	{
		DecimalFormat _1dp = new DecimalFormat("0.0");
		DecimalFormat _3dp = new DecimalFormat("0.000");
		
		double lowest_fail=1.0,weakest_match=0.0;
		double highest = 0.0,lowest=1.0;
		ImageSaverLoader isl = new ImageSaverLoader(); 
		String directory = "images/automatic/smalltest/";
		String isl_load_path = "/"+ directory;
		BufferedImage eye;
		EyeDataType ed;
		BitcodeGenerator b1 = new BitcodeGenerator();
		double hamm;
		GaborParameters wPar, abPar, x0Par, y0Par;
		//As I read the docs, "setFileFilter(FileFilter)" allows you to "filter out files from the user's view", i.e. to control what the user sees, and "setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)" allows the user to "just select directories".
		File folder = new File(directory);
		//File folder2 = new File(directory);
		double sm_box=21.0,bg_box=42.0;
		abPar= new GaborParameters(sm_box,bg_box,3);
		wPar = new GaborParameters(3.1,1.30,3);
		x0Par= new GaborParameters(bg_box, 360-bg_box , (int) (360.0 - bg_box*2));
		y0Par= new GaborParameters(sm_box, bg_box, 3);
		b1.initialiseParams(wPar, abPar, x0Par, y0Par, 360,100);
		File[] listOfFiles = folder.listFiles();
		String[] names = new String[listOfFiles.length];
		BitCode[] bc = new BitCode[listOfFiles.length];
		int count=0;
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].isFile())
			{
				names[count] = listOfFiles[i].getName();
				eye = isl.loadImage(isl_load_path,names[count]);
				ed = LocateIris.find_iris(eye);
				//c1.inner.radius+=1;
				bc[count]=b1.getBitcode(eye,ed);
				count++;
			}
		}
		System.out.print("Names \t\t");
		for (int i=0;i<count;i++)
			System.out.print(names[i].substring(0,5)+"\t");
		System.out.println();
		for (int i=0;i<count;i++)
		{
			System.out.print(names[i]+"\t");
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
				System.out.print(_3dp.format(hamm)+"\t");
			}
			System.out.println();
		}
		System.out.println("Lowest h "+_3dp.format(lowest)+" highest "+_3dp.format(highest));
		System.out.println("Worst match "+_3dp.format(weakest_match)+" worst miss "+_3dp.format(lowest_fail));
		
	}
}
