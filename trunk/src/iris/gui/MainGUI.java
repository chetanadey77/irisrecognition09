package iris.gui;

import iris.database.databaseWrapper.DbException;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.sun.imageio.plugins.common.ImageUtil;

public class MainGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 EventQueue.invokeLater(new Runnable()
         {
                         public void run()
                         {
                                 MainFrame frame = new MainFrame();
                                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                 frame.setVisible(true);
                         }
         });
	}

}


class MainFrame extends JFrame
{
	static final int FRAME_WIDTH = 900;
    static final int FRAME_HEIGHT = 770;

	MainFrame()
	{
		setTitle("Iris Recognition");
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		PanelCompareTwo panelCT = new PanelCompareTwo(FRAME_WIDTH,FRAME_HEIGHT);
		PanelStatistics panelStatistics = new PanelStatistics(FRAME_WIDTH,FRAME_HEIGHT);
		PanelValidate panelValidate = new PanelValidate(FRAME_WIDTH,FRAME_HEIGHT);
		PanelAdministrator panelAdminister = new PanelAdministrator(FRAME_WIDTH,FRAME_HEIGHT);
		PanelAbout panelAbout = new PanelAbout();
		JTabbedPane tabbedMainFrame = new JTabbedPane();
		getContentPane().setLayout(null);	
		getContentPane().add(tabbedMainFrame);
		tabbedMainFrame.addTab("About", panelAbout);
		tabbedMainFrame.addTab("Compare Two",null,panelCT,null);
		tabbedMainFrame.addTab("Statistics",null,panelStatistics,null);
		tabbedMainFrame.addTab("Validate Id",null,panelValidate);
		tabbedMainFrame.addTab("Database Administration", null,panelAdminister,null);
		tabbedMainFrame.setBounds(0, 0, FRAME_WIDTH,FRAME_HEIGHT);
		tabbedMainFrame.setVisible(true);
		
	
			
	}
	
}

              