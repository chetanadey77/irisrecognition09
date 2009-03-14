package iris.gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class DrawLogo extends JPanel {
    Image image = null;
    
    public DrawLogo() {    
    }

    public DrawLogo(Image image) {
        this.image = image;
    }
   

    public void setImage(Image image){
        this.image = image;
    }
    
    public Image getImage(Image image){
        return image;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        if (image != null) { 
            int height = this.getSize().height;
            int width = this.getSize().width;         
            //g.drawImage(image,0,0, width, height, this);
            g.drawImage(image, 0, 0, this); //original image size 
         }  //end if
    } //end paint
} //end class