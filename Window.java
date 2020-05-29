	import javax.swing.Icon;
	import javax.swing.ImageIcon;
	import javax.swing.JFrame;
	import javax.swing.JOptionPane;
	public class Window {
		/*
		 * A class to easily use JOptionPanes to display messages
		 */
		
		private static JFrame j;
		public Window() {
			j = new JFrame();
		}

		//Displays a JOptionPane with a message
		public static void msg3(String msg) {
			JOptionPane.showMessageDialog(null, msg);
		}

		//Displays a message and options for user to choose from
		public static int option(String[] options, String msg) {
			return JOptionPane.showOptionDialog(
					j, 
					msg, // my message
	                "Click a button", // dialog box title
	                JOptionPane.DEFAULT_OPTION, 
	                JOptionPane.INFORMATION_MESSAGE, 
	                null, 
	                options, // possible options
	                options[0]); // default option
			
		}
		
		//Displays a message, options for user to choose from, and an Image to go along with it
		public static int option1(String[] options, String msg, Icon i) {
	        return JOptionPane.showOptionDialog(
	                j, 
	                msg, // my message
	                null, // dialog box title
	                JOptionPane.DEFAULT_OPTION, 
	                JOptionPane.PLAIN_MESSAGE, 
	                i, 
	                options, // possible options
	                options[0]); // default option
	    }
		
		//Displays a message with an icon
		public static void msg2(String msg, Icon i){
	        JOptionPane.showMessageDialog(
	                j, 
	                msg, 
	                null, 
	                JOptionPane.DEFAULT_OPTION, 
	                i); 
	    }
	}


