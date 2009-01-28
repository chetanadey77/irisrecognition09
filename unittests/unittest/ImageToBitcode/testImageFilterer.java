package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import iris.imageToBitcode.ImageProcessor;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

public class testImageFilterer {
	
	imageSaverLoader isl;
	BufferedImage eyeball;
	ImageProcessor filt;
	
	@Before
	public void setUp() throws Exception 
	{
		isl = new imageSaverLoader();
		eyeball = isl.loadImage("eyeball.jpg");
		filt = new ImageProcessor();
	}
	
	@Test
	public void testImageFilterer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBlurredImage() {
		isl.saveImage(filt.getBlurredImage(eyeball,10), "blurred.jpg");
	}
	
	@Test
	public void testgetEdgeDetectionImage() {
		BufferedImage test = eyeball;
		test = filt.getBlurredImage(test, 20);
		test = filt.getEdgeDetectionImage(test);
		isl.saveImage(test, "edge.jpg");
	}
	

}
