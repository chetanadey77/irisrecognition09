package unittest.ImageToBitcode;

import iris.imageToBitcode.EyeDataType;
import iris.imageToBitcode.LocateIris;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Test;


public class TestLocateIrisOne {
	@Test
	public void testIrisOne() {
	EyeDataType ed = new EyeDataType();
	String load_path = "images/automatic/";
	ImageSaverLoader isl = new ImageSaverLoader();
	int x,y,r;
	BufferedImage eyeImage = isl.loadImage("/" +load_path,"001_1_2.gif");
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
    	 	
       	 	isl.saveImage(eyeImage,"001_1_2.gif");
       	 	
       	 	
		
	}
}
