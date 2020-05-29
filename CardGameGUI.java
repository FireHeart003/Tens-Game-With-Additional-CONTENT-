import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class CardGameGUI extends JFrame implements ActionListener {

	/**
	 * Declare and initialize all private instance variables
	 */
	private static final long serialVersionUID = 1L;
	/** Height of the game frame. */
	private static final int DEFAULT_HEIGHT = 400;
	/** Width of the game frame. */
	private static final int DEFAULT_WIDTH = 1000;
	/** Width of a card. */
	private static final int CARD_WIDTH = 120;
	/** Height of a card. */
	private static final int CARD_HEIGHT = 135;
	/** Row (y coord) of the upper left corner of the first card. */
	private static final int LAYOUT_TOP = 30;
	/** Column (x coord) of the upper left corner of the first card. */
	private static final int LAYOUT_LEFT = 30;
	/** Distance between the upper left x coords of
	 *  two horizonally adjacent cards. */
	private static final int LAYOUT_WIDTH_INC = 150;
	/** Distance between the upper left y coords of
	 *  two vertically adjacent cards. */
	private static final int LAYOUT_HEIGHT_INC = 150;
	/** y coord of the "Replace" button. */
	private static final int BUTTON_TOP = 30;
	/** x coord of the "Replace" button. */
	private static final int BUTTON_LEFT = 800;
	/** Distance between the tops of the "Replace" and "Restart" buttons. */
	private static final int BUTTON_HEIGHT_INC = 50;
	/** y coord of the "n undealt cards remain" label. */
	private static final int LABEL_TOP = 160;
	/** x coord of the "n undealt cards remain" label. */
	private static final int LABEL_LEFT = 775;
	/** Distance between the tops of the "n undealt cards" and
	 *  the "You lose/win" labels. */
	private static final int LABEL_HEIGHT_INC = 35;
	/** y coord of the "Replace" button. */
	
	/*
	 * Additional labels:
	 * Elevens Button(Numbers used here will be manipulated to create a Thirteens Button)
	 * DLC LABEL
	 * LOCK LABEL
	 */
	private static final int elevensBUTTON_TOP = 315;
	private static final int elevensBUTTON_LEFT = 800;
	private static final int elevensBUTTON_HEIGHT_INC = 50;
	
	private static final int dlcLABEL_TOP = 160;
	private static final int dlcLABEL_LEFT = 775;
	private static final int dlcLABEL_HEIGHT_INC = 35;
	
	private static final int lockLABEL_TOP = 160;
	private static final int lockLABEL_LEFT = 795;
	private static final int lockLABEL_HEIGHT_INC = 35;
	
	/** The board (Board subclass). */
	private Board board;
	

	//128 coins

	/** The main panel containing the game components. */
	private JPanel panel;
	/** The Replace button. */
	private JButton replaceButton;
	/** The Restart button. */
	private JButton restartButton;
	/** The "number of undealt cards remain" message. */
	private JLabel statusMsg;

	/*
	 * Created a DLC section for this game, requiring additional buttons
	 */
	private JButton elevensButton;	//Elevens button
	private JButton thirteensButton;//thirteens button
	private JLabel dlc;//Message to show that there is DLC content
	private JLabel lock;//The image of the lock next to the elevens button if not unlocked yet
	private JLabel lock2;//The image of the lock next to the thirteens button if not unlocked yet
	
	/** The "you've won n out of m games" message. */
	private JLabel totalsMsg;
	/** The card displays. */
	private JLabel[] displayCards;
	/** The win message. */
	private JLabel winMsg;
	/** The loss message. */
	private JLabel lossMsg;
	/** The coordinates of the card displays. */
	private Point[] cardCoords;
	
	//Images to be used
	private ImageIcon locked =  new ImageIcon((getClass().getResource("lock.png")));
	private ImageIcon cap =  new ImageIcon((getClass().getResource("cap.png")));

	
	/** kth element is true iff the user has selected card #k. */
	private boolean[] selections;
	/** The number of games won. */
	private int totalWins;
	/** The number of games played. */
	private int totalGames;
	
	/*
	 * The menuBar, menu, and menuItems are used for a drop down menu at the top left corner
	 */
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	JMenuItem menuItem2;
	
	/*
	 * The below are used to tell whether or not the player is playing tens or the other DLC content modes such as Elevens and Thirteens
	 */
	static boolean tens;
	//Getter for the boolean tens
	public static boolean getTens() {
		return tens;
	}
	//Setters
	public static void setTensTrue() {
		tens = true;
	}
	public static void setTensFalse() {
		tens = false;
	}

	/*
	 * Coins are used to buy the DLC expansions to the other games
	 * To make sure coins are not reseted, we use setters to set the amount of coins
	 * In each if statement, the getThirteens and getElevens are boolean values from the other classes that tell us whether or not the player was playing that mode
	 * If they were, set it accordingly to the amount of coins that are present during that mode so no coins are lost
	 */
	static int coins;
	
	//Getter
	public static int getCoins() {
		return coins;
	}
	
	//Setter
	public void setCoins() {
			coins = 0;
		if(CardGameGUI13.getThirteens()) {
			coins = CardGameGUI13.getCoins();
			CardGameGUI13.setThirteensFalse();
		}
		else if(CardGameGUI11.getElevens()) {
			coins = CardGameGUI11.getCoins();
			CardGameGUI11.setElevensFalse();
		}
	}

	private static final String[] BUY = {"BOOYAH!", "MEH..."};//options to buy the DLC content

	/*
	 * Prevents player from playing the game at full screen since it will distort images and other proportions of text etc.
	 */
	public void noResize() {
		this.setResizable(false);
	}
	
	/*
	 * Ending scenes before exiting the game
	 */
	public void end() {
		URL url = Overseer.class.getResource("othergame.gif");
        Icon icon = new ImageIcon(url);
		Window.msg2("Your squid kid instincts have overwhelmed you.....You have decided to abandon the GAME."
				+ "\nReturning back to INKOPOLIS and EXITING.", icon);
		URL url2 = Overseer.class.getResource("lobby.gif");
        Icon icon2 = new ImageIcon(url2);
		Window.msg2("Credits: All supported by the power of JOptionPane!"
		
				+ "\n The price of playing this game will be a LEADERBOARD POINT!",icon2);
	}
	/**
	 * Initialize the GUI.
	 * @param gameBoard is a <code>Board</code> subclass.
	 * @throws MalformedURLException 
	 */
	public CardGameGUI(Board gameBoard){
		board = gameBoard;
		totalWins = 0;
		totalGames = 0;
		noResize();
		setTensTrue();//Sets tens true since player is currently playing the game
		setCoins();//Sets the amount of coins
		
		/*
		 * Initializes private instance variables to create the drop down menu on top left corner of the play screen
		 */
		  menuBar = new JMenuBar();
		  menu = new JMenu();
		  
		  //First option in the drop down menu
		  menuItem = new JMenuItem();
		  menuItem.setText("Exit");//Exit button
		  menuItem.addActionListener(new java.awt.event.ActionListener() {
			  public void actionPerformed(java.awt.event.ActionEvent evt) {
				  end();
				  System.exit(0);
			  }
		  });
		  
		  //Second option in the drop down menu
		  menuItem2 = new JMenuItem();
		  menuItem2.setText("Help");//Help button
		  menuItem2.addActionListener(new java.awt.event.ActionListener() {
			  public void actionPerformed(java.awt.event.ActionEvent evt) {
				  Window.msg2("The TENS game rules: "
				  		+ "\n Match pairs that add up to 10 and click 'replace'"
				  		+ "\n4 of 10s, Jacks, Queens, OR Kings can also be eliminated!"
				  		+ "\n Make sure to win by clearing the board and deck!"
				  		+ "\n\n*Pressing RESTART starts a new game!"
				  		+ "\n**Pressing REPLACE replaces selected cards if its a valid play.",cap);
			  }
		  });
		  //Creating the actual drop down menu and naming it "OPTIONS"
		  menu.setText("OPTIONS");
		  menu.add(menuItem);
		  menu.add(menuItem2);
		  menuBar.add(menu);
		  this.setJMenuBar(menuBar);
		  
		// Initialize cardCoords using 5 cards per row
		cardCoords = new Point[board.size()];
		int x = LAYOUT_LEFT;
		int y = LAYOUT_TOP;
		for (int i = 0; i < cardCoords.length; i++) {
			cardCoords[i] = new Point(x, y);
			if (i % 5 == 4) {
				x = LAYOUT_LEFT;
				y += LAYOUT_HEIGHT_INC;
			} else {
				x += LAYOUT_WIDTH_INC;
			}
		}

		selections = new boolean[board.size()];
		initDisplay();//Initializes the display
		setDefaultCloseOperation(EXIT_ON_CLOSE);//Pressing X closes the program/game
		repaint();
	}

	/**
	 * Run the game.
	 */
	public void displayGame() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}

	/**
	 * Draw the display (cards and messages).
	 */
	public void repaint() {
		for (int k = 0; k < board.size(); k++) {
			String cardImageFileName =
				imageFileName(board.cardAt(k), selections[k]);
			URL imageURL = getClass().getResource(cardImageFileName);
			if (imageURL != null) {
				ImageIcon icon = new ImageIcon(imageURL);
				displayCards[k].setIcon(icon);
				displayCards[k].setVisible(true);
			} else {
				throw new RuntimeException(
					"Card image not found: \"" + cardImageFileName + "\"");
			}
		}
		statusMsg.setText(board.deckSize()
			+ " undealt cards remain.");
		statusMsg.setVisible(true);
		totalsMsg.setText("You've won " + totalWins
			 + " out of " + totalGames + " games.");
		totalsMsg.setVisible(true);
		this.setTitle("TENS - Coins: "+getCoins());//Shows the amount of coins you have at the top left corner
		pack();
		panel.repaint();
		
	}

	/**
	 * Initialize the display.
	 */
	private void initDisplay()	{
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};

		// If board object's class name follows the standard format
		// of ...Board or ...board, use the prefix for the JFrame title
		String className = board.getClass().getSimpleName();
		int classNameLen = className.length();
		int boardLen = "Board".length();
		String boardStr = className.substring(classNameLen - boardLen);
		if (boardStr.equals("Board") || boardStr.equals("board")) {
			int titleLength = classNameLen - boardLen;
			setTitle(className.substring(0, titleLength));
		}

		// Calculate number of rows of cards (5 cards per row)
		// and adjust JFrame height if necessary
		int numCardRows = (board.size() + 4) / 5;
		int height = DEFAULT_HEIGHT;
		if (numCardRows > 2) {
			height += (numCardRows - 2) * LAYOUT_HEIGHT_INC;
		}
		
		//Dimensioning the display
		this.setSize(new Dimension(DEFAULT_WIDTH, height));
		panel.setLayout(null);		
		panel.setPreferredSize(
			new Dimension(DEFAULT_WIDTH - 20, height - 20));
		displayCards = new JLabel[board.size()];
		for (int k = 0; k < board.size(); k++) {
			displayCards[k] = new JLabel();
			panel.add(displayCards[k]);
			displayCards[k].setBounds(cardCoords[k].x, cardCoords[k].y,
										CARD_WIDTH, CARD_HEIGHT);
			displayCards[k].addMouseListener(new MyMouseListener());//Adding mouse listener to perform actions when the cards are clicked on
			selections[k] = false;
		}
		
		/*
		 * Setting up the Replace button
		 * Also adding an ActionListner to it
		 */
		replaceButton = new JButton();
		replaceButton.setText("Replace");
		panel.add(replaceButton);
		replaceButton.setBounds(BUTTON_LEFT, BUTTON_TOP, 100, 40);
		replaceButton.addActionListener(this);

		/*
		 * Setting up the Restart button
		 * Also adding an ActionListener to it
		 */
		restartButton = new JButton();
		restartButton.setText("Restart");
		panel.add(restartButton);
		restartButton.setBounds(BUTTON_LEFT, BUTTON_TOP + BUTTON_HEIGHT_INC, 100, 40);
		restartButton.addActionListener(this);
		
		/*
		 * If the Elevens game has not been bought:
		 * Create a button called "11s - LOCK"
		 * 
		 * Otherwise, if the Elevens game was bought:
		 * The button would just be called "Elevens"
		 */
		if(Set.getBoughtElevens() == false) {//Haven't been bought
		elevensButton = new JButton();
		elevensButton.setText("11s - LOCK");
		panel.add(elevensButton);
		elevensButton.setBounds(elevensBUTTON_LEFT, elevensBUTTON_TOP,
										100, 40);
		elevensButton.addActionListener(this);
		}
		else if(Set.getBoughtElevens() == true) {//If already bought Thirteens
			elevensButton = new JButton();
			elevensButton.setText("Elevens");
			panel.add(elevensButton);
			elevensButton.setBounds(elevensBUTTON_LEFT, elevensBUTTON_TOP,
											100, 40);
			elevensButton.addActionListener(this);
		}
	
		/*
		 * The same thing is done with Thirteens
		 * If Thirteens hasn't been bought, display that it's locked - "13s - LOCK"
		 * Otherwise, if bought, display "Thirteens"
		 */
		if(Set.getBoughtThirteens() == false) {//Haven't bought Thirteens
			thirteensButton = new JButton();
			thirteensButton.setText("13s - LOCK");
			panel.add(thirteensButton);
			thirteensButton.setBounds(elevensBUTTON_LEFT, elevensBUTTON_TOP + elevensBUTTON_HEIGHT_INC, 100, 40);
			thirteensButton.addActionListener(this);
		}
		else if(Set.getBoughtThirteens() == true) {//If already bought Thirteens
			thirteensButton = new JButton();
			thirteensButton.setText("Thirteens");
			panel.add(thirteensButton);
			thirteensButton.setBounds(elevensBUTTON_LEFT, elevensBUTTON_TOP + elevensBUTTON_HEIGHT_INC,
											100, 40);
			thirteensButton.addActionListener(this);
		}
		
		//A label that is placed above the Elevens and Thirteens button
		dlc = new JLabel("FRESH DLC!");//Title of the label
		panel.add(dlc);//Adds the label to the panel
		dlc.setBounds(dlcLABEL_LEFT+30, dlcLABEL_TOP+ (LABEL_HEIGHT_INC)*3+15, 250, 30);//Sets the label's position on the panel
		
		/*
		 * These 2 if statements are used to display the lock symbol that appears next to the Elevens and Thirteens button
		 */
		if(Set.getBoughtElevens() == false) {//If the Elevens game has not been purchased yet
			lock = new JLabel(locked);
			panel.add(lock);//display the image of a lock
			lock.setBounds(lockLABEL_LEFT+20, lockLABEL_TOP+ (LABEL_HEIGHT_INC)*3+40, 250, 50);//set lock1's position
		}
		if(Set.getBoughtThirteens() == false) {//If the Thirteens game has not been purchased yet
		lock2 = new JLabel(locked);
		panel.add(lock2);//Display the image of another lock
		lock2.setBounds(lockLABEL_LEFT+20, lockLABEL_TOP+ (LABEL_HEIGHT_INC)*3+90, 250, 50);//Set lock2's position
		}
		
		//Status msg shows how much cards remain in the deck still
		statusMsg = new JLabel(
			board.deckSize() + " undealt cards remain.");
		panel.add(statusMsg);
		statusMsg.setBounds(LABEL_LEFT, LABEL_TOP, 250, 30);

		//Win msg when player beats the game
		winMsg = new JLabel();
		winMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		winMsg.setFont(new Font("SansSerif", Font.BOLD, 25));
		winMsg.setForeground(Color.GREEN);
		winMsg.setText("You win!");
		panel.add(winMsg);
		winMsg.setVisible(false);

		//Loss msg when player loses the game
		lossMsg = new JLabel();
		lossMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		lossMsg.setFont(new Font("SanSerif", Font.BOLD, 25));
		lossMsg.setForeground(Color.RED);
		lossMsg.setText("Sorry, you lose.");
		panel.add(lossMsg);
		lossMsg.setVisible(false);

		//totalsMsg totals up all the games the player won out of the total the player has played
		totalsMsg = new JLabel("You've won " + totalWins
			+ " out of " + totalGames + " games.");
		totalsMsg.setBounds(LABEL_LEFT, LABEL_TOP + 2 * LABEL_HEIGHT_INC, 250, 30);
		panel.add(totalsMsg);
		
		//If player cannot do anything anymore, show the lose message
		if (!board.anotherPlayIsPossible()) {
			signalLoss();
		}

		pack();
		getContentPane().add(panel);
		getRootPane().setDefaultButton(replaceButton);
		panel.setVisible(true);
	}

	//Getter
	public JPanel getPanel() {
		return panel;
	}
	/**
	 * Deal with the user clicking on something other than a button or a card.
	 */
	private void signalError() {
		Toolkit t = panel.getToolkit();
		t.beep();
	}

	/**
	 * Returns the image that corresponds to the input card.
	 * Image names have the format "[Rank][Suit].GIF" or "[Rank][Suit]S.GIF",
	 * for example "aceclubs.GIF" or "8heartsS.GIF". The "S" indicates that
	 * the card is selected.
	 *
	 * @param c Card to get the image for
	 * @param isSelected flag that indicates if the card is selected
	 * @return String representation of the image
	 */
	private String imageFileName(Card c, boolean isSelected) {
		String str = "cards/";
		if (c == null) {
			return "cards/back1.GIF";
		}
		str += c.rank() + c.suit();
		if (isSelected) {
			str += "S";
		}
		str += ".GIF";
		return str;
	}

	/**
	 * Respond to a button click (on either the "Replace" button
	 * or the "Restart" button).
	 * @param e the button click action event
	 */
	public void actionPerformed(ActionEvent e) {
		//Replaces the two cards if it is a possible play
		if (e.getSource().equals(replaceButton)) {
			// Gather all the selected cards.
			List<Integer> selection = new ArrayList<Integer>();
			for (int k = 0; k < board.size(); k++) {
				if (selections[k]) {
					selection.add(new Integer(k));
				}
			}
			// Make sure that the selected cards represent a legal replacement.
			if (!board.isLegal(selection)) {
				signalError();
				return;
			}
			for (int k = 0; k < board.size(); k++) {
				selections[k] = false;
			}
			// Do the replace.
			board.replaceSelectedCards(selection);
			if (board.isEmpty()) {
				signalWin();
			} else if (!board.anotherPlayIsPossible()) {
				signalLoss();
			}
			coins+=5;//Adds 5 coins when replacing a pair of cards
			repaint();
		} 
		
		//Restarts the game
		else if (e.getSource().equals(restartButton)) {
			board.newGame();
			getRootPane().setDefaultButton(replaceButton);
			winMsg.setVisible(false);
			lossMsg.setVisible(false);
			totalGames++;//Increment the number of games played
			if (!board.anotherPlayIsPossible()) {
				signalLoss();
				lossMsg.setVisible(true);
			}
			for (int i = 0; i < selections.length; i++) {
				selections[i] = false;
			}
			repaint();
		} 
		
		//If the elevensButton is clicked
		else if (e.getSource().equals(elevensButton)) {
			if(Set.getBoughtElevens() == false) {//If the player has not bought the Elevens game yet
				int x = Window.option(BUY,"Would you like to exchange 30 coins for ELEVENS?");
				if(x==1) {//If they say they do not want to exchange coins
					Window.msg3("Stay Fresh Squid!");
				}
				else if(x==0) {//If they do:
					if(coins >= 30) {//If they have equal to or more than 30 coins
					Set.setBoughtElevens();//Set elevens to true so that 
					coins -= 30;//decrease number of coins
					board.newGame();//start the transition to the Elevens game
					URL url = Overseer.class.getResource("othergame.gif");
			        Icon icon = new ImageIcon(url);
					Window.msg2("Good catch mate! Starting gears up for ELEVENS!", icon);
					URL url2 = Overseer.class.getResource("loda.gif");
			        Icon icon2 = new ImageIcon(url2);
					Window.msg2("LOADING ELEVENS game!", icon2);
			        getRootPane().setDefaultButton(elevensButton);
					this.dispose();//Dispose the Tens JFrame
					repaint(); 
					TensGUIRunner.elevens();//Starts Elevens Game
					}
					else {//If they do not have enough coins
						Window.msg3("Were you trying to squid your way out?!?" + 
								"\nYou don't have the authorization to do so...");
					}
				}
			}
			
			/*
			 * If the player has already bought Elevens and clicked on the Elevens button
			 * Dispose the current game and start the Elevens Game
			 */
			else if(Set.getBoughtElevens()) {
			board.newGame();
			getRootPane().setDefaultButton(elevensButton);
			URL url = Overseer.class.getResource("super jump.gif");
	        Icon icon = new ImageIcon(url);
			Window.msg2("SUPER JUMPING towards ELEVENS game!", icon);
			this.dispose();
			repaint(); 
			TensGUIRunner.elevens();
				}
			}
		
		//If the thirteensButton is clicked
		else if (e.getSource().equals(thirteensButton)) {
			if(Set.getBoughtThirteens() == false) {//If the player has not bought the Thirteens game yet
				int x = Window.option(BUY,"Would you like to exchange 40 coins for THIRTEENS?");
				if(x==1) {//If they say they do not want to exchange coins
					Window.msg3("Stay Fresh Squid!");
				}
				else if(x==0) {//If they do:
					if(coins >= 40) {//If they have equal to or more than 40 coins
						Set.setBoughtThirteens();
						coins -= 40;//Decrease number of coins
						board.newGame();//start the transition to the Elevens game
						getRootPane().setDefaultButton(thirteensButton);
						URL url = Overseer.class.getResource("othergame.gif");
				        Icon icon = new ImageIcon(url);
						Window.msg2("Good catch mate! Starting gears up for THIRTEENS!", icon);
						URL url2 = Overseer.class.getResource("loda.gif");
				        Icon icon2 = new ImageIcon(url2);
						Window.msg2("LOADING THIRTEENS game!", icon2);
						this.dispose();//Dispose the Tens JFrame
						repaint(); 
						TensGUIRunner.thirteens();//Start the Thirteens game
					}
					else {//If they do not have enough coins
						Window.msg3("Were you trying to squid your way out?!?"
								+ "\n You don't have the authorization to do so...");
					}
				}
			}
			
			/*
			 * If the player has already bought Thirteens and clicked on the Thirteens button
			 * Dispose the current game and start the Thirteens Game
			 */
			else if(Set.getBoughtThirteens()) {
				board.newGame();
				getRootPane().setDefaultButton(thirteensButton);
				URL url = Overseer.class.getResource("super jump.gif");
		        Icon icon = new ImageIcon(url);
				Window.msg2("SUPER JUMPING towards THIRTEENS game!", icon);
				this.dispose();
				repaint(); 
				TensGUIRunner.thirteens();
				}
		}
		else {
			signalError();
			return;
		}
	}

	/**
	 * Display a win.
	 */
	private void signalWin() {
		getRootPane().setDefaultButton(restartButton);
		winMsg.setVisible(true);
		totalWins++;
		totalGames++;
	}

	/**
	 * Display a loss.
	 */
	private void signalLoss() {
		getRootPane().setDefaultButton(restartButton);
		lossMsg.setVisible(true);
		totalGames++;
	}

	/**
	 * Receives and handles mouse clicks.  Other mouse events are ignored.
	 */
	private class MyMouseListener implements MouseListener {

		/**
		 * Handle a mouse click on a card by toggling its "selected" property.
		 * Each card is represented as a label.
		 * @param e the mouse event.
		 */
		public void mouseClicked(MouseEvent e) {
			for (int k = 0; k < board.size(); k++) {
				if (e.getSource().equals(displayCards[k])
						&& board.cardAt(k) != null) {
					selections[k] = !selections[k];
					repaint();
					return;
				}
			}
			signalError();
		}

		/**
		 * Ignore a mouse exited event.
		 * @param e the mouse event.
		 */
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * Ignore a mouse released event.
		 * @param e the mouse event.
		 */
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * Ignore a mouse entered event.
		 * @param e the mouse event.
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * Ignore a mouse pressed event.
		 * @param e the mouse event.
		 */
		public void mousePressed(MouseEvent e) {
		}
	}
}
