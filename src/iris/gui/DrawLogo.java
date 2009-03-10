package iris.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawLogo extends JPanel {
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.blue);
    g.drawRect(0, 0, 400, 400);
    int thickness = 4;

    //for (int i = 0; i <= thickness; i++)
      //g.draw3DRect(200 - i, 10 - i, 80 + 2 * i, 30 + 2 * i, true);
    //for (int i = 0; i < thickness; i++)
      //g.draw3DRect(200 - i, 50 - i, 80 + 2 * i, 30 + 2 * i, false);

    //g.drawOval(10, 100, 80, 30);
  }

  
}
           