package iris.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelStatistics extends JPanel implements ActionListener{
	static JLabel 	imageGraph = new  JLabel();

	static JButton	drawTable = new JButton("Table");
	static JButton	drawGraph = new JButton("Graph");
	

	static ImageIcon iconGraph = new ImageIcon();
	static JTextField description = new JTextField("In each case click the button and then chose a folder containing some eye images. It is not recomended to have more than 10 if you are drawing the chart.");
	PanelStatistics(int Width, int Height)
	{	
		
		add(description);
		add(drawTable);
		add(drawGraph);
	}
	public void actionPerformed(ActionEvent ev)
    {	int n=0;
    	//System.out.println( ev.getActionCommand()+"  "+ev.getClass()+" "+ev.getSource());
    	if (ev.getActionCommand()=="Table") n=0;
    	else if (ev.getActionCommand()=="Graph") n=1;
    	
    
    }
	public void displayTable()
	{
		
	}
}
