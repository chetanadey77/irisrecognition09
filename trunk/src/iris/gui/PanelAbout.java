package iris.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

		

	public class PanelAbout extends JPanel{
		
		static public JLabel logo;

		   public PanelAbout(int FRAME_WIDTH, int FRAME_HEIGHT) {
		    	
		    	this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		    	setLayout(new GridLayout());
		    	setVisible(true);
		    	
		   }
		}
	
