/** 
 *@author Joshua Dickerson 
 * The Card class represents an instance of a playing card 
 * 
 * @param suit is the suit of this card object
 * @param rank is the rank of this card object
 * @param imgFilename is the filename of the graphic for the face of the card
 */

public class Card implements Comparable{

	private String suit;
	private int rank;

	/**
	 * default constructor of the Card class
	 * instantiates a card object with a suit and rank
	 * @param suit
	 * @param rank
	 */
	public Card(String suit, int rank){
		setSuit(suit);
		setRank(rank);
	}

	/**
	 * getRankTrad
	 * takes the numeric value representing rank and converts it to a "traditional" string
	* @return String the "traditional" name of this rank
	*/
	public String getRankTrad(){
		String rankTrad ="";
		switch (this.rank) {
			case 1:
            	rankTrad="Ace";
            break;
            case 11:
            	rankTrad="Jack";
            break;
            case 12:
            	rankTrad="Queen";
            break;
            case 13:
            	rankTrad="King";
            break;
            default:
            	rankTrad=this.rank+"";
            break; 
		}

		return rankTrad;
	} // end getRankTrad()

	/**
	 * getSuit
	 * returns the suit of the card object
	 * @return suit
	 */
	public String getSuit(){
		return this.suit;
	}

	/**
	 * setSuit
	 * sets the suit of the card object
	 * @param suit
	 */
	public void setSuit(String suit){
		this.suit = suit;
	}
	
	/**
	 * getRank
	 * returns the rank of the card object
	 * @return rank
	 */
	public int getRank(){
		return this.rank;
	}

	/**
	 * setRank
	 * sets the rank of the card object
	 * @param rank
	 */
	public void setRank(int rank){
		this.rank = rank;
	}

	/**
	 * toString
	 * returns a string representation of the card object
	 * @return tmp
	 */
	public String toString(){
		String tmp = this.getRankTrad()+" of "+this.getSuit();
		return tmp;
	}


	/**
	 *compareTo
	 *compares the rank this card to a given card, returns an integer signifying less than (-), equal(0), or greater than (+)
	 *@param card
	 *@return int
	 */
	public int compareTo(Object card){
		Card newCard = (Card) card;
		int order;
		if(this.rank == newCard.getRank()){
			int valueThis = getSuitValue(this.suit);
			int valueOther = getSuitValue(newCard.getSuit());
			order = valueThis - valueOther;
		}else{
			order = this.rank - newCard.getRank();
		}
		return order;
	}

	private int getSuitValue(String suit){
		int value;
		if(suit.equals("Clubs")){
			value = 3;
		}else if(suit.equals("Diamonds")){
			value = 2;
		}else if(suit.equals("Hearts")){
			value = 1;			
		}else{
			value = 0;
		}

		return value;
	}
} // end Card
