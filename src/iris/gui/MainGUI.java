package iris.gui;
import iris.gui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import iris.*;
//import iris.gui.findcentre;

public class MainGUI {

    //public static JTextField textfield = new JTextField("/home/user1/Pictures/Iris/017/1/017_1_1.bmp");
    public static JFrame frame = new JFrame("Iris Recognition");
    public static Container content = frame.getContentPane();
   // public static Icon icon = new ImageIcon("017_1_1.gif");
    public static JLabel image = new JLabel("Image will go here");
    //public static JFileChooser filedialog = new JFileChooser();
    public static JButton getimageone = new JButton("Click to get image");
    public static JButton editimage = new JButton("Edit image");
    public static Icon icon;
    public static void main(String[] args) {
         content.setLayout(new FlowLayout());
         frame.setSize(500,500);
         //System.out.println(icon.getIconWidth());
         //frame.getContentPane().add(textfield);
         //frame.getContentPane().add(new JButton("blah"));

         //textfield.setEnabled(true);
         editimage.setEnabled(false);
         //image.setVisible(true);
        image.setPreferredSize(new Dimension(320,280));
         //image.setBounds(0,0,320,280);
         
                 

        
         //image = new JLabel(icon);
         //image.setEnabled(true);
         //image.setVisible(true);
         frame.getContentPane().add(getimageone);
         frame.getContentPane().add(image);
         frame.getContentPane().add(editimage);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                     icon =  gtImage();
                     
                     image.setIcon(icon);
                     image.setEnabled(true);
                     image.setVisible(true);
                     System.out.println(image.getWidth());
                    // image.setSize(240,240);
                     //frame.getContentPane().add(image);
                     editimage.setEnabled(true);
                    // 
                     //frame.setVisible(false);
                     //frame.getContentPane().add(new JButton("NEW"));
                    // frame.setVisible(true);
                     //frame.repaint();
             }
         });
         editimage.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae){
                     System.out.println("Edit Button is pressed");
                     //CircleType c = findcentre((Image) icon);
                     //System.out.println(c.x + c.y + c.radius);
             }
         });
    }   
    public static ImageIcon gtImage() {
    	JFileChooser filedialog = new JFileChooser();
    	filedialog.showOpenDialog(content);
    	System.out.println(filedialog.getSelectedFile().getPath());
        ImageIcon icon = new ImageIcon(filedialog.getSelectedFile().getPath());
        
        return icon;
    }      
  
}

