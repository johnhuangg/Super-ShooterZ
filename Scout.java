/**
 * [Scout.java]
 * this is the scout class
 * @author John Huang
 * @date January 8, 2017
 */

class Scout extends Player{
  //class variables
  private long startTime;
  private long endTime;
  private long elapsedTime;
  
  /**
   * scout
   * constructor for scout class, takes in a bunch of variables for the class
   * @param String name, int x, int y, int health, Weapon weapon, int h, int w
   */ 
  Scout(String name, int x, int y){
    super ( name,  x, y, 100, new Weapon(2,10,500,14), 50, 80);
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
    //get the end time
    endTime = System.nanoTime();
    //calculate end time
    elapsedTime = (endTime - startTime) / 1000000;
    //if its over 200, return true
    if (elapsedTime > 200) {
      return true;
    }
    //else return false
    return false;
  }//end of readyToFire
  /**
   * fire
   * method that fires bullet
   * @param int mx, int my,Client client
   * @author: Andrew Wu
   */
  public void fire(int mx, int my,Client client){
    //same comments as rifleman
    String message = "OP_SHOOT " + mx + " " + my + " " + "300" + " " + "5";
    client.sendInfo(message);
    startTime = System.nanoTime();
  }//end of fire
}