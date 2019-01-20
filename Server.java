
/*
 * [Server.java]
 * Program that creates a server, receives information from clients 
 * and sends commands to clients
 * @Author: Andrew Wu
 * Date: December 18, 2017
 */

//imports:
//io
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//net
import java.net.ServerSocket;
import java.net.Socket;
//util
import java.util.ArrayList;
import java.util.Random;

/*
 * Server
 * program that hosts a server
 */

class Server {
	// class variables:
	// server info:
	private int port;
	private ArrayList<Player> clientArrList;
	private ArrayList clientStreams;
	//boolean checks:
	private boolean serverRunning;
	private boolean gameStarted;
	//game variables:
	private ArrayList<String> startingPos;	//stores the starting positions of players
	//random number generator variables
	private int max = 3, min = 0;
	private int playersAlive = 0;
	private int winner = 0;

	// Server Constructor
	public Server(int port) {
		// initialize server info
		this.port = port;
	    Thread newServer = new Thread(new StartServer());
	    newServer.start();
		System.out.println("Starting server...");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server testServer = new Server(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * StartServer inner class that creates server and continuously accept clients
	 */
	public class StartServer implements Runnable {
		
		@Override
		public void run() {
			//initialize ArrayList for connected clients
			clientArrList = new ArrayList<>();
			//initialize ArrayList for client PrintWriters
			clientStreams = new ArrayList();
			//initialize starting positions ArrayList
			startingPos = new ArrayList();
			startingPos.add("0 100");
			startingPos.add("2435 100");
			startingPos.add("0 1375");
			startingPos.add("2435 1375");
			//initialize ServerSocket
			ServerSocket serverSock = null;

			try {
				//create new server
				System.out.println(port);
				serverSock = new ServerSocket(port);
				serverRunning = true;
				gameStarted = false;
				System.out.println("Server Started!");
				
				//keep on accepting new clients
				while (gameStarted == false && clientArrList.size() < 4) {
					Socket clientSocket = serverSock.accept();
					playersAlive++;
					//initialize client's PrintWriter
					PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
					clientStreams.add(pw);
					//start new thread to listen for info from client
					Thread t = new Thread(new ConnectionHandler(clientSocket, pw));
					t.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
				CreateGame createGameWindow = new CreateGame();
				createGameWindow.getErrorLabel().setText("Error! Port is already taken.");
			} finally {
				while(serverRunning == true) {
				}
				
				System.out.println("Server shut down.");
				//close Socket
				if (serverSock != null) {
					try {
						serverSock.close();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Failed to close server");
					}
				}
			}
		}
	}

	/*
	 * ConnectionHandler inner class for connection threads
	 *  Manages client activities
	 */
	public class ConnectionHandler implements Runnable {
		// inner class variables
		private BufferedReader input;
		private PrintWriter output;
		private Socket client;
		private Player clientPlayer;

		/*
		 * ConnectionHandler Constructor
		 */
		public ConnectionHandler(Socket clientSocket, PrintWriter output) {
			// set PrintWriter
			this.output = output;
			try {
				// set Socket
				this.client = clientSocket;
				// set BufferedReader
				InputStreamReader stream = new InputStreamReader(client.getInputStream());
				this.input = new BufferedReader(stream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// declarations
			String message;// the entire String received from client PrintWriter
			String command, data;
			//variables that store how much the player moved
			int changeX = 0;
			int changeY = 0;
			String direction = "right";
			
			try {
				// continuously listen to clients
				while (serverRunning == true) {
					if (input.ready()) {
						//get info from client
						message = input.readLine();
						//System.out.println("Info from client: " + message);//<-----debug----->
						
						// separate message String into a command code and data
			            command = message.substring(0,message.indexOf(" "));
			            message = message.substring(message.indexOf(" ")+1);
			            data = message;
			            
			            //client connected
						if(command.equals("OP_CONNECT") && gameStarted == false) {
							System.out.println(data + " has connected!");
							
							//generate a random number between 0 and 3, representing indexes
							Random rand = new Random();
							int randNum = rand.nextInt(max - min + 1) + min;
							//max -= 1; //subtract one from max since startingPos ArrayList size will shrink
							//temporary value
							String coords = startingPos.get(randNum);
							int x = Integer.parseInt(coords.substring(0, coords.indexOf(" ")));
							int y = Integer.parseInt(coords.substring(coords.indexOf(" ") + 1));
							//remove position from ArrayList
							//startingPos.remove(randNum);
							
							//temporary variables
							String name = data.substring(0,data.indexOf(" "));
							String selectedClass = data.substring(data.indexOf(" ") + 1);
							//System.out.println(selectedClass);
							//make a new Player object
							if(selectedClass.equals("Scout")){
								clientPlayer = new Scout(name,x,y);					
							}
							else if(selectedClass.equals("Sniper")) {
								clientPlayer = new Sniper(name,x,y);
							}
							else if(selectedClass.equals("Rifleman")) {
								clientPlayer = new Rifleman(name,x,y);
							}
							else if(selectedClass.equals("Specialist")) {
								clientPlayer = new Specialist(name,x,y);
							}
							//add client to ArrayList
							clientArrList.add(clientPlayer);
							
							//temporary variables
							StringBuilder tempMessage = new StringBuilder();
							tempMessage.append("OP_PLAYERS ");
							Player tempPlayer;
							
							selectedClass = "";
							
							//add client information to tempMessage
							for(int i = 0;i < clientArrList.size();i++) {
								tempPlayer = clientArrList.get(i);
								if (tempPlayer instanceof Specialist) {
									selectedClass = "Specialist";
								} else if (tempPlayer instanceof Sniper) {
									selectedClass = "Sniper";
								} else if (tempPlayer instanceof Scout) {
									selectedClass = "Scout";
								} else if(tempPlayer instanceof Rifleman){
									selectedClass = "Rifleman";
								}
								tempMessage.append(tempPlayer.getName() + " " + tempPlayer.getX() + " " + tempPlayer.getY() + " " + selectedClass + " ");
							}
							
							//send information to all clients
							sendToClients(tempMessage.toString());
						}
						//client disconnected
						else if(command.equals("OP_DISCONNECT")) {
							System.out.println(data + " has disconnected!");
							//close the server if the host has left game
							if(clientArrList.indexOf(clientPlayer) == 0) {
								serverRunning = false;
							}
							//remove client from ArrayList
							clientArrList.remove(clientPlayer);
						}
						//if a player has been shot
						else if(command.equals("OP_HIT")) {
							//info[0] = guy who got shot
							//info[1] = owner of bullet
							String info[] = data.split(" ");
							
							clientPlayer.setCurrentHealth(clientPlayer.getCurrentHealth() - Integer.parseInt(info[2]));
							if (clientPlayer.getCurrentHealth() <= 0) {
								System.out.println("dead");
								clientPlayer.die();
								clientPlayer.respawn(max, min, startingPos);
							}
							
							sendToClients("OP_HEALTH " + clientPlayer.getName() + " " + clientPlayer.getCurrentHealth()
									+ " " + clientPlayer.getLives());
						}
						//check for key pressed
						else if(command.equals("OP_PRESSED")) {
							// change client's position based on key pressed
							if (data.equals("A")) {
								changeX = -10;
								direction = "left";
							}
							else if (data.equals("D")) {
								changeX = 10;
								direction = "right";
							}
							 if (data.equals("SP")) {
								changeY = -70;
							}
						}
						//check for key released
						else if (command.equals("OP_RELEASED")) {
							// change client's position based on key pressed
							changeX = 0;
							changeY = 0;
						}
						else if (command.equals("OP_SHOOT")) {
							// relay new bullet info to all clients
							String tempMessage = "OP_SHOTSFIRED " + clientPlayer.getX() + " " + clientPlayer.getY()
									+ " " + data + " " + clientPlayer.getName();
							sendToClients(tempMessage);
						}
						else if(command.equals("OP_STARTGAME")) {
							//set boolean variable to true and alert all clients that game has been started
							gameStarted = true;
							sendToClients("OP_STARTGAME ");
						}
						else {
							//System.out.println("Failed to receive client message");
						}
						
						System.out.println(playersAlive);
						
						if(clientPlayer.getLives() == 0) {
							sendToClients("OP_OBSERVE " + clientPlayer.getName());
							playersAlive--;
						}
					
						if(playersAlive <= 1) {
							for(int i = 0;i < clientArrList.size();i++) {
								if(clientPlayer.getName().equals(clientArrList.get(i).getName())) {
									winner = i;
								}
							}
						}
						
						//check if game has ended
						if(playersAlive == 0) {
							sendToClients("OP_ENDGAME " + winner);
						}
						
						// if the state of the player has changed
						if (command.equals("PLAYER_STATE")) {
							if (data.equals("FALLING")) {
								clientPlayer.fall();
								clientPlayer.setFalling(true);
							} else {
								clientPlayer.setVertSpeed(0);
								clientPlayer.setFalling(false);
								clientPlayer.setY(clientPlayer.getPlatformY() - 150);
							}
						}
						
						//check if player has died by falling
						if(clientPlayer.getY() >= 1500) {
							clientPlayer.die();
							clientPlayer.respawn(max, min, startingPos);
							sendToClients("OP_DEATH " + clientPlayer.getName());
						}
						
						try {
							Thread.sleep(5);
						} catch (Exception exc) {
						} // delay*/
						
						//update player coords
						clientPlayer.setX(clientPlayer.getX()+changeX,direction);
						clientPlayer.setY(clientPlayer.getY() + changeY);
						//clientPlayer.setY(clientPlayer.getY()+changeY);
						// continuously send player coordinates to all clients
						sendToClients("OP_UPDATE " + clientPlayer.getName() + " " + clientPlayer.getX() + " "
								+ clientPlayer.getY() + " " + direction);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Server has shut down!");
				// close BufferedReader,PrintWriter, and Socket when server shuts down
				try {
					if (input != null) {
						input.close();
					}
					if (output != null) {
						output.close();
					}
					if (client != null) {
						client.close();
					}
				} catch (Exception e) {

				}
			}
		}
	}//end of ConnectionHandler
	
	/*
	 * sendToClients
	 */
	public void sendToClients(String message) {
		PrintWriter pw = null;
		
		try {
			for(int i = 0;i < clientStreams.size();i++) {
				pw = (PrintWriter)(clientStreams.get(i));
				pw.println(message);
				pw.flush();
			}
		}catch(Exception e) {
			System.out.println("Failed to send info to clients");
		}
	}//end of sendToClients
	
	/*
	 * isServerRunning
	 */
	public boolean isServerRunning() {
		return this.serverRunning;
	}
	
}//end of Server