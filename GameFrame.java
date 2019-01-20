/**
* [GameFrame.java]
* main program for the game
* @author Andrew Wu
* Date: January 7, 2018
**/

//Graphics &GUI imports
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.EventQueue;
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

class GameFrame extends JFrame {

	// class variable (non-static)
	static double x, y;
	static GameAreaPanel gamePanel;

	// player movement
	private int changeX = 0;
	private int changeY = 0;

	// client info
	private Client client;
	private ArrayList<Player> playerList;
	private ArrayList<Bullet> bulletList;
	private Player myPlayer;

	// map
	private BackgroundFrame map;

	// gui
	private HealthBar healthBar;

	// player image array
	private BufferedImage playerImages[][][];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame newGame = new GameFrame(new Client("127.0.0.1", 5000, "5000", ""));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor - this runs first
	GameFrame(Client client) {

		super("My Game");
		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280, 800);
		// this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		// this.setUndecorated(true); //Set to true to remove title bar
		this.setResizable(false);

		// initialize player images

		// initialize ArrayLists
		playerList = new ArrayList<>();
		bulletList = new ArrayList<>();

		// initialize client
		this.client = client;

		// initialize healthbar
		healthBar = new HealthBar(client.getPlayerList().size());

		// initialize map loader
		map = new BackgroundFrame(client.getMyPlayer().getX(), client.getMyPlayer().getY());

		// Set up the game panel (where we put our graphics)
		gamePanel = new GameAreaPanel();
		this.add(new GameAreaPanel());

		// add keylistener
		MyKeyListener keyListener = new MyKeyListener();
		this.addKeyListener(keyListener);

		// add mouselistener
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);

		// add mousemotionlistener
		MyMouseMotionListener mouseMotionListener = new MyMouseMotionListener();
		this.addMouseMotionListener(mouseMotionListener);

		// request window focus
		this.requestFocusInWindow(); // make sure the frame has focus
		// make window visible
		this.setVisible(true);

		// load images
		try {
			// [class][direction][image]
			// reg,mach,shot,snip
			// right,left
			// arm,jump,walk in order
			playerImages = new BufferedImage[][][] { { { ImageIO.read(new File("Reg_arm_right.png")),
					ImageIO.read(new File("Reg_jump_right.png")), ImageIO.read(new File("Reg_bod_right_1.png")),
					ImageIO.read(new File("Reg_bod_right_2.png")), ImageIO.read(new File("Reg_bod_right_3.png")) },
					{ ImageIO.read(new File("Reg_arm_left.png")), ImageIO.read(new File("Reg_jump_left.png")),
							ImageIO.read(new File("Reg_bod_left_1.png")), ImageIO.read(new File("Reg_bod_left_2.png")),
							ImageIO.read(new File("Reg_bod_left_3.png")) } },
					{ { ImageIO.read(new File("Mach_arm_right.png")), ImageIO.read(new File("Mach_jump_right.png")),
							ImageIO.read(new File("Mach_bod_right_1.png")),
							ImageIO.read(new File("Mach_bod_right_2.png")),
							ImageIO.read(new File("Mach_bod_right_3.png")),
							ImageIO.read(new File("Mach_bod_right_4.png")) },
							{ ImageIO.read(new File("Mach_arm_left.png")), ImageIO.read(new File("Mach_jump_left.png")),
									ImageIO.read(new File("Mach_bod_left_1.png")),
									ImageIO.read(new File("Mach_bod_left_2.png")),
									ImageIO.read(new File("Mach_bod_left_3.png")),
									ImageIO.read(new File("Mach_bod_left_4.png")) } },
					{ { ImageIO.read(new File("Shot_arm_right.png")), ImageIO.read(new File("Shot_jump_right.png")),
							ImageIO.read(new File("Shot_bod_right_1.png")),
							ImageIO.read(new File("Shot_bod_right_2.png")),
							ImageIO.read(new File("Shot_bod_right_3.png")) },
							{ ImageIO.read(new File("Shot_arm_left.png")), ImageIO.read(new File("Shot_jump_left.png")),
									ImageIO.read(new File("Shot_bod_left_1.png")),
									ImageIO.read(new File("Shot_bod_left_2.png")),
									ImageIO.read(new File("Shot_bod_left_3.png")) } },
					{ { ImageIO.read(new File("Snip_arm_right.png")), ImageIO.read(new File("Snip_jump_right.png")),
							ImageIO.read(new File("Snip_bod_right_1.png")),
							ImageIO.read(new File("Snip_bod_right_2.png")),
							ImageIO.read(new File("Snip_bod_right_3.png")) },
							{ ImageIO.read(new File("Snip_arm_left.png")), ImageIO.read(new File("Snip_jump_left.png")),
									ImageIO.read(new File("Snip_bod_left_1.png")),
									ImageIO.read(new File("Snip_bod_left_2.png")),
									ImageIO.read(new File("Snip_bod_left_3.png")) } } };
		} catch (IOException e) {
			System.out.println("Failed to load images");
		}

		// Start the game loop in a separate thread
		Thread t = new Thread(new Runnable() {
			public void run() {
				animate();
			}
		}); // start the gameLoop
		t.start();
	} // End of Constructor

	/**
	 * drawSprites
	 */
	public void drawSprites(Graphics g, Player player, int x, int y) {
		if (player instanceof Specialist) {
			// System.out.println("CHECK1");
			if (player.getDirection().equals("right")) {
				g.drawImage(playerImages[0][0][2], x, y, 50, 80, null);
			} else {
				g.drawImage(playerImages[0][1][2], x, y, 50, 80, null);
			}
		} else if (player instanceof Sniper) {
			// System.out.println("CHECK2");
			if (player.getDirection().equals("right")) {
				g.drawImage(playerImages[3][0][2], x, y, 50, 80, null);
			} else {
				g.drawImage(playerImages[3][1][2], x, y, 50, 80, null);
			}
		} else if (player instanceof Scout) {
			// System.out.println("CHECK3");
			if (player.getDirection().equals("right")) {
				g.drawImage(playerImages[1][0][2], x, y, 50, 80, null);
			} else {
				g.drawImage(playerImages[1][1][2], x, y, 50, 80, null);
			}
		} else {
			// System.out.println("CHECK4");
			if (player.getDirection().equals("right")) {
				g.drawImage(playerImages[2][0][2], x, y, 50, 80, null);
			} else {
				g.drawImage(playerImages[2][1][2], x, y, 50, 80, null);
			}
		}
	}// end of drawSprites

	// the main gameloop - this is where the game state is updated
	public void animate() {

		while (true) {
			/*
			 * this.x = (Math.random() * 1024); // update coords this.y = (Math.random() *
			 * 768);
			 */
			try {
				Thread.sleep(40);
			} catch (Exception exc) {
			} // delay*/
			this.repaint();
		}
	}

	/** --------- INNER CLASSES ------------- **/

	// Inner class for the the game area - This is where all the drawing of the
	// screen occurs
	private class GameAreaPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required
			setDoubleBuffered(true);

			// update this client's player
			myPlayer = client.getMyPlayer();

			if (client.getPlayerDied() == true) {
				client.setPlayerDied(false);
				map.loadMap(myPlayer.getX(), myPlayer.getY());
			}

			// update and draw map
			map.update(myPlayer.getX(), myPlayer.getY());
			map.draw(g, this);
			
			if (myPlayer.getObserve() == false) {
				// check if this client's player is on a platform
				if (myPlayer.collisionDetect(map.getplatforms()) == true) {
					client.sendInfo("PLAYER_STATE FALLING");
					// myPlayer.fall();
				} else {
					client.sendInfo("PLAYER_STATE NOT_FALLING");
				}
			}

			// receive Player info from server
			playerList = client.getPlayerList();
			// draw this player
			if (myPlayer.getObserve() == false) {
				drawSprites(g, myPlayer, 640, 400);
			}
			// g.fillRect(640, 400, 50, 50);
			// myPlayer.drawBoundingBox(g);

			// System.out.println(myPlayer.getX() + " " + myPlayer.getY());

			g.setColor(Color.RED);
			// draw other players
			for (int i = 0; i < playerList.size(); i++) {
				// ignore this client's player
				if (!playerList.get(i).getName().equals(myPlayer.getName())) {
					int tempX = playerList.get(i).getX();
					int tempY = playerList.get(i).getY();

					// System.out.println(tempX + " " + tempY);
					// g.fillOval(640 + tempX - myPlayer.getX(), 400 + tempY - myPlayer.getY(), 50,
					// 50); // notice the x,y
					drawSprites(g, playerList.get(i), 640 + tempX - myPlayer.getX(), 400 + tempY - myPlayer.getY());
				}
		/*		if (playerList.get(i) instanceof Specialist) {
					System.out.println("CHECK1");
				} else if (playerList.get(i) instanceof Sniper) {
					System.out.println("CHECK2");
				} else if (playerList.get(i) instanceof Scout) {
					System.out.println("CHECK3");
				} else {
					System.out.println("CHECK4");
				}*/
				healthBar.setPlayerLives(i, playerList.get(i).getLives());
			}

			// draw gui
			healthBar.setCurrentHealth(myPlayer.getCurrentHealth());
			healthBar.draw(g);

			// create in-game bullets
			bulletList = client.getBulletList();

			// draw bullets
			for (int i = 0; i < bulletList.size(); i++) {
				// check if client's player has been shot
				if (myPlayer.checkShot(bulletList.get(i)) == true
						&& !bulletList.get(i).getOwner().equals(myPlayer.getName())) {
					client.sendInfo("OP_HIT " + myPlayer.getName() + " " + bulletList.get(i).getOwner() + " "
							+ bulletList.get(i).getDamage());
					client.getBulletList().remove(i);
				}
				// if it hasn't reached max range
				else if (bulletList.get(i).update() == true) {
					if (bulletList.get(i).getOwner().equals(myPlayer.getName())) {
						bulletList.get(i).drawMyBullet(g);
					} else {
						bulletList.get(i).drawEnemyBullet(g,myPlayer.getX(),myPlayer.getY());
					}
				} else {
					client.getBulletList().remove(i);
				}
			}
			try {
				Thread.sleep(5);
			} catch (Exception e) {

			}
		}
	}

	// ----------- Inner class for the keyboard listener - this detects key presses
	// and runs the corresponding code
	private class MyKeyListener implements KeyListener {

		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			// System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
			// else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // If ESC is pressed
			if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
				client.sendInfo("OP_PRESSED A");
				changeX = -10;
			} else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
				client.sendInfo("OP_PRESSED D");
				changeX = 10;
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				client.sendInfo("OP_PRESSED SP");
			}
		}

		public void keyReleased(KeyEvent e) {
			client.sendInfo("OP_RELEASED ");
			changeX = 0;
		}
	} // end of keyboard listener

	// ----------- Inner class for the keyboard listener - This detects mouse
	// movement & clicks and runs the corresponding methods
	private class MyMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			System.out.println("Mouse Clicked");
			System.out.println("X:" + e.getX() + " y:" + e.getY());
		}

		public void mousePressed(MouseEvent e) {
			System.out.println("shoot");
			if(myPlayer.getObserve() == false) {
				client.getMyPlayer().fire(e.getX(), e.getY(), client);
			}
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	} // end of mouselistener

	// --------- Inner class for Listening to movement of mouse
	private class MyMouseMotionListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
			if(myPlayer.getObserve() == false) {
				client.getMyPlayer().fire(e.getX(), e.getY(), client);
			}
		}

		public void mouseMoved(MouseEvent e) {
		}
	}
}