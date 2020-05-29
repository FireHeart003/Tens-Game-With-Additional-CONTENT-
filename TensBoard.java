import java.util.List;

public class TensBoard extends Board {
		/**
		 * The size (number of cards) on the board.
		 */
		private static final int BOARD_SIZE = 13;

		/**
		 * The ranks of the cards for this game to be sent to the deck.
		 */
		private static final String[] RANKS =
			{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

		/**
		 * The suits of the cards for this game to be sent to the deck.
		 */
		private static final String[] SUITS =
			{"spades", "hearts", "diamonds", "clubs"};

		/**
		 * The values of the cards for this game to be sent to the deck.
		 */
		private static final int[] POINT_VALUES =
			{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

		/**
		 * Flag used to control debugging print statements.
		 */
		private static final boolean I_AM_DEBUGGING = false;


		/**
		 * Creates a new <code>ElevensBoard</code> instance.
		 */
		 public TensBoard() {
		 	super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
		 }

		/**
		 * Determines if the selected cards form a valid group for removal.
		 * In Elevens, the legal groups are (1) a pair of non-face cards
		 * whose values add to 11, and (2) a group of three cards consisting of
		 * a jack, a queen, and a king in some order.
		 * @param selectedCards the list of the indices of the selected cards.
		 * @return true if the selected cards form a valid group for removal;
		 *         false otherwise.
		 */
		@Override
		public boolean isLegal(List<Integer> selectedCards) {
			if (selectedCards.size() == 2) {
				return containsPairSum10(selectedCards);
			} else if (selectedCards.size() == 4) {
				return contains4King(selectedCards)
						|| contains4Queen(selectedCards) || contains4Ten(selectedCards) || contains4Jack(selectedCards);
			} else {
				return false;
			}
		}

		/**
		 * Determine if there are any legal plays left on the board.
		 * In Elevens, there is a legal play if the board contains
		 * (1) a pair of non-face cards whose values add to 11, or (2) a group
		 * of three cards consisting of a jack, a queen, and a king in some order.
		 * @return true if there is a legal play left on the board;
		 *         false otherwise.
		 */
		@Override
		public boolean anotherPlayIsPossible() {
			List<Integer> cIndexes = cardIndexes();
		//	System.out.println("=======================");
			//System.out.println("10"+ containsPairSum10(cIndexes));
			//System.out.println("k"+ contains4King(cIndexes));
			//System.out.println("Q" + contains4Queen(cIndexes));
			//System.out.println("10" + contains4Ten(cIndexes));
			//System.out.println("J" + contains4Jack(cIndexes));
			//System.out.println("======================");
			return containsPairSum10(cIndexes) || contains4King(cIndexes)
					|| contains4Queen(cIndexes) || contains4Ten(cIndexes) || contains4Jack(cIndexes);
		}
		
		/*
		 * Checks if the two cards that the player has chosen has a sum of 10 and returns true if it is and false if it's not 10
		 */
		 private boolean containsPairSum10(List<Integer> selectedCards) {
			 if(selectedCards.size()< 2){//only pairs that sum up to 11 count
					return false;//returns false since pair is 2 cards
				}
				//only a one time comparison
				for(int i = 0; i<selectedCards.size()-1;i++){//basically runs once since size is two cards minus one is one
					for(int j = i+1; j<selectedCards.size();j++){//second for loop to compare the second card with he first one
						if(cardAt(selectedCards.get(i)).pointValue() + cardAt(selectedCards.get(j)).pointValue() == 10) {
							//gets the card using cardAt at the index i and gets the pointValue of that card and adds it with
							//the pointValue of the card using cardAt at index j to see if it adds up to 11
							//System.out.println(cardAt(selectedCards.get(i)));
							//System.out.println(cardAt(selectedCards.get(j)));
							return true;//returns true if sums up to 11
						}
					}
				}
				return false;//otherwise return false
		    }

		 /*
		  * Checks if the selectedCards the player chose are 4 Kings
		  * If so, return true and if it is not 4 kings, return false
		  */
		 private boolean contains4King(List<Integer> selectedCards) {
		     int king = 0;  
			 for (Integer kObj : selectedCards) {
		            int k = kObj.intValue();
		            if (cardAt(k).rank().equals("king")) {
		             king++;
		            }
		        }
		        if(king ==4) {
		        	return true;
		        }
		        else {
		        	return false;
		        }
		    }
		 
		 /*
		  * Checks if the selectedCards the player chose are 4 Jacks
		  * If so, return true and if it is not 4 Jacks, return false
		  */
		 private boolean contains4Jack(List<Integer> selectedCards) {
		     int jack = 0;  
			 for (Integer kObj : selectedCards) {
		            int k = kObj.intValue();
		            if (cardAt(k).rank().equals("jack")) {
		             jack++;
		            }
		        }
		        if(jack ==4) {
		        	return true;
		        }
		        else {
		        	return false;
		        }
		    }
		 
		 /*
		  * Checks if the selectedCards the player chose are 4 Tens
		  * If so, return true and if it is not 4 Tens, return false
		  */
		 private boolean contains4Ten(List<Integer> selectedCards) {
		     int ten = 0;  
			 for (Integer kObj : selectedCards) {
		            int k = kObj.intValue();
		            if (cardAt(k).rank().equals("10")) {
		             ten++;
		            }
		        }
		        if(ten ==4) {
		        	return true;
		        }
		        else {
		        	return false;
		        }
		    }
		 
		 /*
		  * Checks if the selectedCards the player chose are 4 Queens
		  * If so, return true and if it is not 4 Queens, return false
		  */
		 private boolean contains4Queen(List<Integer> selectedCards) {
		     int queen = 0;  
			 for (Integer kObj : selectedCards) {
		            int k = kObj.intValue();
		            if (cardAt(k).rank().equals("queen")) {
		             queen++;
		            }
		        }
		        if(queen ==4) {
		        	return true;
		        }
		        else {
		        	return false;
		        }
		    }
}
