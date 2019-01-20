
//imports:
//awt
import java.awt.EventQueue;
import java.awt.Font;
//swing
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateGame {
	// Global Declarations:
	// frame
	private JFrame frame;

	// labels
	private JLabel titleLabel;
	private JLabel portLabel;
	private JLabel nameLabel;
	private JLabel errorLabel;

	// textFields
	private JTextField portField;
	private JTextField nameField;

	// buttons
	private JButton createButton;
	private JButton backButton;
	private JButton selectSniperButton;
	private JButton selectScoutButton;
	private JButton selectRiflemanButton;
	private JButton selectSpecialistButton;

	// GroupLayout
	private GroupLayout groupLayout;

	// variable for storing which class the player selects
	private String selectedClass = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGame window = new CreateGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateGame() {
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
		// initialize JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// initialize JLabels:
		// titleLabel
		titleLabel = new JLabel("Host A Game");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		// portLabel
		portLabel = new JLabel("Port:");
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		// nameLabel
		nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		// errorLabel
		errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// initialize JTextFields:
		// portField
		portField = new JTextField();
		portField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		portField.setColumns(10);
		// nameField
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		nameField.setColumns(10);

		// initialize JButtons:
		// createButton
		createButton = new JButton("Create Game");
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check if all information has been filled in
				if (nameField.getText().equals("") || portField.equals("")) {
					errorLabel.setText("Error! Please fill in all text fields.");
				}
				else if(selectedClass.equals("")) {
					errorLabel.setText("Error! Choose a class.");
				}
				else {
					// get information from text fields
					String name = nameField.getText();
					int port = Integer.parseInt(portField.getText());
					// try to create a new server
					Server newServer = new Server(port);
					frame.dispose();
					
					try {
						Thread.sleep(1500);
						if(newServer.isServerRunning() == true) {
							HostLobby newHostLobby = new HostLobby("127.0.0.1", port, name,selectedClass);
						}
					}catch(Exception f) {
						
					}
				}
			}
		});// end of createButton ActionListener

		// backButton
		backButton = new JButton("Back");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create new menu window and close CreateGame frame
				Menu menuWindow = new Menu();
				frame.dispose();
			}
		});//end of backButton ActionListener

		//selectSniperButton
		selectSniperButton = new JButton("");
		selectSniperButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedClass = "Sniper";
			}
		});//end of selectSniperButton ActionListener
		
		//selectScoutButton
		selectScoutButton = new JButton("");
		selectScoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedClass = "Scout";
			}
		});//end of selectScoutButton ActionListener

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
		});//send of selectSpecialistButton

		// setup GroupLayout
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(288).addComponent(titleLabel))
						.addGroup(groupLayout.createSequentialGroup().addGap(151).addComponent(portLabel)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(portField, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(nameLabel).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(nameField, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(123, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(37).addComponent(backButton).addGap(147)
						.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE).addGap(18)
						.addComponent(createButton).addGap(55))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup().addGap(77)
						.addComponent(selectScoutButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(selectSniperButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(selectRiflemanButton, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(selectSpecialistButton, GroupLayout.PREFERRED_SIZE, 137,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(115, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(titleLabel).addGap(45)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(portLabel)
								.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(nameLabel).addComponent(
										nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(selectSniperButton, GroupLayout.PREFERRED_SIZE, 141,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(selectScoutButton, GroupLayout.PREFERRED_SIZE, 141,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(selectRiflemanButton, GroupLayout.PREFERRED_SIZE, 141,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(selectSpecialistButton, GroupLayout.PREFERRED_SIZE, 141,
										GroupLayout.PREFERRED_SIZE))
						.addGap(83).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(createButton).addComponent(backButton).addComponent(errorLabel))
						.addGap(32)));
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
}
