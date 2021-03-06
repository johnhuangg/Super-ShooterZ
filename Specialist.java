/**
 * [Specialist.java]
 * this is the shotgunner class
 * @author John Huang
 * @date January 8, 2017
 */
class Specialist extends Player{
  //class variables
  private long startTime;
  private long endTime;
  private long elapsedTime;
  
  /**
   * Specialist
   * constructor for specialist class, takes in a bunch of variables for the class
   * @param String name, int x, int y, int health, Weapon weapon, int h, int w
   */ 
  Specialist(String name, int x, int y){
    super ( name,  x, y, 100, new Weapon(5,10,400,8), 50, 80);
  }
  
  /**
   * readyToFire
   * method that calculates the between shots and determines
   * if the player can shoot another bullet
   * @param nothing
   * returns boolean
   * @author: Andrew Wu
   */  
  public boolean readyToFire() {
    //same comments as rifleman
    endTime = System.nanoTime();
    elapsedTime = (endTime - startTime) / 1000000;    
    if (elapsedTime > 500) {
      return true;
    }
    return false;
  }//end of readyToFire
  
  /**
   * fire
   * method that sends bullet info to server
   * @params int,int, Client object
   * returns nothing
   */
  public void fire(int mx, int my, Client client) {
    //same comments as rifleman
    if (readyToFire()) {
      String message = "OP_SHOOT " + mx + " " + my + " " + "300" + " " + "20";
      client.sendInfo(message);
      startTime = System.nanoTime();
    }
  }//end of fire
}