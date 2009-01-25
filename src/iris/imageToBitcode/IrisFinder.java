package iris.imageToBitcode;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IrisFinder {
	
	BufferedImage eye;
	
	public IrisFinder()
	{	
		eye = loadImage("C:\\bla.jpg");
		saveImage("C:\\bla2.jpg");
	}
	
   BufferedImage loadImage(String filename) {  
	   BufferedImage bimg = null;  
       try { 
    	   File f = new File(filename);
    	   bimg = ImageIO.read(f);  
       } catch (Exception e) {  
    	   e.printStackTrace();  
       }  
       return bimg;  
   }  

   void saveImage(String filename)
   {
	   File outputFile = new File(filename);
	   try {
		   ImageIO.write(eye, "JPG", outputFile);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }

   }
}
