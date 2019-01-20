
/**
 * [Bullet.java]
 * This is the bullet class
 * @author Andrew Wu
 * Date: January 4, 2018
 */
import java.awt.Graphics;
import java.awt.Rectangle;

class Bullet {
	private int damage;
	private int x, y; // players coords
	private int screenX, screenY;
	private int mX, mY; // mouse coords
	private int speed = 20;
	private int range;
	private int travelled;
	private String owner;
	private Rectangle boundingBox;
	
	//map coords
	private double angle;
	private double xVelocity;
	private double yVelocity;
	
	//screen coords
	private double screenAngle;
	private double screenXVelocity;
	private double screenYVelocity;
	

	// constructor
	public Bullet(int x, int y, int mX, int mY, int range, int damage, String owner) {
		this.x = x;
		this.y = y;
		this.mX = mX;
		this.mY = mY;
		this.range = range;
		this.damage = damage;
		this.owner = owner;
		this.travelled = 0;
		this.screenX = 640;
		this.screenY = 400;
		this.boundingBox = new Rectangle(x, y, 10, 10);
	
		// find angle at which bullet is shot from
		this.angle = Math.atan2(mX - x, mY - y);
		this.screenAngle = Math.atan2(mX - screenX, mY - screenY);
		
		// calculate velocity for map coords
		this.xVelocity = (speed) * Math.sin(screenAngle);
		this.yVelocity = (speed) * Math.cos(screenAngle);
	
		// calculate velocity for screen
		this.screenXVelocity = (speed) * Math.sin(screenAngle);
		this.screenYVelocity = (speed) * Math.cos(screenAngle);
	}// end of Bullet constructor
	
	/**
	 * getDamage
	 * this returns damage
	 */
	public int getDamage() {
		return this.damage;
	}//end of getDamage
	
	/**
	 * getBoundingBox
	 * this returns the bounding box of the bullet
	 */
	public Rectangle getBoundingBox() {
		return this.boundingBox;
	}//end of getBoundingBox
	
	/**
	 * getOwner 
	 * method that returns owner of bullet
	 * @params nothing 
	 * returns String
	 */
	public String getOwner() {
		return this.owner;
	}//end of getOwner
	
	/**
	 * drawEnemyBullet 
	 * method that draws other player's bullets
	 * @params Graphics 
	 * returns nothing
	 */
	public void drawEnemyBullet(Graphics g,int playerX,int playerY) {
		g.fillOval(640+x-playerX, y+400-playerY, 10, 10);
	//	640+tempX-myPlayer.getX(), 400+tempY-myPlayer.getY()
	}// end of drawEnemyBullet

	/**
	 * drawMyBullet 
	 * method that draws a player's own bullets, the coords being from
	 * the center of the screen
	 * @params Graphics 
	 * returns nothing
	 */
	public void drawMyBullet(Graphics g) {
		g.fillOval(screenX, screenY, 10, 10);
	}// end of drawMyBullet

	/**
	 * update 
	 * updates the position of the bullet 
	 * @params nothing
	 * returns nothing
	 */
	public boolean update() {
		if (travelled < range) {
			// temporary storage of past coords
			int tempX = x;
			int tempY = y;

			// update new coords
			x += xVelocity;
			y += yVelocity;
			screenX += screenXVelocity;
			screenY += screenYVelocity;
			
			//640+tempX-myPlayer.getX(), 400+tempY-myPlayer.getY()

			//update bounding box
			this.boundingBox.x = (int)(boundingBox.getX() + xVelocity);
			this.boundingBox.y = (int)(boundingBox.getY() + yVelocity);
			
			// calculate distance traveled
			travelled += (int) Math.sqrt(Math.pow(tempX - x, 2) + Math.pow(tempY - y, 2));
			return true;
		}
		return false;
	}// end of update
}// end of Bullet class