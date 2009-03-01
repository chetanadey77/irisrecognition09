package unittest.ImageToBitcode;

import static org.junit.Assert.*;
import iris.imageToBitcode.GaborParameters;

import org.junit.Test;

public class TestGaborParameters {

	@Test
	public void testSet_w() {
		GaborParameters g = new GaborParameters();
		g.set_w(0,5.0,5);
		assertEquals(0, g.get_wStepN(0));
		assertEquals(3, g.get_wStepN(3));
		assertEquals(5, g.get_wStepN(4));
		
		g.set_w(10,20,100);
		assertEquals(10, g.get_wStepN(0));
		assertEquals(10.5, g.get_wStepN(4));
		assertEquals(20, g.get_wStepN(99));
		
		g.set_w(50,50,1);
		assertEquals(50, g.get_wStepN(0));
	}

	@Test
	public void testGet_wNumSteps() {
		GaborParameters g = new GaborParameters();
		g.set_w( 0, 5.0, 5);
		assertEquals(5,g.get_wNumVals());
		
		g.set_w( 0, 5.0, 1);
		assertEquals(1,g.get_wNumVals());
	}

}
