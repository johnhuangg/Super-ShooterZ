//imports:
//swing
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
//awt
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class HostLobby {
	//Global Declarations:
	//JFrame
	private JFrame frame;
	
	//JLabels
	private JLabel waitingLabel;
	private JLabel player1Label;
	private JLabel player2Label;
	private JLabel player3Label;
	private JLabel player4Label;
	
	//JButton
	private JButton startButton;
	
	//GroupLayout
	private GroupLayout groupLayout;
	
	//Lobby Variables
	private String name;
	private int port;
	private String ip;
	private String selectedClass;
	private boolean gameStarted;
	private ArrayList<Player> playerArrList;
	private JLabel[] labelArr = new JLabel[4];
	private Client newClient;
	
	/** 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HostLobby window = new HostLobby("127.0.0.1",5000,"","");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HostLobby(String ip,int port,String name,String selectedClass) {
		//initialize class variables
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.selectedClass = selectedClass;
		playerArrList = new ArrayList<>();
		
		// buildGUI
		initialize();
		
		Thread t = new Thread(new StartClient());
		t.start();
	}
	
	/*
	 * StartClient Thread
	 */
	public class StartClient implements Runnable{
		@Override
		public void run() {
			// make new client
			newClient = new Client(ip, port, name, selectedClass);
			newClient.connect();

			// if game hasn't started, keep on checking for lobby members
			while (gameStarted == false) {
				try {
					// get list of all players from server
					playerArrList = newClient.getPlayerList();
					// update names in lobby
					for (int i = 0; i < playerArrList.size(); i++) {
						labelArr[i].setText(playerArrList.get(i).getName());
					}
					// check if game has started
					gameStarted = newClient.getGameStarted();
					Thread.sleep(1000);// delay
				} catch (Exception e) {

				}
			}
			frame.dispose();
			GameFrame newGame = new GameFrame(newClient);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//initialize JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//initialize JLabels:
		//waitingLabel
		waitingLabel = new JLabel("Waiting For Players To Join...");
		waitingLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		//player1Label
		player1Label = new JLabel("Player One...");
		player1Label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		//player2Label
		player2Label = new JLabel("Player Two...");
		player2Label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		//player3Label
		player3Label = new JLabel("Player Three...");
		player3Label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		//player4Label
		player4Label = new JLabel("Player Four...");
		player4Label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		// add all Labels to array
		labelArr[0] = player1Label;
		labelArr[1] = player2Label;
		labelArr[2] = player3Label;
		labelArr[3] = player4Label;
		
		//initialize JButtons:
		//startButton
		startButton = new JButton("Start Game");
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newClient.sendInfo("OP_STARTGAME ");
			}
		});//end of startButton ActionListener
		
		//setup GroupLayout
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(187)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(waitingLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(player1Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(player2Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(player3Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(player4Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(202, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(587, Short.MAX_VALUE)
					.addComponent(startButton)
					.addGap(38))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(60)
					.addComponent(waitingLabel)
					.addGap(67)
					.addComponent(player1Label)
					.addGap(34)
					.addComponent(player2Label)
					.addGap(37)
					.addComponent(player3Label)
					.addGap(31)
					.addComponent(player4Label)
					.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
					.addComponent(startButton)
					.addGap(34))
		);
		//add Group Layout to frame
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}

}
