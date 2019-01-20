/**
 * 
 * 
 * @author 
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


class EndFrame extends JFrame {
  
  
  //Constructor - this runs first
  EndFrame(Player[] players, int winner)throws IOException {  
    super("My Game");
	String[] names = new String[players.length];
	for(int i = 0;i < names.length;i++) {
		names[i] = players[i].getName();
	}
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    
    //main panel
    JPanel endPanel = new JPanel();
    endPanel.setLayout(null);
    endPanel.setBackground(new Color(215,255,255));
    
    //button
    JButton retButton = new JButton("MENU");
    retButton.addActionListener(new RetButtonListener());
    
    //player labels
    JLabel[] playerLabels = new JLabel[players.length];
    for(int i=0; i<players.length; i++){ 
      playerLabels[i] = new JLabel("<html><div style='text-align: center;'><h1 style=\"color:#B22222;font-size: 150%;\">"+names[i]+"</h1></div></html>\"</div></html>");
    }
    playerLabels[winner] = new JLabel("<html><div style='text-align: center;'><h1 style=\"color:#008000;font-size: 200%;\">"+names[winner]+"</h1></div></html>\"</div></html>");
    
    //winner label
    JLabel winnerWord1 = new JLabel("<html><div style='text-align: right;'><h1 style=\"color:#8B008B;font-size: 175%;\">WINNER</h1></div></html>");
    JLabel winnerWord2 = new JLabel("<html><div style='text-align: left;'><h1 style=\"color:#8B008B;font-size: 175%;\">WINNER</h1></div></html>");
    
    //construct the frame
    endPanel.add(winnerWord1);
    endPanel.add(winnerWord2);
    for(int i = 0;i < players.length;i++) {
    	endPanel.add(playerLabels[i]);
    }
    endPanel.add(retButton);
    
    winnerWord1.setLocation((int)(0.2*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    winnerWord1.setSize((int)(0.2*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    
    winnerWord2.setLocation((int)(0.6*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    winnerWord2.setSize((int)(0.2*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    
    playerLabels[winner].setLocation((int)(0.4*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    playerLabels[winner].setSize((int)(0.2*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    
    int j = 0;
    for(int i=0;i<players.length;i++){
      if(i!=winner){
        playerLabels[i].setLocation((int)((0.1*this.getWidth())+j*(0.3*this.getWidth())),(int)(3.0/7.0 *this.getHeight()));
        playerLabels[i].setSize((int)(0.2*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
        System.out.println(i);
        j++;
      }
    }
    
    retButton.setLocation((int)(0.7*this.getWidth()),(int)(5.0/7.0 *this.getHeight()));
    retButton.setSize((int)(0.2*this.getWidth()),(int)(1.0/7.0 *this.getHeight()));
    
    this.add(endPanel);
    
    //Set up the game panel (where we put our graphics)
    this.requestFocusInWindow(); //make sure the frame has focus   
    this.setVisible(true);
    
    
  } //End of Constructor
  
  //Inner class that is used to detect a button press
  class RetButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {  
      
      //button does stuff here
      
      Menu menu = new Menu();
      setVisible(false);
      dispose();
    }
  }
  
}