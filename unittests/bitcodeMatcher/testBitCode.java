package bitcodeMatcher;

import static org.junit.Assert.*;
import iris.imageToBitcode.BitCode;

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
		BitCode b1 = new BitCode(2);
		BitCode b2 = new BitCode(2);
		
		
		b1.addBit(1); b1.addBit(0); 
		b2.addBit(1); b2.addBit(0);
		System.out.println(b1.size()+" "+b2.size());
		assertEquals(0, BitCode.hammingDistance(b1,b2));
		
		b1.addBit(1); b1.addBit(0);
		b2.addBit(0); b2.addBit(1);
		System.out.println(b1.length()+" "+b2.length());
		System.out.println(b1.getBitcodeSize()   +" "+b2.getBitcodeSize());
		assertEquals(0.5, BitCode.hammingDistance(b1,b2));
		
		b1.clear(0,3);b2.clear();
		b1.addBit(1);b1.addBit(1);b1.addBit(0);b1.addBit(0);b1.addBit(1);
		b2.addBit(1);b2.addBit(0);b2.addBit(0);b2.addBit(1);b2.addBit(1);
		System.out.println(b1.size()+" "+b2.size());
		assertEquals(0.0, BitCode.hammingDistance(b1,b2));
		
		// test with random bitcodes
		b1 = new BitCode();
		b2 = new BitCode();
		
		for(int i=0; i<10000; i++)
		{
			b1.addBit((int)Math.round(Math.random()));
			b2.addBit((int)Math.round(Math.random()));
			
		}
		
		System.out.println("b1: " + (float)b1.cardinality()/(float)b1.getBitcodeSize() );
		System.out.println("b2: " + (float)b2.cardinality()/(float)b2.getBitcodeSize() );
		System.out.println("hamming: " + BitCode.hammingDistance(b1,b2));
		
	}
}
