package bitcodeMatcher;

import static org.junit.Assert.*;
import iris.bitcodeMatcher.BitCode;

import org.junit.Before;
import org.junit.Test;

public class testBitCode {

	BitCode b;
	
	@Before
	public void setUp() throws Exception {
		b = new BitCode(2048);
	}

	@Test
	public void testGetBitCode() {

		for (int i = 1; i < 2048/2; i++)
		{
			b.addBit(0);
			b.addBit(1);
		}
		
		int[] i = b.getBitCode();
		assertEquals(0xAAAAAAAA, i[0]);
		assertEquals(0xAAAAAAAA, i[1]);
		assertEquals(0xAAAAAAAA, i[2]);
		assertEquals(0xAAAAAAAA, i[3]);
	}

}
