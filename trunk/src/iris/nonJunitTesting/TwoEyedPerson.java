package iris.nonJunitTesting;

import iris.imageToBitcode.BitCode;

public class TwoEyedPerson {
	int _name;
	BitCode _left;
	BitCode _right;
	
	public TwoEyedPerson(int name, BitCode left, BitCode right)
	{
		_name = name;
		_left = left;
		_right = right;
	}
	
	public boolean samePerson(TwoEyedPerson tep)
	{
		if (tep._name==_name) return true;
		return false;
	}
	
	public static boolean samePerson(TwoEyedPerson tep1,TwoEyedPerson tep2)
	{
		if (tep1._name==tep2._name) return true;
		return false;
	}
	public static double doubleHamming(TwoEyedPerson tep1,TwoEyedPerson tep2)
	{
		double hd_left = BitCode.hammingDistance(tep1._left,tep2._left);
		double hd_right = BitCode.hammingDistance(tep1._right,tep2._right);
		return (hd_left +hd_right)/2.0;
	}
}
