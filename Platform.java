import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;

//class used to represent/create platforms

class Platform {
  Rectangle rec;
  BufferedImage image;
  int x, y, width, height;
  
  public Platform(int x, int y, int width, int height) { //constructor
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    
    rec = new Rectangle(x,y, width,height);  //intializes all coordinate and dimension variables and creates a rectangle for them
    try { //loads image
      image = ImageIO.read(new File("platform.png"));
    } catch (Exception e) {}
    
  }
  
  public BufferedImage getImage() {
    return this.image;
  }
  
  public Rectangle getRec() {
    return this.rec;
  }
  public void setRec(Rectangle newRec) {
    this.rec = newRec;
  }
  
  public void updateCoord(int newY) { //used to update the y coordinate for moving platforms
    this.y = newY;
    rec.setBounds(this.x, this.y, this.width, this.height);
  }
  
  public int getY() {
    return this.y;
  }
}
    
    