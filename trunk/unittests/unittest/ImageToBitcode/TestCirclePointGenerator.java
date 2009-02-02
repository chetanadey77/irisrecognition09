package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.util.Vector;

import javax.imageio.ImageIO;

import iris.imageToBitcode.CirclePointGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestCirclePointGenerator {

	CirclePointGenerator cg;
	BufferedImage testImage;
	String testDir;
	ImageSaverLoader isl;
	
	@Before
	public void setUp() throws Exception {
		cg = new CirclePointGenerator(50,50,20);
		testImage = isl.loadImage("eye.bmp");
	}

	@After
	public void tearDown() throws Exception {
		isl.saveImage(testImage, "circle.jpg");
	}

	@Test
	public void testCirclePointGenerator() {

	}

	@Test
	public void testGetCirclePoints() {
		Vector<int[]> points = cg.getCirclePoints();
		for (int i = 0; i < points.size(); i++)
		{
			int[] point = points.get(i);
			int x = point[0];
			int y = point[1];
			if (x >= 0 && y >= 0)
			{ 
				testImage.setRGB(x, y, 0xFFFFFFFF ); 
			}
		}
		assertEquals(20, points.size());	
	}


}
