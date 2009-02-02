package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import iris.imageToBitcode.GaussianFilter;

import org.junit.Test;

public class TestGaussianFilter {

	@Test
	public void testFilter() {
		GaussianFilter g = new GaussianFilter(20);
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BufferedImage output = g.filter(eyeball, null);
		isl.saveImage(output, "blurred.jpg");
	}
}