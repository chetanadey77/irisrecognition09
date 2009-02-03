package iris.imageToBitcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class UnWrapper {

	CoordConverter cc;

	public UnWrapper()
	{
		
	}

	public BufferedImage unWrap(BufferedImage eyeImage, int xp, int yp, int rp, int xi, int yi, int ri, int r_pixels, int th_pixels)
	{
		BufferedImage retImg = new BufferedImage(th_pixels, r_pixels, BufferedImage.TYPE_BYTE_GRAY);
		cc = new CoordConverter(xp,yp,rp,xi,yi,ri);

		for(int th=0; th < th_pixels; th++)
		{
			for(int r=0; r < r_pixels; r++)
			{
				double fTh = 360/th_pixels*th;
				double fR = 1.0/r_pixels*r;
				int rgb = 0;
				try{
					rgb = eyeImage.getRGB(cc.getX(fR, fTh), cc.getY(fR, fTh));
					retImg.setRGB(th, r, rgb);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println(fR + " : " + fTh);
					System.out.println(cc.getX(fR, fTh) + " : " + cc.getY(fR, fTh));
				}

			}
		}
		return retImg;
	}
	
	public int[][] unWrapByteArr(BufferedImage eyeImage, int xp, int yp, int rp, int xi, int yi, int ri, int r_pixels, int th_pixels)
	{
		BufferedImage img = this.unWrap(eyeImage, xp, yp, rp, xi, yi, ri, r_pixels, th_pixels);
		int[][] retvals = new int[img.getWidth()][img.getHeight()];
		Color c;
		for (int i=0; i<img.getWidth()-1; i++)
		{
			for (int j=0; j<img.getHeight()-1; j++)
			{
				c = new Color(img.getRGB(i,j));
				retvals[i][j] = (c.getRed() + c.getGreen() + c.getBlue())/3;
			}
		}
		return retvals;
	}

	public BufferedImage unWrapWithGuides(BufferedImage eyeImage, int xp, int yp, int rp, int xi, int yi, int ri, int r_pixels, int th_pixels)
	{
		BufferedImage grayImage = this.unWrap(eyeImage, xp, yp, rp, xi, yi, ri, r_pixels, th_pixels);
		BufferedImage retImage = this.toColor(grayImage);

		Graphics g = retImage.getGraphics();
		Color c;
		for (int i = 0; i<10; i++)
		{	
			c = this.gradientColor(((float)i)/10f);
			int y = i*retImage.getHeight()/10;
			g.setColor(c);
			g.drawLine(0, y , retImage.getWidth()-1, y);
		}

		return retImage;
	}

	public BufferedImage originalWithGuides(BufferedImage eyeImage, int xp, int yp, int rp, int xi, int yi, int ri)
	{
		BufferedImage retImage = this.toColor(eyeImage);
		int rad, x, y;

		Color c;
		for (float r = 0; r < 1; r += 0.1)
		{
			x = (int) ((1-r)*xp + r*xi);
			y = (int) ((1-r)*yp + r*yi);
			rad = (int) ((1-r)*rp + r*ri);
			c = gradientColor(r);
			Graphics g = retImage.getGraphics();
			g.setColor(c);
			g.drawOval(x-rad, y-rad , 2*rad, 2*rad);			
		}
		return retImage;
	}

	private BufferedImage toColor(BufferedImage grayImg)
	{
		BufferedImage retImage = new BufferedImage(grayImg.getWidth(), grayImg.getHeight(), BufferedImage.TYPE_INT_RGB); 
		Graphics g = retImage.getGraphics();
		g.drawImage(grayImg,0,0,null);
		return retImage;
	}

	private Color gradientColor(float k)
	{
		Color c = Color.getHSBColor(0.9f * k, 1f, 1f);
		int alpha = 100;
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha); 
	}


}
