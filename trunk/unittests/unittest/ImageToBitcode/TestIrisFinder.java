package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.net.httpserver.Authenticator.Success;

import iris.imageToBitcode.*;

public class TestIrisFinder {

	BufferedImage eyeImage;
	String testDir;
	IrisFinder irf;

	@Before
	public void setUp() throws Exception
	{
		
		loadImage(testDir + "eyeball.jpg");
		irf = new IrisFinder(eyeImage);

	}

	@After
	public void tearDown()
	{
		saveImage(testDir + "eyeball2.jpg");
	}

	@Test
	public void testIrisFinder() {
		IrisFinder i = new IrisFinder(eyeImage);
		assertTrue(true);
	}

	//Utility functions
	void saveImage(String filename)
	{
		File outputFile = new File(filename);
		try {
			ImageIO.write(eyeImage, "JPG", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void loadImage(String filename)
	{
		try { 
			File f = new File(filename);
			eyeImage = ImageIO.read(f);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
	}

}
