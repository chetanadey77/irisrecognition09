package iris.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class PanelAbout extends javax.swing.JPanel {

	DrawLogo logo;
	
	public PanelAbout(int FRAME_WIDTH, int FRAME_HEIGHT) {

		logo = new DrawLogo();
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//aboutPanel.add(logo);
		this.setBackground(Color.WHITE);
		//add(logo);
	}
	
}