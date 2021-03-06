
/*
 * Client
 * Program that connects to a server, gets information from server,
 * and sends information to server
 * @Author: Andrew Wu
 * Date: September 18, 2017
 */
//imports:
//awt
import java.awt.EventQueue;
//io
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//net
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//swing
import javax.swing.JFrame;

public class Client {
	// class variables
	// JFrame
	private JFrame frame;

	// client info
	private String ip;
	private String name;
	private int port;
	private String selectedClass;
	// client tools
	private Socket mySocket;
	private BufferedReader input;
	private PrintWriter output;
	// boolean checks
	private boolean serverRunning;
	private boolean gameStarted;
	private boolean playerDied;

	// client's player
	private Player clientPlayer;
	private int changeX;
	private int changeY;

	// player coordinates
	private ArrayList<Player> playerArrList;

	// messages from server
	private Queue<String> playerCoords;

	// in-game projectiles
	private ArrayList<Bullet> bulletArrList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client("127.0.0.1", 5000, "Andrew", "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client(String ip, int port, String name, String selectedClass) {
		// initialize client info variables
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.selectedClass = selectedClass;
		// initialize ArrayLists
		playerArrList = new ArrayList<>();
		playerCoords = new LinkedList<String>();
		playerDied = false;
		// initialize();
	}

	/**
	 * sendInfo
	 */
	public void sendInfo(String info) {
		try {
			output.println(info);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * getPlayerList method that returns playerArrList ArrayList
	 * 
	 * @params nothing returns ArrayList
	 */
	public ArrayList<Player> getPlayerList() {
		return this.playerArrList;
	}// end of getPlayerList

	/**
	 * getMyPlayer method that returns this client's player
	 * 
	 * @params nothing returns nothing
	 */
	public Player getMyPlayer() {
		return clientPlayer;
	}//

	/**
	 * getBulletList method that returns bulletArrList ArrayList
	 * 
	 * @params nothing returns ArrayList
	 */
	public ArrayList<Bullet> getBulletList() {
		return this.bulletArrList;
	}// end of getBulletList

	/**
	 * getGameStarted method that returns gameStarted Boolean
	 * 
	 * @params nothing returns boolean
	 */
	public boolean getGameStarted() {
		return this.gameStarted;
	}// end of getGameStarted

	/**
	 * getChangeX
	 */
	public int getChangeX() {
		return this.changeX;
	}

	/**
	 * getChangeY
	 */
	public int getChangeY() {
		return this.changeY;
	}

	/**
	 * getPlayerDied returns if player died or not
	 */
	public boolean getPlayerDied() {
		return this.playerDied;
	}

	/**
	 * setPlayerDied
	 */
	public void setPlayerDied(boolean playerDied) {
		this.playerDied = playerDied;
	}

	/**
	 * connect Connection method that connects this client to server
	 * 
	 * @param nothing
	 *            returns nothing
	 */
	public void connect() {
		try {
			// create new socket
			mySocket = new Socket(ip, port);
			System.out.println("connected to server");

			// initialize InputStreamReader
			InputStreamReader stream = new InputStreamReader(mySocket.getInputStream());
			input = new BufferedReader(stream);

			// initialize PritnWriter
			output = new PrintWriter(mySocket.getOutputStream());

			// send connect info to server
			output.println("OP_CONNECT " + name + " " + selectedClass);
			output.flush();

			// update serverRunning variable
			serverRunning = true;
			gameStarted = false;

			// begin new thread to received info from server
			Thread t = new Thread(new ServerListener(mySocket, output));
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failure to connect");
		}
	}// end of connect

	public class ServerListener implements Runnable {
		private BufferedReader input;
		private PrintWriter output;
		private Socket client;

		/**
		 * ServerListener constructor
		 */
		public ServerListener(Socket clientSocket, PrintWriter output) {
			// initialize PrintWriter
			this.output = output;
			try {
				// initialize Socket
				this.client = clientSocket;
				// initialize Readers
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// declarations:
			String message;// message from server
			String command, data;
			int previousY = 0;

			try {
				// keep on receiving info from server
				while (serverRunning == true) {
					if (this.input.ready()) {
						// get info from server
						message = this.input.readLine();
						// System.out.println("Info from Server: " + message); //<-----debug----->
						// separate message String into a command code and data
						command = message.substring(0, message.indexOf(" "));
						message = message.substring(message.indexOf(" ") + 1);
						data = message;

						if (command.equals("OP_UPDATE")) {
							// decode message
							String[] info = data.split(" ");

							if (name.equals(info[0])) {// name
								clientPlayer.setX(Integer.parseInt(info[1]), info[3]);// x
								clientPlayer.setY(Integer.parseInt(info[2]));// y
							} else {

								for (int i = 0; i < playerArrList.size(); i++) {
									Player tempPlayer = playerArrList.get(i);
									if (tempPlayer.getName().equals(info[0])) {
										tempPlayer.setX(Integer.parseInt(info[1]), info[3]);// x
										tempPlayer.setY(Integer.parseInt(info[2]));// y
									}
								}
							}

						} else if (command.equals("OP_PLAYERS")) {
							playerArrList.clear();
							System.out.println("Data: " + data);
							while (data.indexOf(" ") != -1) {
								// decode message
								String tempName = data.substring(0, data.indexOf(" "));
								data = data.substring(data.indexOf(" ") + 1);
								int tempX = Integer.parseInt(data.substring(0, data.indexOf(" ")));
								data = data.substring(data.indexOf(" ") + 1);
								int tempY = Integer.parseInt(data.substring(0, data.indexOf(" ")));
								data = data.substring(data.indexOf(" ") + 1);
								String playerClass = data.substring(0, data.indexOf(" "));
								data = data.substring(data.indexOf(" ") + 1);

								Player tempPlayer;
								
								System.out.println("Player class " + playerClass);
								// make a new Player object
								if (playerClass.equals("Scout")) {
									System.out.println("CHECK1");
									tempPlayer = new Scout(tempName, tempX, tempY);
								} else if (playerClass.equals("Sniper")) {
									System.out.println("CHECK2");
									tempPlayer = new Sniper(tempName, tempX, tempY);
								} else if (playerClass.equals("Rifleman")) {
									System.out.println("CHECK3");
									tempPlayer = new Rifleman(tempName, tempX, tempY);
								} else {
									System.out.println("CHECK4");
									tempPlayer = new Specialist(tempName, tempX, tempY);
								}

								// add player to ArrayList
								playerArrList.add(tempPlayer);

								if (tempName.equals(name)) {
									clientPlayer = tempPlayer;
								}
							}
						} else if (command.equals("OP_SHOTSFIRED")) {
							// decode data from server
							String[] info = data.split(" ");

							// create new Bullet object
							Bullet newBullet = new Bullet(Integer.parseInt(info[0]), Integer.parseInt(info[1]),
									Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]),
									Integer.parseInt(info[5]), info[6]);

							// add new bullet to ArrayList
							bulletArrList.add(newBullet);
						} else if (command.equals("OP_HEALTH")) {
							String[] info = data.split(" ");

							if (info[0].equals(name)) {
								clientPlayer.setCurrentHealth(Integer.parseInt(info[1]));
								clientPlayer.setLives(Integer.parseInt(info[2]));
							}

							for (int i = 0; i < playerArrList.size(); i++) {
								if (playerArrList.get(i).getName().equals(info[0])) {
									playerArrList.get(i).setCurrentHealth(Integer.parseInt(info[1]));
									playerArrList.get(i).setLives(Integer.parseInt(info[2]));
								}
							}
						} else if (command.equals("OP_DEATH")) {
							if (data.equals(clientPlayer.getName())) {
								playerDied = true;
								clientPlayer.die();
								changeX = 0;
								changeY = 0;
								previousY = 0;
							}
						} else if (command.equals("OP_OBSERVE")) {
							if (name.equals(data)) {
								clientPlayer.setObserve(true);
							}
						} else if (command.equals("OP_ENDGAME")) {
							client.close();
							Player[] tempArr = new Player[playerArrList.size()];
							tempArr = playerArrList.toArray(tempArr);
							EndFrame endFrame = new EndFrame(tempArr, Integer.parseInt(data));
						} else if (command.equals("OP_STARTGAME")) {
							gameStarted = true;
						} else {
							System.out.println("Failed to receive message from server");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (output != null) {
						output.close();
					}
					if (input != null) {
						input.close();
					}
					if (client != null) {
						client.close();
					}
				} catch (Exception e) {
					System.out.println("Failed to close client.");
				}

			}
		}
	}// end of ServerListener

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// initialize JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

}
