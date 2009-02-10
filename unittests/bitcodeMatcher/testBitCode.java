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
	
//	@Test
//	public void testBitCode()
//	{
//		b = new BitCode(3000);
//		assertEquals(94, b.());
//		
//		for (int bits=1;bits<10000; bits+=1)
//		{
//			b = new BitCode(bits);
//			assertEquals(Math.ceil((float)bits/32), b.getNumBits());
//		}
//		
//	}

	@Test
	public void testAddBit() {
		b = new BitCode(2048);
		
		b.addBit(true);
		b.addBit(false);
		b.addBit((byte)1);
		b.addBit((byte)0);
		b.addBit((int)1);
		b.addBit((int)0);
		
		assertEquals(true, b.get(0));
		assertEquals(false, b.get(1));
		assertEquals(true, b.get(2));
		assertEquals(false, b.get(3));
		assertEquals(true, b.get(4));
		assertEquals(false, b.get(5));
	}
	
	@Test
	public void tesGetNumBits()
	{
		b = new BitCode(2048);
		for (int i = 0; i<33; i++){ b.addBit(0);}
		assertEquals(33, b.getBitcodeSize());
	}
	
	@Test
	public void testGetBit()
	{
		b = new BitCode(2048);
		b.addBit(1); b.addBit(1); b.addBit(1); b.addBit(0);
		assertEquals(true, b.get(0));
		assertEquals(true, b.get(1));
		assertEquals(true, b.get(2));
		assertEquals(false, b.get(3));
	}

}
