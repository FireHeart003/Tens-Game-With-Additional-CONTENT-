
public class Set {
	/*
	 * A class used to keep track of the DLC content and whether the player has bought any of them or not
	 */
	
	static boolean boughtElevens = false;//At the start, the player hasn't purchased the Elevens game yet so its false
	static boolean boughtThirteens = false;//At the start, the player hasn't purchased the Thirteens game yet so its false
	
	//Getter to get whether the player has bought Elevens or not
	public static boolean getBoughtElevens() {
		return boughtElevens;
	}

	//Getter to get whether the player has bought Thirteens or not
	public static boolean getBoughtThirteens() {
		return boughtThirteens;
	}
	
	/*
	 * Setters which are used when the player buys one of the DLC content
	 */
	public static void setBoughtElevens() {
		boughtElevens = true;
	}
	public static void setBoughtThirteens() {
		boughtThirteens = true;
	}
}
