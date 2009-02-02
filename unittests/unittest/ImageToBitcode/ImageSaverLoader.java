package unittest.ImageToBitcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSaverLoader {
	
	String currentPath;
	public ImageSaverLoader()
	{
		currentPath = System.getProperty("user.dir") + "/unittests/testImages/";
	}
	
	public void saveImage(BufferedImage bimg, String filename)
	{
		File outputFile = new File(currentPath + filename);
		try {
			ImageIO.write(bimg, "GIF", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage loadImage(String filename)
	{
		BufferedImage bimg = null;
		try { 
			File f = new File(currentPath + filename);
			bimg = ImageIO.read(f);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
		return bimg;
	}
}
