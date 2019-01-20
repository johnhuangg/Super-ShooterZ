/**
 * Menu
 * GUI program from main menu
 * @Author Andrew
 * 
 */

//imports:
//swing
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
//awt 
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {
	//Global Declarations:
	//JFrame
	private JFrame frame;
	private ImageIcon bgImage = new ImageIcon("titleScreen.png");
	
	
	//JLabels
	private JLabel TitleLabel;
	
	//JButton
	private JButton createGameButton;
	private JButton joinGameButton;
	
	//GroupLayout
	private GroupLayout groupLayout;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); 
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//initialize frame
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//intialize labels:
		//TitleLabel
		TitleLabel = new JLabel("Super Smash Arena");
		TitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		
		//initialize buttons:
		//createGameButon
		createGameButton = new JButton("Create Game");
		createGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		createGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGame createGameWindow = new CreateGame();
				frame.dispose();
			}
		});
		
		//joinGameButton
		joinGameButton = new JButton("Join Game");
		joinGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		joinGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JoinGame joinGameWindow = new JoinGame();
				frame.dispose();
			}
		});
		
		//setup GroupLayout
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(220)
							.addComponent(TitleLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(270)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(joinGameButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(createGameButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))))
					.addContainerGap(246, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(101)
					.addComponent(TitleLabel)
					.addGap(80)
					.addComponent(createGameButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addComponent(joinGameButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(190, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
}
