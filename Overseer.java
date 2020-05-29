import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Overseer {
	/*
	 * The Introduction to the Game 
	 */
		
	//Images to be used
	private ImageIcon cardpic =  new ImageIcon((getClass().getResource("cards.png")));
	private ImageIcon lobby =  new ImageIcon((getClass().getResource("lobby.png")));
	private ImageIcon coin =  new ImageIcon((getClass().getResource("Coin.jpg")));
		
	/*
	 * THis method does the explanations of the game and how the Tens Game works
	 * MalformedURLException is used to avoid any problems/exceptions given by the usage of gifs
	 */
	public void begin() throws MalformedURLException {
		URL url = Overseer.class.getResource("bye.gif");
        Icon icon = new ImageIcon(url);
		Window.msg2("Entering RANK MODE: TENS" 
         		+ "\n Marina and Pearl will get you up to speed.", cardpic);
         Window.msg2("Yo waddup gang, its the other SQUID SISTERS coming to help you! The TENS game involves you choosing"
         		+ "\n pairs that add up to 10, or 4 of 10s, Jacks, Queens, or Kings!"
         		+ "\n Simple and easy, just like fishcake!", icon);
         Window.msg2("You're in for a treat this time. Each play gives you 5 coins! Exchange these coins to play minigames! "
         		+ "\nYour coins will be DISPLAYED on the TOP!"
         		+ "\n***No more POSSIBLE PLAYS and you LOSE***", coin);
	}
	
	/*
	 * Welcomes the player with a gif and then calls the begin method
	 */
	public void welcome() throws MalformedURLException {
		URL url = Overseer.class.getResource("home.gif");
        Icon icon = new ImageIcon(url);
		Window.msg2("Welcome to INKOPOLIS, home of the INKLINGS!", icon);
             begin();
	}
	
	/*
	 * Constructor: Calls the welcome method
	 */
	public Overseer() throws MalformedURLException {
		welcome();
	}
}
