package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import iris.imageToBitcode.CoordConverter;

import org.junit.Before;
import org.junit.Test;

public class TestCoordConverter {

	CoordConverter cc = new CoordConverter(182,134,37,182,134,100);
	ImageSaverLoader isl = new ImageSaverLoader();

	@Test
	public void testCoordConverter() {
		BufferedImage img = isl.loadImage("eye.bmp");
		int x,y;
		for (double r = 0; r <= 1; r += 0.1)
		{
			double bla = r;
			for (double theta=0; theta < 360; theta += 0.1)
			{
				x = cc.getX(r, theta);
				y = cc.getY(r,theta);
				img.setRGB(x, y, Color.white.getRGB());
			}
		}
		
		isl.saveImage(img, "coordConv.jpg");
	}

}
