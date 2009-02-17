package unittest.ImageToBitcode;

import java.awt.image.BufferedImage;

import iris.imageToBitcode.UnWrapper;

import org.junit.Before;
import org.junit.Test;

public class TestUnWrapper {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUnWrap() {
		int xi,yi,ri,xp,yp,rp;
		xi = 182; yi = 134; ri = 37;
		xp = 182; yp = 134; rp = 100;
		
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage img = isl.loadImage("002_1_1.gif");
		
		BufferedImage retImg = UnWrapper.unWrap(img,xp,yp,rp,xi,yi,ri,100,360);
		isl.saveImage(retImg, "unwrapped2.jpg");
	
	}
	
	@Test
	public void testUnWrapWithLines() {
		int xi,yi,ri,xp,yp,rp;
		xi = 182; yi = 134; ri = 37;
		xp = 182; yp = 134; rp = 100;
		
		UnWrapper u = new UnWrapper();
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage img = isl.loadImage("002_1_1.gif");
		
		BufferedImage retImg = u.unWrapWithGuides(img,xp,yp,rp,xi,yi,ri,100,360);
		isl.saveImage(retImg, "unwrapWithGuides.jpg");
		
		retImg = u.originalWithGuides(img, xp, yp, rp, xi, yi, ri);
		isl.saveImage(retImg, "unwrapOriginalWithGuides.jpg");
	
	}
	


}
