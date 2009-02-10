package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import iris.bitcodeMatcher.BitCode;
import iris.imageToBitcode.BitcodeGenerator;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

public class TestBitCodeGenerator {

	@Test
	public void testGetBitcode() {
		ImageSaverLoader isl = new ImageSaverLoader();
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BitcodeGenerator b = new BitcodeGenerator();
		BitCode i = b.getBitcode(eyeball,182,134,37,182,134,100);
		

	}

}
