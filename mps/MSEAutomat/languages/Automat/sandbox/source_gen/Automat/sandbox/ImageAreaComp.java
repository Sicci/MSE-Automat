package Automat.sandbox;

/*Generated by MPS */

import java.awt.Image;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

public class ImageAreaComp extends StylePanel {
  private static final long serialVersionUID = 1L;
  private Image imgAutomatLogo;
  private Color cBorderColor;

  public ImageAreaComp(String sImagePath, Color cBorderColor) {
    try {
      this.imgAutomatLogo = ImageIO.read(new File(sImagePath));
      this.cBorderColor = cBorderColor;
    } catch (Exception e) {
      System.out.println("Error: Missing Image");
      System.exit(0);
    }
  }

  public void paint(Graphics g) {
    g.setColor(this.cBorderColor);
    g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    g.drawImage(this.imgAutomatLogo, 1, 1, getWidth() - 2, getHeight() - 2, null);
  }


}
