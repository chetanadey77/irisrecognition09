package iris.imageToBitcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Extracts and unwraps an iris from an eye image, given the iris inner and outer boundaries
 * @author Arnar B. Jonsson
 * @version 1.0
 */
public class UnWrapper {

	CoordConverter cc;

	public UnWrapper() { }
	
	/**
	 * Unwrap an iris image
	 * @param eyeImage original image of an eye
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @return the unwrapped iris as a BufferedImage
	 */
	public BufferedImage unWrap(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris, int unwrHeight, int unwrWidth)
	{
		BufferedImage retImg = new BufferedImage(unwrWidth, unwrHeight, BufferedImage.TYPE_BYTE_GRAY);
		cc = new CoordConverter(xPup,yPup,rPup,xIris,yIris,rIris);

		for(int th=0; th < unwrWidth; th++)
		{
			for(int r=0; r < unwrHeight; r++)
			{
				double fTh = 360/unwrWidth*th;
				double fR = r * 1.0 / unwrHeight;
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
	
	/**
	 * Returns an unwrapped iris as a two dimensional array of integers
	 * @param eyeImage original image of an eye
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @return the unwrapped iris
	 */
	public int[][] unWrapByteArr(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris, int unwrHeight, int unwrWidth)
	{
		BufferedImage img = this.unWrap(eyeImage, xPup, yPup, rPup, xIris, yIris, rIris, unwrHeight, unwrWidth);
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
	
	/**
	 * Returns an unwrapped iris as a BufferedImage with colored guides (for visual analysis in GUI) 
	 * @param eyeImage original image of an eye
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @return the unwrapped iris
	 */
	public BufferedImage unWrapWithGuides(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris, int unwrHeight, int unwrWidth)
	{
		BufferedImage grayImage = this.unWrap(eyeImage, xPup, yPup, rPup, xIris, yIris, rIris, unwrHeight, unwrWidth);
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

	/**
	 * Returns the original eye image with colored guides (for visual analysis in GUI) 
	 * @param eyeImage original image of an eye
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 * @return the unwrapped iris
	 */
	public BufferedImage originalWithGuides(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris)
	{
		BufferedImage retImage = this.toColor(eyeImage);
		int rad, x, y;

		Color c;
		for (float r = 0; r < 1; r += 0.1)
		{
			x = (int) ((1-r)*xPup + r*xIris);
			y = (int) ((1-r)*yPup + r*yIris);
			rad = (int) ((1-r)*rPup + r*rIris);
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
