package iris.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
    static final int FRAME_HEIGHT = 850;

	MainFrame()
	{
		setTitle("Iris Recognition");
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
		PanelCompareTwo panelCT = new PanelCompareTwo(FRAME_WIDTH,FRAME_HEIGHT);
		PanelStatistics panelStatistics = new PanelStatistics(FRAME_WIDTH,FRAME_HEIGHT);
		JTabbedPane tabbedMainFrame = new JTabbedPane();
		getContentPane().setLayout(null);	
		getContentPane().add(tabbedMainFrame);
		tabbedMainFrame.addTab("Compare Two",null,panelCT,null);
		tabbedMainFrame.addTab("Statistics",null,panelStatistics,null);
		tabbedMainFrame.setBounds(0, 0, FRAME_WIDTH,FRAME_HEIGHT);
		tabbedMainFrame.setVisible(true);
	
			
	}
		
}

              