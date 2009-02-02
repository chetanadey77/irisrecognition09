package bitcodeMatcher;

import static org.junit.Assert.*;
import iris.bitcodeMatcher.BitCode;

import org.junit.Before;
import org.junit.Test;

public class testBitCode {

	BitCode b;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAddBit() {
		b = new BitCode(2048);
		
		for (int i = 0; i < 32; i++) { b.addBit(1);	}
		for (int i = 0; i < 32/2; i++) { b.addBit(1); b.addBit(0);}
		for (int i = 0; i < 32/2; i++) { b.addBit(0); b.addBit(1);}
		for (int i = 0; i < 32/4; i++) { b.addBit(1); b.addBit(1); b.addBit(0); b.addBit(0);}
		for (int i = 0; i < 32/4; i++) { b.addBit(0); b.addBit(0); b.addBit(1); b.addBit(1);}
		
		int[] i = b.getBitCode();
		
		assertEquals(Integer.toBinaryString(i[0]),0xFFFFFFFF, i[0]);
		assertEquals(Integer.toBinaryString(i[1]),0xAAAAAAAA, i[1]);
		assertEquals(Integer.toBinaryString(i[2]),0x55555555, i[2]);
		assertEquals(Integer.toBinaryString(i[3]),0xCCCCCCCC, i[3]);
		assertEquals(Integer.toBinaryString(i[4]),0x33333333, i[4]);
	}

}
