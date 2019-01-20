/**
 * [HealthBar.java]
 * this is the healthbar class
 * @author John Huang
 * @date January 11, 2018
 */

//imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

class HealthBar{
  //class variables
  private double maxHealth=100.0;
  private int currentHealth=100;
  private int outerWidth=200;
  private int outerHeight=50;
  private int innerWidthAdjust=180;
  private int innerWidth=180;
  private int innerHeight=40;
  private int outerX=540;
  private int outerY=700;
  private int innerX=550;
  private int innerY=705;
  private int[] playerHealth;
  /**
   * HealthBar
   * constructor for HealthBar
   */ 
  HealthBar(int num){
    //create array for the number of players
    playerHealth= new int[num];
    //make each player have 5 hp
    for (int i=0;i<num;i++){
      playerHealth[i]=5;
    }
  }
  /**
   * draw
   * method for drawing the rectangles for healthbar
   **/ 
  public void draw(Graphics g) {
    //set color to gray
    g.setColor(Color.GRAY);
    //draw a rectangle
    g.fillRect(outerX,outerY,outerWidth,outerHeight); 
    //set color to red
    g.setColor(Color.RED); 
    //draw a rectangle
    g.fillRect(innerX, innerY, innerWidthAdjust,innerHeight); 
    //set color to white
    g.setColor(Color.WHITE);
    //set font to white
    g.setFont(new Font ("Arial",Font.BOLD,24));
    //draw the string
    g.drawString(currentHealth+"/100",605,735);
    //for each player, draw his health bar
    for (int i=0;i<this.playerHealth.length;i++){
      //set font
      g.setFont(new Font ("Arial",Font.BOLD,24));
      //draw the string for "player"
      g.drawString("Player "+(i+1),20,90+i*100);
      //set the x and y for the coords for drawing
      int y=(80+i*100)-(50/2);
      int x=150-(50/2);
      //set color to red
      g.setColor(Color.RED); 
      //fill the circle
      g.fillOval(x, y, 50, 50);
      //set font 
      g.setFont(new Font ("Arial",Font.BOLD,30));   
      //set color to whtie
      g.setColor(Color.WHITE); 
      //draw the player health
      g.drawString(Integer.toString(playerHealth[i]),142,92+i*100);
    }
  }
  /**
   * setCurrentHealth
   * method for setting health of player
   **/ 
  public void setCurrentHealth(int health){
    //set the health of player
    this.currentHealth=health;
    //adjust the health bar
    innerWidthAdjust=(int)((currentHealth/maxHealth)*innerWidth);   
  }
  /**
   * setPlayerLives
   * method for setting the lives of a player
   **/ 
  public void setPlayerLives(int num, int lives){
    this.playerHealth[num]=lives;
  }
}