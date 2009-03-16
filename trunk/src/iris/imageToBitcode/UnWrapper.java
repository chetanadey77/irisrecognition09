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

	static CoordConverter cc;

	public UnWrapper() 
	{ }

	/**
	 * Unwrap an iris image
	 * @param eyeImage original image of an eye
	 * @param eyeData holds the size and location of the pupil and iris 
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @param overWrap is  the size (in pixels) of the duplication of the iris image
	 * @return the unwrapped iris as a BufferedImage
	 */
	public static BufferedImage unWrap(BufferedImage eyeImage, EyeDataType eyeData, int unwrHeight, int unwrWidth, int overWrap )
	{
		int xPup = eyeData.inner.x;
		int yPup = eyeData.inner.y;
		int rPup = eyeData.inner.radius;
		int xIris = eyeData.outer.x;
		int yIris = eyeData.outer.y;
		int rIris = eyeData.outer.radius;
		BufferedImage retImg = new BufferedImage(unwrWidth + overWrap, unwrHeight, BufferedImage.TYPE_BYTE_GRAY);
		cc = new CoordConverter(xPup,yPup,rPup,xIris,yIris,rIris);

		for(int th=0; th < unwrWidth; th++)
		{
			for(int r=0; r < unwrHeight; r++)
			{
				double fTh = 360.0/unwrWidth*th;
				double fR = r * 1.0 / unwrHeight;
				int rgb = 0;
				try{
					rgb = eyeImage.getRGB(cc.getX(fR, fTh), cc.getY(fR, fTh));
					retImg.setRGB(th, r, rgb);
					if (th<overWrap) retImg.setRGB(th + unwrWidth, r, rgb);
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
	 * @param eyeImage original image of an eye
	 * @param eyeData holds the size and location of the pupil and iris 
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @return the unwrapped iris as a BufferedImage
	 */
	public static BufferedImage unWrap(BufferedImage eyeImage,EyeDataType eyeData,int unwrHeight, int unwrWidth)
	{
		return unWrap(eyeImage, eyeData,unwrHeight, unwrWidth,0);
	}

	/**
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
	public static BufferedImage unWrap(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris, int unwrHeight, int unwrWidth)
	{
		return unWrap(eyeImage,new EyeDataType(xPup,yPup,rPup,xIris,yIris,rIris),unwrHeight,unwrWidth,0);
	}

	/**
	 * @param eyeImage original image of an eye
	 * @param xPup center point of pupil (x)
	 * @param yPup center point of pupil (y)
	 * @param rPup radius of pupil
	 * @param xIris center point of iris (x)
	 * @param yIris center point of iris (y)
	 * @param rIris radius of iris
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @param overWrap is  the size (in pixels) of the duplication of the iris image
	 * @return the unwrapped iris as a BufferedImage
	 */
	public static BufferedImage unWrap(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris, int unwrHeight, int unwrWidth,int overWrap)
	{
		return unWrap(eyeImage,new EyeDataType(xPup,yPup,rPup,xIris,yIris,rIris),unwrHeight,unwrWidth,overWrap);
	}

	/**
	 * Returns an unwrapped iris as a two dimensional array of integers
	 * @param eyeImage original image of an eye
	 * @param ed the location of the pupil and iris in an EyeDataType
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @param overWrap is the extra rotation past 360 degrees in pixel (like x)
	 * @return the unwrapped iris
	 */
	public int[][] unWrapByteArr(BufferedImage eyeImage, EyeDataType ed, int unwrHeight, int unwrWidth, int overWrap)
	{
		BufferedImage img = UnWrapper.unWrap(eyeImage, ed, unwrHeight, unwrWidth,overWrap);
		int[][] retvals = new int[img.getWidth()+overWrap][img.getHeight()];
		Color c;
		for (int i=0; i<(img.getWidth() ); i++)
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
	 * @param overWrap is the extra rotation past 360 degrees in pixel (like x)
	 * @return the unwrapped iris
	 */
	public int[][] unWrapByteArr(BufferedImage eyeImage, int xPup, int yPup, int rPup, int xIris, int yIris, int rIris, int unwrHeight, int unwrWidth,int overWrap)
	{
		EyeDataType ed = new EyeDataType(xPup, yPup, rPup, xIris, yIris, rIris);
		return unWrapByteArr( eyeImage,  ed,  unwrHeight,  unwrWidth,  overWrap);
	}
	
	/**
	 * Returns an unwrapped iris as a two dimensional array of integers
	 * @param eyeImage original image of an eye
	 * @param ed the location of the pupil and iris in an EyeDataType
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @return the unwrapped iris
	 */
	public int[][] unWrapByteArr(BufferedImage eyeImage, EyeDataType ed, int unwrHeight, int unwrWidth)
	{
		return unWrapByteArr( eyeImage,  ed,  unwrHeight,  unwrWidth,  0);
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
		EyeDataType ed = new EyeDataType(xPup, yPup, rPup, xIris, yIris, rIris);
		return unWrapByteArr( eyeImage,  ed,  unwrHeight,  unwrWidth,  0);
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
		BufferedImage grayImage = UnWrapper.unWrap(eyeImage, xPup, yPup, rPup, xIris, yIris, rIris, unwrHeight, unwrWidth);
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
	 * Returns an unwrapped iris as a BufferedImage with colored guides (for visual analysis in GUI)
	 * @param eyeImage original image of an eye
	 * @param eye the location of the pupil and iris in an EyeDataType
	 * @param unwrHeight the width (in pixels) of the unwrapped iris image
	 * @param unwrWidth the height (in pixels) of the unwrapped iris image
	 * @return the unwrapped iris
	 */
	public BufferedImage unWrapWithGuides(BufferedImage eyeImage, EyeDataType eye, int unwrHeight, int unwrWidth)
	{
		return unWrapWithGuides(eyeImage, eye.inner.x,eye.inner.y,eye.inner.radius,eye.outer.x,eye.outer.y,eye.outer.radius, unwrHeight, unwrWidth);
	}
	
	/**
	 * Returns the original eye image with colored guides (for visual analysis in GUI)
	 * @param eyeImage original image of an eye
	 * @param eye the location of the pupil and iris in an EyeDataType
	 * @return the original image with colored guides
	 */
	public BufferedImage originalWithGuides(BufferedImage eyeImage, EyeDataType eye)
	{
		return originalWithGuides(eyeImage, eye.inner.x, eye.inner.y, eye.inner.radius, eye.outer.x, eye.outer.y, eye.outer.radius);
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
	 * @return the original image with colored guides
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