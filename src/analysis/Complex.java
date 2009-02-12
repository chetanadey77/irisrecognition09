
public class Complex {
	
	private double re;
	private double im;

	public Complex(){re=0.0; im=0.0;}	
	public Complex(double _re, double _im){re=_re; im=_im;}
      
	public void setRe( double _re) {re=_re;}
	public double getRe() {return re;}
	public void setIm(double _im) {im=_im;}
	public double getIm() {return im;}

	public Complex add( Complex c){return new Complex(re+c.re,im+c.im);}
	public Complex add( double c){return new Complex(re+c,im);}
	public Complex mult(Complex c){return new Complex(re*c.re-im*c.im,im*c.re+re*c.im);}
	public Complex mult(double c){return new Complex(re*c,im*c);}
	
}

