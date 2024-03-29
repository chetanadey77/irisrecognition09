package iris.gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 * PanelAbout includes background information on the software and our logo
 * 
 * 
 * @author ss1008
 *
 */

public class PanelAbout extends JPanel {
  protected  Image ci;
  
  public PanelAbout() {
	  BufferedImage loadim = null;
		try {
			loadim = ImageIO.read(new File("Iris_Recognition_About.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		ci = loadim;
  }
  public void setImage(Image si) {
    this.ci=si;
    validate();
    repaint();

  }
  public Image getDisplayedImage() {
    return this.ci;
  }

  public void update(Graphics g) {
	    if (ci!=null) {
	      g.drawImage(ci, 0,0,this.ci.getWidth(this),this.ci.getHeight(this), this);
	    } else {
	      Color c=g.getColor();
	      g.setColor(Color.white);
	      g.fillRect(0,0,this.ci.getWidth(this), this.ci.getHeight(this));
	      g.setColor(c);
	    }
	  }
  public void paint (Graphics g) {
    update(g);
  }
}
