package unittest.ImageToBitcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSaverLoader {
	
	String currentPath;
	
	public ImageSaverLoader()
	{
		currentPath = System.getProperty("user.dir");
	}
	
	public void saveImage(BufferedImage bimg, String folder, String filename)
	{
		File outputFile = new File(currentPath + folder + filename);
		try {
			ImageIO.write(bimg, "GIF", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveImageAbPath(BufferedImage bimg, String filename)
	{
		this.saveImage(bimg, filename);
	}
	public void saveImage(BufferedImage bimg, String filename)
	{
		this.saveImage(bimg,"/unittests/testImages/", filename);
	}

	public BufferedImage loadImage(String folder, String filename)
	{
		BufferedImage bimg = null;
		try { 
			File f = new File(currentPath + folder + filename);
			bimg = ImageIO.read(f);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
		return bimg;
	}
	public BufferedImage loadImageAbPath(String folder, String filename)
	{
		BufferedImage bimg = null;
		try { 
			File f = new File(folder + filename);
			bimg = ImageIO.read(f);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
		return bimg;
	}
	public BufferedImage loadImage(String filename)
	{
		return this.loadImage("/images/", filename);
	}
}
