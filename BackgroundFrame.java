/**
* This template can be used as reference or a starting point
* for your final summative project
* @author Mangat
**/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Rectangle;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class BackgroundFrame { 

  //class variable (non-static)
   static double x, y;
   private BufferedImage image;
   int bgX = 0;
   int bgY = 0;
   int movingPlatform1Velocity = -1;
   int movingPlatform2Velocity = 1;
   int movingUpperBound = 1150;
   int movingLowerBound = 200;
   public int tempX, tempY;
   int px;
   int py;
   
   Rectangle [] recs = new Rectangle[24];
   Platform [] platforms = new Platform[24]; //array to store all the platforms
   int [][] platformData = {{0,100,125,100}, {2435,100,125,100}, {0, 1375, 125, 100}, {2435,1375,125,100},  //spawn platforms  (4)
     {300,100,125,100}, {2150, 100, 125, 100},{780, 100, 1000, 100}, {1900, 100, 125, 100}, {550, 100, 125, 100}, //upper platforms (5)
     {450,1375,600,100}, {1175, 1375, 250,100}, {1550, 1375, 600, 100}, {1250,1275,100,100}, {950,1275,100,100}, {1550,1275,100,100},  //lower platforms & guards (6)
     {400,1000,1800, 100}, {450,625, 300, 100}, {900, 625, 300, 100}, {1350, 625, 300, 100}, {1800, 625, 300, 100}, {600,525,100,100}, {1800,525,100,100}, //middle platforms & guards (7)
     {150,1150, 125, 100}, {2300, 200, 135, 100}}; //moving platforms (2)
   
   int[][] platformDataCopy =   {{0,100,125,100}, {2435,100,125,100}, {0, 1375, 125, 100}, {2435,1375,125,100},  //spawn platforms  (4)
     {300,100,125,100}, {2150, 100, 125, 100},{780, 100, 1000, 100}, {1900, 100, 125, 100}, {550, 100, 125, 100}, //upper platforms (5)
     {450,1375,600,100}, {1175, 1375, 250,100}, {1550, 1375, 600, 100}, {1250,1275,100,100}, {950,1275,100,100}, {1550,1275,100,100},  //lower platforms & guards (6)
     {400,1000,1800, 100}, {450,625, 300, 100}, {900, 625, 300, 100}, {1350, 625, 300, 100}, {1800, 625, 300, 100}, {600,525,100,100}, {1800,525,100,100}, //middle platforms & guards (7)
     {150,1150, 125, 100}, {2300, 200, 135, 100}}; //moving platforms (2)
    //copy array of the platform data to store original coordinates (platformData changes/updates with movement)


  //Constructor - this runs first
  BackgroundFrame(int startingX, int startingY) { 
   loadMap(startingX,startingY);
    try {      //loads background image
      image = ImageIO.read(new File("background2.jpg"));
    }    catch (Exception e) { e.printStackTrace(); }
    makePlatforms();
  } //End of Constructor

  //the main gameloop - this is where the game state is updated
  public void update(int playerX,int playerY) {    

	  px = playerX;
	  py = playerY;
      
      if(platformData[22][1] >= movingUpperBound) {
        movingPlatform1Velocity = -1;
      }      
      if (platformData[22][1] <= movingLowerBound) {
        movingPlatform1Velocity = 1;
      }      
      if(platformData[23][1] >= movingUpperBound) {        
        movingPlatform2Velocity = -1;
      }      
      if (platformData[23][1] <= movingLowerBound) {
        movingPlatform2Velocity = 1;
      }
      if (movingUpperBound > 1150) {
        movingUpperBound = 1150;
      }
      if (movingLowerBound > 200) {
        movingLowerBound = 200;
      }      
      if(movingUpperBound < 350) {
        movingUpperBound = 350;
      }
      if(movingLowerBound < -600) {
        movingLowerBound = -600;
      }
      
      platformData[22][1] += movingPlatform1Velocity;
      platformData[23][1] += movingPlatform2Velocity;  
  }
  
  public void loadMap(int startingX, int startingY) {
   
  
  if(startingX == 0 && startingY == 100) {
   tempX = 640;
   tempY = 400;
  }
  else if(startingX == 2435 && startingY == 100) {
   tempX = -1920;
   tempY = 400;
  }
  else if(startingX == 0 && startingY == 1375) {
   tempX = 640;
   tempY = -400;
  }
  else {
   tempX = -1920;
   tempY = -400;
  }
  

  }
  
  public Platform[] passPlatforms() {
    return this.platforms;
  }
 
  public void makePlatforms() { //constructs platforms
    
    for (int i = 0; i < platforms.length; i ++) {
      platforms[i] = new Platform(platformData[i][0], platformData[i][1], platformData[i][2], platformData[i][3]);
    }
  }

 public Platform[] getplatforms() {
  return this.platforms;
 }

 public void draw(Graphics g, JPanel jpl) {

  g.drawImage(image, 0, 0, 1280, 800, null);
  for (int i = 0; i < platforms.length; i++) {
	  //System.out.println(px + " " + py);
		  g.drawImage(platforms[i].getImage(), (640+platformData[i][0]-px) , 400+platformData[i][1]-py, platformData[i][2],platformData[i][3], jpl);

	  
  }
 }
}