package iris.imageToBitcode;

import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * A class for applying filters to an image of an eye
 * 
 * @author Arnar B. Jónsson
 * @version 1.0
 */
public class ImageFilterer {

	public ImageFilterer()
	{
		
	}

	public BufferedImage getBlurredImage(BufferedImage inImage, int amount)
	{
		inImage = this.toGrayscale(inImage);
		BufferedImage blurredImage = null;
		
		int dim = amount * amount;
		float[] matrix = new float[dim];
		for (int i = 0; i < dim; i++)
			matrix[i] = 1.0f/(float)dim;

		BufferedImageOp op = new ConvolveOp( new Kernel(amount, amount, matrix), ConvolveOp.EDGE_NO_OP, null );

		blurredImage = op.filter(inImage,null);

		return blurredImage;

	}

	public BufferedImage getEdgeDetectionImage(BufferedImage inImage)
	{
		inImage = this.toGrayscale(inImage);
		BufferedImage edgeImage = null;

		float[] h1 = {
				1f, 1f, 1f, 
				0f, 0f, 0f, 
				-1f, -1f, -1f};
		float[] h2 = {
				0f, 1f, 1f, 
				-1f, 0f, 1f, 
				-1f, -1f, 0f};
		float[] h3 = {
				-1f, 0f, 1f, 
				-1f, 0f, 1f, 
				-1f, 0f, 1f};

		BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, h1) );
		edgeImage = op.filter(inImage, null);
		
		return edgeImage;
	}
	
	private BufferedImage toGrayscale(BufferedImage colorImage)
	{
		   BufferedImage image = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
		   Graphics g = image.getGraphics();  
		   g.drawImage(colorImage, 0, 0, null);
		   
		   return image;
		     
	}
}
