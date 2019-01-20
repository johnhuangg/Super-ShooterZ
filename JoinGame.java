//imports:
//awt
import java.awt.EventQueue;
//swing
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JoinGame {
	//Global Declarations:
	//frame
	private JFrame frame;
	
	//JLabels
	private JLabel nameLabel;
	private JLabel ipLabel;
	private JLabel titleLabel;
	private JLabel portLabel;
	private JLabel errorLabel;
	
	//JTextFields
	private JTextField portField;
	private JTextField ipField;
	private JTextField nameField;

	//JButtons
	private JButton joinButton;
	private JButton backButton;
	private JButton selectSniperButton;
	private JButton selectRiflemanButton;
	private JButton selectSpecialistButton;
	private JButton selectScoutButton;
	
	//variable for storing which class the player selects
	private String selectedClass = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinGame window = new JoinGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JoinGame() {
		initialize();
	}
	
	/*
	 * getErrorLabel
	 * this gets errorLabel
	 * @params nothing
	 * return error JLabel
	 */
	public JLabel getErrorLabel() {
		return this.errorLabel;
	}//end of getErrorLabel

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//initialize JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//initialize JLabels:
		//titleLabel
		titleLabel = new JLabel("Join A Game");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		//portLabel
		portLabel = new JLabel("Port:");
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		//ipLabel
		ipLabel = new JLabel("IP:");
		ipLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		//nameLabel
		nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//initialize JTextFields:
		//portField
		portField = new JTextField();
		portField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		portField.setColumns(10);
		//ipField
		ipField = new JTextField();
		ipField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		ipField.setColumns(10);
		//nameField
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		nameField.setColumns(10);
		
		//initialize JButtons:
		//joinButton
		joinButton = new JButton("Join Game");
		joinButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if user has entered all information
				if (nameField.getText().equals("") || portField.equals("") || ipField.equals("")) {
					errorLabel.setText("Error! Fill in all text fields.");
				}
				else if(selectedClass.equals("")) {
					errorLabel.setText("Error! Choose a class.");
				}
				else {
					//get info from JTextFields
					String name = nameField.getText();
					String ip = ipField.getText();
					int port = Integer.parseInt(portField.getText());
					//try to connect to server
					Lobby lobbyWindow = new Lobby(ip, port, name,selectedClass);
					frame.dispose();
				}
			}
		});//end of joinButton ActionListener
		
		//backButton
		backButton = new JButton("Back");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create new menu window and close JoinGame frame
				Menu menuWindow = new Menu();
				frame.dispose();
			}
		});//end of backbutton ActionListener
		
		//selectScoutButton
		selectScoutButton = new JButton("");
		selectScoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedClass = "Scout";
			}
		});//end of selectScoutButton ActionListener
		
		//selectSniperButton
		selectSniperButton = new JButton("");
		selectSniperButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedClass = "Sniper";
			}
		});//end of selectSniperButton ActionListener
		
		//selectRiflemanButton
		selectRiflemanButton = new JButton("");
		selectRiflemanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedClass = "Rifleman";
			}
		});//end of selectRiflemanButton ActionListener
		
		//selectSpecialistButton
		selectSpecialistButton = new JButton("");
		selectSpecialistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedClass = "Specialist";
			}
		});//selectSpecialistButton ActionListener
		
		//setup GroupLayout
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(290)
							.addComponent(titleLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(158)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(portLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(portField, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
									.addGap(47)
									.addComponent(ipLabel)
									.addGap(18)
									.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(nameLabel)
									.addGap(18)
									.addComponent(nameField)))))
					.addContainerGap(155, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addComponent(backButton)
					.addGap(18)
					.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(joinButton)
					.addGap(50))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(64)
					.addComponent(selectScoutButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(selectSniperButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(selectRiflemanButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(selectSpecialistButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(115, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(titleLabel)
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(portLabel)
						.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ipLabel)
						.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(83)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectScoutButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectSniperButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectRiflemanButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectSpecialistButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(joinButton)
						.addComponent(backButton)
						.addComponent(errorLabel))
					.addGap(27))
		);
		frame.getContentPane().setLayout(groupLayout);
		
		frame.setVisible(true);
	}//end of initialize
}//end of JoinGame
