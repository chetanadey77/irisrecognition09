package unittest.ImageToBitcode;

import static org.junit.Assert.*;
import iris.imageToBitcode.GaborParameters;

import org.junit.Test;

public class TestGaborParameters {

	@Test
	public void testSet() {
		GaborParameters g = new GaborParameters(0, 5.0, 5);
		assertEquals(0, g.get_StepN(0));
		assertEquals(3, g.get_StepN(3));
		assertEquals(5, g.get_StepN(4));
		
		g.set(10,20,100);
		assertEquals(10, g.get_StepN(0));
		assertEquals(10.5, g.get_StepN(4));
		assertEquals(20, g.get_StepN(99));
		
		g.set(50,50,1);
		assertEquals(50, g.get_StepN(0));
		
		g.set(10,1,10);
		assertEquals(10, g.get_StepN(0));
		assertEquals(9, g.get_StepN(1));
		assertEquals(1, g.get_StepN(9));
	}

	@Test
	public void testGet_NumVals() {
		GaborParameters g = new GaborParameters();
		g.set( 0, 5.0, 5);
		assertEquals(5,g.numVals);
		
		g.set( 0, 5.0, 1);
		assertEquals(1,g.numVals);
	}

}
