package unittest.ImageToBitcode;

import java.awt.image.BufferedImage;

import iris.imageToBitcode.EyeDataType;
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
		xi = 182; yi = 134; ri = 100;
		xp = 182; yp = 134; rp = 37;
		
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage img = isl.loadImage("002_1_1.gif");
		
		BufferedImage retImg = UnWrapper.unWrap(img,xp,yp,rp,xi,yi,ri,100,360);
		isl.saveImage(retImg, "unwrapped2.gif");
	
	}
	
	@Test
	public void testUnWrapWithLines() {
		int xi,yi,ri,xp,yp,rp;
		xi = 182; yi = 134; ri = 100;
		xp = 182; yp = 134; rp = 37;
		
		UnWrapper u = new UnWrapper();
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage img = isl.loadImage("002_1_1.gif");
		
		BufferedImage retImg = u.unWrapWithGuides(img,xp,yp,rp,xi,yi,ri,100,360);
		isl.saveImage(retImg, "unwrapWithGuides.gif");
		
		retImg = u.originalWithGuides(img, xp, yp, rp, xi, yi, ri);
		isl.saveImage(retImg, "unwrapOriginalWithGuides.gif");
	
	}
	@Test
	public void testUnWrapOverLoaded1() {
		int xi,yi,ri,xp,yp,rp;
		xi = 182; yi = 134; ri = 100;
		xp = 182; yp = 134; rp = 37;
		
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage img = isl.loadImage("002_1_1.gif");
		
		BufferedImage retImg = UnWrapper.unWrap(img,xp,yp,rp,xi,yi,ri,100,360,100);
		isl.saveImage(retImg, "unwrappedOL1.gif");
	
	}
	@Test
	public void testUnWrapOverLoaded2() {
		EyeDataType eye = new EyeDataType();
		eye.inner.x = 182; eye.inner.y = 134; eye.inner.radius = 37;
		eye.outer.x = 182; eye.outer.y = 134; eye.outer.radius = 100;
		
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage img = isl.loadImage("002_1_1.gif");
		

		BufferedImage retImg = UnWrapper.unWrap(img,eye,100,360);
		isl.saveImage(retImg, "unwrappedOL2.gif");
	
	}
	@Test
	public void testUnWrapOverLoaded3() {
			EyeDataType eye = new EyeDataType();
			eye.inner.x = 182; eye.inner.y = 134; eye.inner.radius = 37;
			eye.outer.x = 182; eye.outer.y = 134; eye.outer.radius = 100;
			
			ImageSaverLoader isl = new ImageSaverLoader();
			BufferedImage img = isl.loadImage("002_1_1.gif");
			

			BufferedImage retImg = UnWrapper.unWrap(img,eye,100,360,100);
			isl.saveImage(retImg, "unwrappedOL3.gif");
		
		}

}
