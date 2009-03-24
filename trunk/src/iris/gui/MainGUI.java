package iris.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * A class that creates an instance of MainFrame
 * and adds it to the EventQueue
 * 
 * @author en108
 *
 */
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
                        	 try{
                        		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        	 } catch(Exception e){
                        		 e.printStackTrace();  
                        	 }   MainFrame frame = new MainFrame();
                                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                 frame.setVisible(true);
                         }
         });
	}

}
/**
 * MainFrame is an extension of JFrame and it contains a 
 * JTabbedPane() each of which has a PannelObject. The content
 * for each of these is a separate Panel class.
 * 
 * @author en108
 *
 */

class MainFrame extends JFrame
{
	static final int FRAME_WIDTH = 900;
    static final int FRAME_HEIGHT = 775;

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

              