package FinalProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BallGame extends JPanel implements KeyListener, ActionListener  {

	private boolean play = false;
	private int score = 0;

	private int totalBricks = 21;

	// set speed of the game, so like, an action happens every 8 milliseconds
	private Timer timer;
	private int delay = 8;

	private int playerX = 310;

	// set initial ball position
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private MapGenerator map;

	public BallGame() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		// background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);

		// image background painted AFTER black background
		final ImageIcon icon = new ImageIcon("cherryblossom.gif");
		Image img = icon.getImage();
		//		g.drawImage(img, 30, 20, this);
		g.drawImage(img, 0, 80, this);

		// drawing map
		map.draw((Graphics2D)g);

		// borders
		g.setColor(Color.GRAY);
		g.fillRect(0, 0,  3, 592);
		g.fillRect(0, 0,  692, 3);
		g.fillRect(691, 0,  3, 592);

		// scores
		g.setColor(Color.PINK);
		g.setFont(new Font("serf", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);

		// the paddle
		g.setColor(Color.PINK);
		g.fillRect(playerX, 550, 100, 8);

		// the ball
		g.setColor(Color.GREEN);
		g.fillOval(ballposX, ballposY, 20, 20);

		// when all bricks go bye-bye
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.BLACK);
			g.setFont(new Font("serf", Font.BOLD, 40));
			g.drawString("Congratulations, You Rock!!!", 70, 300);

			g.setFont(new Font("serf", Font.BOLD, 30));
			g.drawString("Score: "+score, 300, 340);

			g.setFont(new Font("serf", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 240, 370);
		}

		// if the ball goes below the paddle and ask user to restart
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.BLACK);
			g.setFont(new Font("serf", Font.BOLD, 40));
			g.drawString("Game Over!!! You Suck.", 140, 300);

			g.setFont(new Font("serf", Font.BOLD, 30));
			g.drawString("Score: "+score, 300, 340);

			g.setFont(new Font("serf", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 240, 370);
		}

		g.dispose();
	}

	// if ball touches rectangle, remove rectangle from totalBricks
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {

			// when the ball collides with a brick, reverse the y values
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}

			A: for(int i = 0; i <map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickwidth + 80;
						int brickY = i * map.brickheight + 50;
						int brickwidth = map.brickwidth;
						int brickheight = map.brickheight;

						Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;

						// when the ball collides with another brick, add +5 to the score and remove it from the
						// total amount of bricks
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,  i ,  j);
							totalBricks --;
							score +=5;

							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}

							break A;
						}
					}
				}
			}

			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}

		repaint();
	}

	// key inputs and parameters
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	// only allows player to move within the bounds
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <10 ) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}

		// pressing enter will restart the game to the default positions, but will autoplay
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);

				repaint();
			}
		}
	}

	// sets the x and y values for the inputs
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
}