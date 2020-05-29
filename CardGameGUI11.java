import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class CardGameGUI11 extends JFrame implements ActionListener {

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
	
	private Board board;
	
	/*
	 * The below are used to tell whether or not the player is playing Elevens
	 */
	static boolean elevens;
	
	//Getter for the boolean elevens
	public static boolean getElevens() {
		return elevens;
	}
	
	//Setters
	public static void setElevensTrue() {
		elevens = true;
	}
	public static void setElevensFalse() {
		elevens = false;
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
	 if(CardGameGUI13.getThirteens()) {
			coins = CardGameGUI13.getCoins();
			CardGameGUI13.setThirteensFalse();
		}
		else if(CardGameGUI.getTens()) {
			coins = CardGameGUI.getCoins();
			CardGameGUI.setTensFalse();
		}
	}
	//Image to be used
	private ImageIcon cap =  new ImageIcon((getClass().getResource("cap.png")));

	/** The main panel containing the game components. */
	private JPanel panel;
	/** The Replace button. */
	private JButton replaceButton;
	/** The Restart button. */
	private JButton restartButton;
	/** The "number of undealt cards remain" message. */
	
	private JButton thirteensButton;//Thirteens button

	private JLabel statusMsg;
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

	JMenuBar menuBar2;
	JMenu menu2;
	JMenuItem menuItem3;
	JMenuItem menuItem4;
	/**
	 * Initialize the GUI.
	 * @param gameBoard is a <code>Board</code> subclass.
	 */
	private static final String[] BUY = {"BOOYAH!", "MEH..."};//Options for player to choose from

	public void disposeThis() {//Dispose the JFrame
		this.dispose();
	}
	
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
	public CardGameGUI11(Board gameBoard) {
		board = gameBoard;
		totalWins = 0;
		totalGames = 0;
		noResize();
		setElevensTrue();//Sets Elevens true since player is currently playing the game
		setCoins();//Sets the amount of coins
		
		/*
		 * Initializes private instance variables to create the drop down menu on top left corner of the play screen
		 */
		  menuBar = new JMenuBar();
		  menu = new JMenu();
		  
		  //First option in the drop down menu
		  menuItem = new JMenuItem();
		  menuItem.setText("Exit");//EXIT Button
		  menuItem.addActionListener(new java.awt.event.ActionListener() {
			  public void actionPerformed(java.awt.event.ActionEvent evt) {
				  end();
				  System.exit(0);
			  }
		  });
		  
		  //Second option in the drop down menu
		  menuItem2 = new JMenuItem();
		  menuItem2.setText("Help");//HELP Button
		  menuItem2.addActionListener(new java.awt.event.ActionListener() {
			  public void actionPerformed(java.awt.event.ActionEvent evt) {
				  Window.msg2("The ELEVENS game rules: "
				  		+ "\n Match pairs that add up to 11 and click 'replace'"
				  		+ "\nA JQK triple can also be eliminated!"
				  		+ "\n Make sure to win by clearing the board and deck!"
				  		+ "\n\n*Pressing RESTART starts a new game!"
				  		+ "\n**Pressing REPLACE replaces selected cards if its a valid play.", cap);
			  }
		  });
		  menu.setText("OPTIONS");
		  menu.add(menuItem);
		  menu.add(menuItem2);
		  menuBar.add(menu);

		  //Create a Second drop down menu
		  menuBar2 = new JMenuBar();
		  menu2 = new JMenu();
		  		  
		  /*
		   * First option in the 2nd drop down menu:
		   * Since the game starts with Tens, the player can automatically go back to it
		   * Player does not need to purchase it
		   */
		  menuItem3 = new JMenuItem();
		  menuItem3.setText("TENS Game");//Tens game
		  menuItem3.addActionListener(new java.awt.event.ActionListener() {
			  public void actionPerformed(java.awt.event.ActionEvent evt) {
				  URL url = Overseer.class.getResource("super jump.gif");
			        Icon icon = new ImageIcon(url);
					Window.msg2("SUPER JUMPING towards TENS game!", icon);
				  disposeThis();//Dispose Elevens game
				  TensGUIRunner.tens();//Starts the Tens Game
			  }
		  });
		  
		  //Second option in the drop down menu
		  menuItem4 = new JMenuItem();
		  menuItem4.setText("THIRTEENS Game");//Thirteens Game 
		  menuItem4.addActionListener(new java.awt.event.ActionListener() {
			  public void actionPerformed(java.awt.event.ActionEvent evt) {//If this button is clicked
				  if(Set.getBoughtThirteens() == false) {//If the player has not bought the Thirteens game yet
						int x = Window.option(BUY,"Would you like to exchange 40 coins for THIRTEENS?");
					if(x==1) {//If they say they do not want to exchange coins
						Window.msg3("Stay Fresh Squid!");
					}
					else if(x==0) {//If they do:
						if(coins >= 40) {//If they have equal to or more than 40 coins
							Set.setBoughtThirteens();
							coins -= 40;//Decrease number of coins
							board.newGame();//start the transition to the Thirteens game
							getRootPane().setDefaultButton(thirteensButton);
							URL url = Overseer.class.getResource("othergame.gif");
					        Icon icon = new ImageIcon(url);
							Window.msg2("Good catch mate! Starting gears up for THIRTEENS!", icon);
							URL url2 = Overseer.class.getResource("loda.gif");
					        Icon icon2 = new ImageIcon(url2);
							Window.msg2("LOADING THIRTEENS game!", icon2);
							disposeThis();//Dispose the Elevens game JFrame
							repaint(); 
							TensGUIRunner.thirteens();//Starts the Thirteens game
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
						disposeThis();
						repaint(); 
						TensGUIRunner.thirteens();
						}
				}
		  });
		  
		  menu2.setText("DLC");//Sets the 2nd drop down menu's name as DLC
		  menu2.add(menuItem3);//Adds the Tens button to DLC
		  menu2.add(menuItem4);//Adds the Thirteens button to DLC
		  menuBar.add(menu2);//Adds the whole drop down menu to the menuBar
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
		initDisplay();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		this.setTitle("ELEVENS - Coins: "+ coins);
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
			displayCards[k].addMouseListener(new MyMouseListener());
			selections[k] = false;
		}
		
		/*
		 * Setting up the Replace button
		 * Also adding an ActionListner to it
		 */
		replaceButton = new JButton();
		replaceButton.setText("Replace");
		panel.add(replaceButton);
		replaceButton.setBounds(BUTTON_LEFT, BUTTON_TOP, 100, 30);
		replaceButton.addActionListener(this);

		/*
		 * Setting up the Restart button
		 * Also adding an ActionListener to it
		 */
		restartButton = new JButton();
		restartButton.setText("Restart");
		panel.add(restartButton);
		restartButton.setBounds(BUTTON_LEFT, BUTTON_TOP + BUTTON_HEIGHT_INC,
										100, 30);
		restartButton.addActionListener(this);

		//Status msg shows how much cards still remain in the deck
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
		totalsMsg.setBounds(LABEL_LEFT, LABEL_TOP + 2 * LABEL_HEIGHT_INC,
								  250, 30);
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
