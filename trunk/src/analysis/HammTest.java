import junit.framework.TestCase;


public class HammTest extends TestCase {

	public void testHamm() {
		int length = 10;
		BitCode b1 = new BitCode(length);
		BitCode b2 = new BitCode(length);
		BitCode m1 = new BitCode(length);
		BitCode m2 = new BitCode(length);
		
		String b1st = "1010100100";
		String b2st = "1111111111";
		String m1st = "0000000001";
		String m2st = "0000000001";

		b1 = init(b1st,length);
		b2 = init(b2st,length);
		m1 = init(m1st,length);
		m2 = init(m2st,length);

		Hamm hd = new Hamm(b1,b2,m1,m2,9,1,length);
		hd.distance();
		//System.out.println(hd.distance());
		
	}

	
	
	private BitCode init(String s,int length){
		BitCode ans = new BitCode(length);
		for(int i=0;i<length;i++){
			if(s.charAt(i)=='1') ans.set(i,true);
			else ans.set(i,false);
		}
		return ans;
	}
	
	
	
	
	
	//public void testDistance() {
		//fail("Not yet implemented");
	//}

	//public void testPrintHDs() {
		//fail("Not yet implemented");
	//}
}
/*
	public void testObject() {
		fail("Not yet implemented");
	}

	public void testGetClass() {
		fail("Not yet implemented");
	}

	public void testHashCode() {
		fail("Not yet implemented");
	}

	public void testEquals() {
		fail("Not yet implemented");
	}

	public void testClone() {
		fail("Not yet implemented");
	}

	public void testToString() {
		fail("Not yet implemented");
	}

	public void testNotify() {
		fail("Not yet implemented");
	}

	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	public void testWaitLong() {
		fail("Not yet implemented");
	}

	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	public void testWait() {
		fail("Not yet implemented");
	}

	public void testFinalize() {
		fail("Not yet implemented");
	}

}*/
