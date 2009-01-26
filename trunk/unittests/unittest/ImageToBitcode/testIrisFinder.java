package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.sun.net.httpserver.Authenticator.Success;

import iris.imageToBitcode.*;

public class testIrisFinder {
	
	BufferedImage bimg;
	
	void setUp()
	{
		try { 
			File f = new File("c:\\bla.jpg");
			bimg = ImageIO.read(f);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
	}
	
	@Test
	public void testIrisFinder() {
		IrisFinder i = new IrisFinder(bimg);
		assertTrue(true);
	}

	@Test
	public void testLoadImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveImage() {
		fail("Not yet implemented");
	}
	@Test
   	public void testCirclePoints()
	{
		BufferedImage b = null;
	    IrisFinder i = new IrisFinder(b);
	    Vector<int[]> bla = i.circlePoints(0, 0, 5);   
	}
	   
   void saveImage(String filename)
   {
	   File outputFile = new File(filename);
	   try {
		   ImageIO.write(bimg, "JPG", outputFile);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }

}
