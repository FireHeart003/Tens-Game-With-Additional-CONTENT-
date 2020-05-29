import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TensGUIRunner {
	/*
	 * The main class where the game starts
	 */
	
	//Main method to be run
	public static void main(String[] args) throws  MalformedURLException {
		Overseer o = new Overseer();//Create the Overseer object, which serves as an introduction to the game
        Board board = new TensBoard();//Create the tens board
        CardGameGUI gui = new CardGameGUI(board);//Creates/implements the GUI of the board
        gui.displayGame();//Display the game
    }
	
	/*
	 * The Tens method that starts the Tens game
	 * This is used when the player wants to play this game when there current game in play is Elevens or Thirteens
	 */
	public static void tens(){
		Board board = new TensBoard();
        CardGameGUI gui = new CardGameGUI(board);
        gui.displayGame();
	}
	
	/*
	 * The Elevens method that starts the Elevens game
	 * This is used when the player wants to play this game when there current game in play is Tens or Thirteens
	 */
	public static void elevens() {
		Board board = new ElevensBoard();
        CardGameGUI11 gui = new CardGameGUI11(board);
        gui.displayGame();
	}
	
	/*
	 * The Thirteens method that starts the Thirteens game
	 * This is used when the player wants to play this game when there current game in play is Tens or Thirteens
	 */
	public static void thirteens() {
		Board board = new ThirteensBoard();
        CardGameGUI13 gui = new CardGameGUI13(board);
        gui.displayGame();
	}
}
