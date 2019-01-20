/**
 * [Sniper.java]
 * this is the sniper class
 * @author John Huang
 * @date January 8, 2017
 */

class Sniper extends Player{
  //class variables
  private long startTime;
  private long endTime;
  private long elapsedTime;
  /**
   * sniper
   * constructor for sniper class, takes in a bunch of variables for the class
   * @param String name, int x, int y, int health, Weapon weapon, int h, int w
   */ 
  Sniper(String name, int x, int y) {
    super(name, x, y, 100, new Weapon(15, 1, 1000, 7), 50, 80);
  }
  
  /**
   * readyToFire 
   * method that calculates the between shots and determines if the
   * player can shoot another bullet
   * @param nothing
   * returns boolean
   * @author: Andrew Wu
   */
  public boolean readyToFire() {
    //same comments as rifleman
    endTime = System.nanoTime();
    elapsedTime = (endTime - startTime) / 1000000;
    
    if (elapsedTime > 1000) {
      return true;
    }
    return false;
  }//end of readyToFire
  /**
   * fire
   * method that fires bullet
   * @param int mx, int my,Client client
   * @author: Andrew Wu
   */
  public void fire(int mx, int my, Client client) {
    //same comments as rifleman
    String message = "OP_SHOOT " + mx + " " + my + " " + "300" + " " + "50";
    client.sendInfo(message);
    startTime = System.nanoTime();
  }//end of fire  
}