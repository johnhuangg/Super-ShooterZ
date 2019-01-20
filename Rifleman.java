/**
 * [Rifleman.java]
 * this is the rifleman class
 * @author John Huang
 * @date January 8, 2017
 */

class Rifleman extends Player{
  private long startTime;
  private long endTime;
  private long elapsedTime;
  
  /**
   * rifleman
   * constructor for rifleman class, takes in a bunch of variables for the class
   * @param String name, int x, int y, int health, Weapon weapon, int h, int w
   */ 
  Rifleman(String name, int x, int y){
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
    //get end time 
    endTime = System.nanoTime();
    //calculated the elapsed time
    elapsedTime = (endTime - startTime) / 1000000;
    //return true if over 150 ms
    if (elapsedTime > 150) {
      return true;
    }
    //return false
    return false;
  }//end of readyToFire
  /**
   * fire
   * method that fires bullet
   * @param int mx, int my,Client client
   * @author: Andrew Wu
   */
  public void fire(int mx, int my,Client client){
    //print to the system
    String message = "OP_SHOOT " + mx + " " + my + " " + "300" + " " + "1";
    //send the information to the client
    client.sendInfo(message);
    //start time
    startTime = System.nanoTime();
  }//end of fire
}