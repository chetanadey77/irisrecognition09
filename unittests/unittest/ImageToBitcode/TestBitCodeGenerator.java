package unittest.ImageToBitcode;

import static org.junit.Assert.*;

import iris.imageToBitcode.BitcodeGenerator;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

public class TestBitCodeGenerator {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBitcodeGenerator() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBitcode() {
		imageSaverLoader isl = new imageSaverLoader();
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BitcodeGenerator b = new BitcodeGenerator();
		int[] i = b.getBitcode(eyeball);
		assertEquals(0, i[0]);
		
	}
	
	void main()
	{
		imageSaverLoader isl = new imageSaverLoader();
		BufferedImage eyeball = isl.loadImage("eye.bmp");
		BitcodeGenerator b = new BitcodeGenerator();
		int[] i = b.getBitcode(eyeball);
		assertEquals(0, i[0]);
		assertEquals(0, i[1]);
		assertEquals(0, i[2]);
		assertEquals(0, i[3]);
	}

}
