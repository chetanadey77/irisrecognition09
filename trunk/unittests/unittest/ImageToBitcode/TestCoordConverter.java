package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import iris.imageToBitcode.CoordConverter;

import org.junit.Before;
import org.junit.Test;

public class TestCoordConverter {

	CoordConverter cc = new CoordConverter(500,500,100,600,600,300);
	ImageSaverLoader isl = new ImageSaverLoader();

	@Test
	public void testCoordConverter() {
		BufferedImage img = new BufferedImage(1000,1000,BufferedImage.TYPE_BYTE_GRAY);
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
		
		isl.saveImage(img, "coordConv.gif");
	}

}
