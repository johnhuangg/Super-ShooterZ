/**
* This template can be used as reference or a starting point
* for your final summative project
* @author Mangat
**/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class BackgroundFrame extends JFrame {

	// class variable (non-static)
	private BufferedImage image;
	// int trueX = 0;
	// int trueY = 0;
	int bgX = 0;
	int bgY = 0;
	int movingPlatform1Velocity = -1;
	int movingPlatform2Velocity = 1;
	int movingUpperBound = 1150;
	int movingLowerBound = 200;

	Platform[] platforms = new Platform[24]; // array to store all the platforms
	int[][] platformData = { { 0, 100, 125, 100 }, { 2435, 100, 125, 100 }, { 0, 1375, 125, 100 },
			{ 2435, 1375, 125, 100 }, // spawn platforms (4)
			{ 300, 100, 125, 100 }, { 2150, 100, 125, 100 }, { 780, 100, 1000, 100 }, { 1900, 100, 125, 100 },
			{ 550, 100, 125, 100 }, // upper platforms (5)
			{ 450, 1375, 600, 100 }, { 1175, 1375, 250, 100 }, { 1550, 1375, 600, 100 }, { 1250, 1275, 100, 100 },
			{ 950, 1275, 100, 100 }, { 1550, 1275, 100, 100 }, // lower platforms & guards (6)
			{ 400, 1000, 1800, 100 }, { 450, 625, 300, 100 }, { 900, 625, 300, 100 }, { 1350, 625, 300, 100 },
			{ 1800, 625, 300, 100 }, { 600, 525, 100, 100 }, { 1800, 525, 100, 100 }, // middle platforms & guards (7)
			{ 150, 1150, 125, 100 }, { 2300, 200, 135, 100 } }; // moving platforms (2)

	int[][] platformDataCopy = { { 0, 100, 125, 100 }, { 2435, 100, 125, 100 }, { 0, 1375, 125, 100 },
			{ 2435, 1375, 125, 100 }, // spawn platforms (4)
			{ 300, 100, 125, 100 }, { 2150, 100, 125, 100 }, { 780, 100, 1000, 100 }, { 1900, 100, 125, 100 },
			{ 550, 100, 125, 100 }, // upper platforms (5)
			{ 450, 1375, 600, 100 }, { 1175, 1375, 250, 100 }, { 1550, 1375, 600, 100 }, { 1250, 1275, 100, 100 },
			{ 950, 1275, 100, 100 }, { 1550, 1275, 100, 100 }, // lower platforms & guards (6)
			{ 400, 1000, 1800, 100 }, { 450, 625, 300, 100 }, { 900, 625, 300, 100 }, { 1350, 625, 300, 100 },
			{ 1800, 625, 300, 100 }, { 600, 525, 100, 100 }, { 1800, 525, 100, 100 }, // middle platforms & guards (7)
			{ 150, 1150, 125, 100 }, { 2300, 200, 135, 100 } }; // moving platforms (2)
	// copy array of the platform data to store original coordinates (platformData
	// changes/updates with movement)

	// Constructor - this runs first
	BackgroundFrame() {
		try { // loads background image
			image = ImageIO.read(new File("background2.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		makePlatforms();
	} // End of Constructor

	// the main gameloop - this is where the game state is updated
	public void update(int x, int y) {

		bgX = x;
		bgY = y;

		if (bgX > 0) {
			bgX = 0;
		}
		if (bgY > 0) {
			bgY = 0;
		}
		if (bgX < -1280) {
			bgX = -1280;
		}
		if (bgY < -800) {
			bgY = -800;
		}

		for (int i = 0; i < platforms.length; i++) { // prevents platforms from moving off the screen

			if (platformData[i][0] > platformDataCopy[i][0]) { // left boundary
				platformData[i][0] = platformDataCopy[i][0];
			}
			if (platformData[i][0] < (platformDataCopy[i][0] - 1280)) { // right boundary
				platformData[i][0] = platformDataCopy[i][0] - 1280;
			}

			if (i >= 22) { // moving platforms
				if (platformData[i][1] > movingUpperBound) { // upper boundary
					platformData[i][1] = movingUpperBound;
				}

				if (platformData[i][1] < movingLowerBound - 800) { // lower boundary
					platformData[i][1] = movingLowerBound - 800;
				}
			} else { // static platforms

				if (platformData[i][1] > platformDataCopy[i][1]) { // upper boundary
					platformData[i][1] = platformDataCopy[i][1];
				}
				if (platformData[i][1] < (platformDataCopy[i][1] - 800)) { // lower boundary
					platformData[i][1] = platformDataCopy[i][1] - 800;
				}
			}
		}

		for (int i = 0; i < platforms.length; i++) { // used to move platforms with the screen
			platformData[i][0] = x;
			platformData[i][1] = y;
			platforms[22].updateCoord((platformData[22][1]) - bgY);
			platforms[23].updateCoord((platformData[23][1]) - bgY);
		}

		if (platformData[22][1] >= movingUpperBound) {
			movingPlatform1Velocity = -1;
		}
		if (platformData[22][1] <= movingLowerBound) {
			movingPlatform1Velocity = 1;
		}

		if (platformData[23][1] >= movingUpperBound) {

			movingPlatform2Velocity = -1;
		}
		if (platformData[23][1] <= movingLowerBound) {
			movingPlatform2Velocity = 1;
		}

		if (movingUpperBound > 1150) {
			movingUpperBound = 1150;
		}
		if (movingLowerBound > 200) {
			movingLowerBound = 200;
		}
		if (movingUpperBound < 350) {
			movingUpperBound = 350;
		}
		if (movingLowerBound < -600) {
			movingLowerBound = -600;
		}

		platformData[22][1] += movingPlatform1Velocity;
		platformData[23][1] += movingPlatform2Velocity;

		try {
			Thread.sleep(1);
		} catch (Exception e) {
		}
	}

	public Platform[] getplatforms(){
		return this.platforms;
	}
	public void makePlatforms() { // constructs platforms

		for (int i = 0; i < platforms.length; i++) {
			platforms[i] = new Platform(platformData[i][0], platformData[i][1], platformData[i][2], platformData[i][3]);
		}
	}

	public void draw(Graphics g) {
		g.drawImage(image, bgX, bgY, 2560, 1600, null);
		for (int i = 0; i < platforms.length; i++) {
			g.drawImage(platforms[i].getImage(), platformData[i][0], platformData[i][1], platformData[i][2],
					platformData[i][3], null);
		}
	}
}