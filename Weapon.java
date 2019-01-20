/**
 * [Weapon.java]
 * this is the weapon class
 * @author John Huang
 * @date December 20,2017
 */
class Weapon{
  //class variables
  private int damage;
  private int fireRate;
  private int range;
  private int magazine;
  
  Weapon(int damage, int fireRate, int range, int magazine){
    //initialize the class variables
    this.damage=damage;
    this.fireRate=fireRate;
    this.range=range;
    this.magazine=magazine;
    
  }
  /**
   * setDamage
   * this sets the damage of the weapon
   * @param int damage which is the damage
   */ 
  public void setDamage(int damage){
    this.damage=damage;
  }
  /**
   * getDamage
   * this gets the damage of the player
   * @return int damage which is the damage
   */ 
  public int getDamage(){
    return this.damage;
  }
  /**
   * setFireRate
   * this sets the fireRate of the weapon
   * @param int fireRate which is the fire rate of the weapon
   */ 
  public void setFireRate(int fireRate){
    this.fireRate=fireRate;
  }
  /**
   * getRange
   * this gets the range of the player
   * @return int range which is the range
   */ 
  public int getRange(){
    return this.range;
  }
  /**
   * setRange
   * this sets the range of the weapon
   * @param int range which is the range
   */ 
  public void setRange(int range){
    this.range=range;
  }
  /**
   * getMagazine
   * this gets the magazine of the player
   * @return int magazine which is the damage
   */ 
  public int getMagazine(){
    return this.magazine;
  } 
  /**
   * setMagazine
   * this sets the magazine of the weapon
   * @param int magazine which is the magazine
   */ 
  public void setMagazine(int magazine){
    this.magazine=magazine;
  }
  
  
}