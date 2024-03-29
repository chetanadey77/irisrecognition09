package unittest.ImageToBitcode;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import unittest.*;

import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.LocateIris;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;

public class TestLocateIris {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test_pupil()
	{
		EyeDataType ed = new EyeDataType();
		BufferedImage eyeImage;
		String load_path = "images/new/";
		String file;
		int rmin=200, xmin=400, ymin=400;
		int rmax = 0, xmax=0, ymax = 0;
		int x,y,r;
		File folder = new File(load_path);
		File[] listOfFiles = folder.listFiles(); 
		ImageSaverLoader isl = new ImageSaverLoader();
		//String[] listOf = {"072_1_1.gif"};
		for (int i = 0; i < listOfFiles.length; i++) 
		//for (int i = 0; i < 3; i++) 
		{
			if (listOfFiles[i].isFile()) {
				file = listOfFiles[i].getName();
				//file = listOf[i];
				System.out.println(file);
				eyeImage = isl.loadImage("/" + load_path, file);
				//for(int n=0;i<10;i++)
				//System.out.println("here");
				
				ed=LocateIris.find_iris(eyeImage);
				//eyeImage= LocateIris.gaussian_blur(eyeImage,7);
				//eyeImage = LocateIris.edgeDetection(eyeImage);
				//System.out.println("here");
                x = ed.inner.x;
                y = ed.inner.y;
                r = ed.inner.radius;
                eyeImage = LocateIris.draw_part_circle(eyeImage,x,y,r,(char)126, 0xFFFFFF);
           	 	Graphics g  = eyeImage.createGraphics();
           	 	String message = x + " , " + y + "  Radius " + r;
           	 	//g.setFont(new Font("SansSerif",Font.BOLD,18));
           	 	g.drawString(message,10,20);
           	 	x = ed.outer.x;
           	 	y = ed.outer.y;
           	 	r = ed.outer.radius;
           	 	eyeImage = LocateIris.draw_part_circle(eyeImage,x,y,r,(char)102, 0xFFFFFF);
        	 	g  = eyeImage.createGraphics();
        	 	message = x + " , " + y + "  Radius " + r;
        	 	//g.setFont(new Font("SansSerif",Font.BOLD,18));
        	 	g.drawString(message,10,40);
        	 	
           	 	isl.saveImage(eyeImage,"/unittests/testImages/new_pupil_centres/",file);
           	 	
           	 	if ( x>xmax) xmax=x;
           	 	if ( r>rmax) rmax=r;
           	 	if ( y>ymax) ymax=y;
           	 	if (x<xmin) xmin = x;
           	 	if (y<ymin) ymin =y;
           	 	if (r<rmin) rmin =r;
           	 	System.out.println(xmin + " " + xmax + "    " + ymin +" "+ymax+"     "+rmin + " "+rmax);
           	 	
			}
		}
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
