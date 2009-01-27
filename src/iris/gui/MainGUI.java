package iris.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {

    public static JTextField textfield = new 
JTextField("/home/user1/Pictures/Iris/017/1/017_1_1.bmp");
    public static JFrame frame = new JFrame("Iris Recognition");
    public static Container content = frame.getContentPane();
   // public static Icon icon = new ImageIcon("017_1_1.gif");
   // public static JLabel image = new JLabel(icon);
    //public static JFileChooser filedialog = new JFileChooser();
    public static JButton getimageone = new JButton("CLick to get image");

    public static void main(String[] args) {
         content.setLayout(new FlowLayout());
         frame.setSize(500,500);
         //System.out.println(icon.getIconWidth());
         //frame.getContentPane().add(textfield);
         //frame.getContentPane().add(new JButton("blah"));


         //textfield.setEnabled(true);
         //image.setEnabled(true);
         //image.setVisible(true);
         //image.setSize(240,240);
        

        
         //image = new JLabel(icon);
         //image.setEnabled(true);
         //image.setVisible(true);
         frame.getContentPane().add(getimageone);
         frame.setVisible(true);
         /*
         textfield.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent ae){
                         System.out.println(NewGUI.textfield.getText());
                         Icon icon = new 
ImageIcon(NewGUI.textfield.getText());
                         JButton button2 = new JButton(icon);
                         content.add(button2);
                 }
         });
*/
         getimageone.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae){
                     System.out.println("Button is pressed");
                     Icon icon =  gtImage();
                     JLabel image = new JLabel(icon);
                     image.setEnabled(true);
                     image.setVisible(true);
                     System.out.println(image.getWidth());
                     image.setSize(240,240);
                     frame.getContentPane().add(getimageone);
                     frame.repaint();
             }
         });
    }   
    public static ImageIcon gtImage() {
    	JFileChooser filedialog = new JFileChooser();
    	filedialog.showOpenDialog(content);
    	System.out.println(filedialog.getSelectedFile().getPath());
        ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        JLabel image = new JLabel(icon);
        image.setEnabled(true);
        image.setVisible(true);
        frame.getContentPane().add(getimageone);
        System.out.println(image.getWidth());
        return icon;
    }      
  
}

