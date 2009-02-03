package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import iris.imageToBitcode.CoordConverter;

import org.junit.Before;
import org.junit.Test;

public class TestCoordConverter {

	
	ImageSaverLoader isl = new ImageSaverLoader();

	@Test
	public void testCoordConverter() {
		CoordConverter cc = new CoordConverter(182,134,37,182,134,100); 
		
		BufferedImage img = isl.loadImage("eye.bmp");
		int x,y;
		for (double r = 0; r <= 1; r += 0.1)
		{
			for (double theta=0; theta < 360; theta += 0.1)
			{
				x = cc.getX(r, theta);
				y = cc.getY(r,theta);
				img.setRGB(x, y, Color.white.getRGB());
			}
		}
		
		isl.saveImage(img, "coordConv.jpg");
	}
	
	@Test
	public void test01() 
	{
		int x,y;
		CoordConverter cc = new CoordConverter(500,500,100,600,600,200);
		x = cc.getX(0.9972222222222222, 0.0);
		y = cc.getY(0.9972222222222222, 0.0);
		assertEquals(600f+199f, x);
		assertEquals(599f, y);
		x = cc.getX(1.0, 90);
		assertEquals(600d, x);
	
	}

}
