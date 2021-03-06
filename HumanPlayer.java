/**
 * HumanPlayer
 * @author phelanvendeville
 * Class to handle all of the functionality for the human player in the gofish game. Implements the 
 * Player interface.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;  
import java.util.Stack;


public class HumanPlayer implements Player{
	private Deck gameDeck;
	private Player opponent;
	private boolean hasCards;
	private boolean opponentHasCard;
	private Hand playerHand;

	/**
	 * main method for testing purposes
	 */
	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.shuffle();

		/* Test the respondCardRequest function */
		Card[] cards = new Card[5];
		//add a specific card to the hand so we know what we are looking for
		Card newCard = new Card("spades", 1);

		//add cards from the deck to the cards array
		for(int ii=0; ii<4; ii++){
			cards[ii] = deck.getTopCard(); 
		}
		//add our specific card
		cards[4] = newCard;

		Hand hand = new Hand(cards);
		HumanPlayer human = new HumanPlayer(hand);
		
		/*Test the respondCardRequest function
		System.out.println("Before Hand");
		System.out.println(hand);
		ArrayList<Card> returnedRequest = human.respondCardRequest(1);
		System.out.println("");
		System.out.println("The matched cards");
		System.out.println(returnedRequest);
		System.out.println("");
		System.out.println("the new hand, minus those cards");
		System.out.println(human.getHand());*/

		/* test the doTurn function, creating a new player as an opponent*/ 
		Card[] cards2 = new Card[5];
		deck.shuffle();
		for(int ii=0; ii<5; ii++){
			cards2[ii] = deck.getTopCard(); 
		}
		Hand hand2 = new Hand(cards2);
		HumanPlayer computer = new HumanPlayer(hand2);

		System.out.println("Opponents Hand");
		System.out.println(computer.getHand());
		System.out.println("");

		human.doTurn(deck, computer);
	}
	
	/**
	 * default constructor for human player
	 * @param hand
	 */
	public HumanPlayer(Hand playerHand){
		this.playerHand = playerHand;
	}
	
	/**
	 * doTurn
	 * Function to perform the actions of a turn
	 * @param gameDeck
	 * @param opponent
	 * @return deck
	 */
	public Deck doTurn(Deck gameDeck,Player opponent){
		this.gameDeck = gameDeck;
		this.opponent = opponent;
		ArrayList<Card> foundCards = new ArrayList<Card>();

		//Display the user's hand to them
		System.out.println("Your Hand");
		UserInterface.displayHand(playerHand);

		//get user input (which card to request from opponent?)
		int desiredCard = UserInterface.getCommand(playerHand);
		
		//if the opponent has no cards in their hand, the game is over
		if(desiredCard == -1){
			//call the endgame functions
		}
		
		//request all the cards of desired type from the opponent
		if (makeCardRequest(opponent, desiredCard)){
			//get all instances of that card from the opponent
			foundCards = opponent.respondCardRequest(desiredCard);
			//add them to your hand
			for(Card card : playerHand.getCards()){
				foundCards.add(card);
			}
			System.out.println("Your new hand");
			System.out.println(foundCards);
			System.out.println("Opponents new hand");
			System.out.println(opponent.getHand());

			//remove them from the opponent's hand
			//check for a full set of cards in your hand
			//play the full set down, if there are any
			//call doTurn() again
		}
		//the opponent doesn't have the card, and the player must go fish
		else{
			//remove the top card from the deck
			//add that card to your hand
			//check for the presence of a full set
			//play that full set if there is one
			//if the card pulled from the deck is the one asked for, call doTurn()
		}
		
		return gameDeck;
	}
	
	/**
	 * hasCards
	 * Function to determine if the player has any cards in their hand
	 * @return hasCards
	 */
	public boolean hasCards(){
		if(playerHand.calcTotal() > 0){
			hasCards = true;
		}
		else
			hasCards = false;
		
		return hasCards;
	}
	
	/**
	 * makeCardRequest
	 * Function used to ask another player if they possess a card
	 * @param opponent
	 * @return opponentHasCard
	 */
	public boolean makeCardRequest(Player opponent, int desiredCard){
		this.opponent = opponent;
		opponentHasCard = false;

		//if the card is in the opponent's hand, then return true. otherwise, false.
		for(Card card : opponent.getHand().getCards()){
			if(card.getRank() == desiredCard){
				opponentHasCard = true;
				return opponentHasCard;
			}
		}

		return opponentHasCard;
	}
	
	/**
	 * respondCardRequest
	 * Used to respond to card requests from opponents. Looks through hand and removes the card(s) requested
	 * for, if it is present. Returns the (those) card(s).
	 * @param desiredCard
	 * @return null if not preset, the and array containing the card(s) if present
	 */
	public ArrayList<Card> respondCardRequest(int desiredCard){
		//create a holder arraylist for the found cards
		ArrayList<Card> foundCards = new ArrayList<Card>();

		//oldhand is a container for the players current hand
		Hand oldHand = playerHand;
		//newhand is a stack to place the cards not culled from the players hand into
		Stack<Card> newHand = new Stack<Card>();

		//go through each card in the hand
		for(int i=0; i<oldHand.getCards().length; i++){
			Card currentCard = oldHand.getCards()[i];
			//if the current card's rank isn't equal to the desired rank...
			if(currentCard.getRank() != desiredCard){
				//add that card to the new hand. it will not be given to the requesting player
				newHand.push(currentCard);
			}
			else{
				//otherwise, the card matches. add it to the arraylist keeping track of found cards
				foundCards.add(currentCard);
			}
		}

		// convert the new hand to a hand object, and make that the new player hand
		Card[] cardHolder = new Card[newHand.size()];
		for(int j=0; j<newHand.size(); j++){
			cardHolder[j] = newHand.get(j);
			System.out.println(cardHolder[j]);
		}
		Hand freshHand = new Hand(cardHolder);
		this.playerHand = freshHand;

		/* Iterator Method */
		//copy the player's hand to an array list so it can be iterated
		// ArrayList<Card> handCopy = new ArrayList<Card>(Arrays.asList(playerHand.getCards()));
		// Iterator<Card> it = handCopy.iterator();

		// while(it.hasNext()){
		// 	Card currentCard = it.next();
		// 	// System.out.println(currentCard);
		// 	if(currentCard.getRank() == desiredCard){
		// 		// System.out.println("same");
		// 		// add the card to our temporary arraylist
		// 		foundCards.add(currentCard);
		// 		//remove the card from our hand
		// 		it.remove();

		// //add the remaining cards to a hand
		// Card[] cards = new Card[handCopy.size()];
		// for(int i=0; i<handCopy.size(); i++){
		// 	cards[i] = handCopy.get(i); 
		// }
		// //set the hand field to the contents of the new hand
		// Hand hand = new Hand(cards, 1);
		// this.playerHand = hand;
		/* END Iterator Method */
		
		//return the arraylist
		return foundCards;
	}

	/**
	  * getHand()
	  * @return a player's hand
	  * return a player's hand
	  */
	public Hand getHand(){
		return this.playerHand;
	}

	/**
	 * endTurn
	 * Function to set the final values for the end of the turn
	 */
	public void endTurn(){
		
	}

	public Card[] getMyCompleteSets(){
		Card[] cards = new Card[1];
		return cards;
	}
}