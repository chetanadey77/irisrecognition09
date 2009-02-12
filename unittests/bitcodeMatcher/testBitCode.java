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
	
	@Test
	public void testHammingDistance()
	{
		BitCode b1 = new BitCode(2048);
		BitCode b2 = new BitCode(2048);
		
		assertEquals(0,b1.hammingDistance(b2));
		
		b1.addBit(1); b1.addBit(0); 
		b2.addBit(1); b2.addBit(0);
		
		assertEquals(0, b1.hammingDistance(b2));
		
		b1.addBit(1); b1.addBit(0);
		b2.addBit(0); b2.addBit(1);
		
		assertEquals(2, b2.hammingDistance(b1));
	}

}
