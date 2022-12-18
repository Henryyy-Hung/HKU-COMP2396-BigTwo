import java.util.ArrayList;
import java.util.Collection;

/**
 * The BigTwo class implements the CardGame interface and is used to model a Big Two card game. 
 * It has private instance variables for storing the number of players, a deck of cards, a list of players, 
 * a list of hands played on the table, an index of the current player, and a user interface. Below is a detailed description for the BigTwo class.
 * 
 * @author Hung Ka Hing UID:3035782750
 * 
 */
public class BigTwo implements CardGame
{
	
	/**
	 * a constructor for creating a Big Two card game.
	 * (i) create 4 players and add them to the player list; and 
	 * (ii) create a BigTwoUI object for providing the user interface.
	 */
	public BigTwo()
	{
		this.playerList = new ArrayList<CardGamePlayer>();
		this.numOfPlayers = 4; 
		this.ui = new BigTwoUI(this);
		for (int i = 0; i < this.numOfPlayers; i++) 
		{
			this.playerList.add(new CardGamePlayer());
		}
	}
	
	// an int specifying the number of players.
	private int numOfPlayers;
	
	// a deck of cards.
	private Deck deck;
	
	// a list of players.
	private ArrayList<CardGamePlayer> playerList;
	
	// a list of hands played on the table.
	private ArrayList<Hand> handsOnTable;
	
	// an integer specifying the index of the current player.
	private int currentPlayerIdx;
	
	// a BigTwoUI object for providing the user interface.
	private BigTwoUI ui;
	
	/**
	 * a method for getting the number of players.
	 * 
	 * @return this.numOfPlayers The number of players
	 */
	public int getNumOfPlayers()
	{
		return this.numOfPlayers;
	}
	
	/**
	 * a method for retrieving the deck of cards being used.
	 * 
	 * @return this.deck The deck of cards being used.
	 */
	public Deck getDeck()
	{
		return this.deck;
	}
	
	/**
	 * a  method  for  retrieving  the  list  of players.
	 * 
	 * @return this.playerList The list of players.
	 */
	public ArrayList<CardGamePlayer> getPlayerList()
	{
		return this.playerList;
	}
	
	/**
	 * a method for retrieving the list of hands played on the table.
	 * 
	 * @return this.handsOnTable The hands played on the table.
	 */
	public ArrayList<Hand> getHandsOnTable()
	{
		return this.handsOnTable;
	}
	
	/**
	 * a method for retrieving the index of the current player.
	 * 
	 * @return this.currentPlayerIdx The index of the current player.
	 */
	public int getCurrentPlayerIdx()
	{
		return this.currentPlayerIdx;
	}
	
	/**
	 * a  method  for  starting/restarting  the  game  with  a  given shuffled deck of cards.
	 * (i) remove all the cards from the players as well as from the table; 
	 * (ii) distribute the cards to the players; 
	 * (iii) identify the player who holds the Three of Diamonds; 
	 * (iv) set both the currentPlayerIdx of the BigTwo object and the activePlayer of the 
	 * 		BigTwoUI object to the index of the player who holds the Three of Diamonds; 
	 * (v) call the repaint() method of the BigTwoUI object to show the cards on the table;
	 * (vi) call the promptActivePlayer() method of the BigTwoUI object to prompt user to select cards and make his/her move.
	 * 
	 * @param deck The given shuffled deck of cards
	 */
	public void start(Deck deck)
	{
		// remove all the cards from the players
		for (int i = 0; i < this.getNumOfPlayers(); i++)
		{
			this.getPlayerList().get(i).removeAllCards();
		}
		
		// remove all the cards from the table and update the UI
		this.handsOnTable = new ArrayList<Hand>(); 
		this.ui = new BigTwoUI(this);
		
		// distribute the cards to the players from the top of the deck
		while (! deck.isEmpty())
		{
			for (int i = 0; i < this.getNumOfPlayers(); i++)
			{
				if ( ! deck.isEmpty())
				{
					// identify the player who holds the Three of Diamonds
					if ( deck.getCard(0).equals(new Card(0,2)) )
					{
						// set the currentPlayerIdx of the BigTwo object to the index of the player who holds the Three of Diamonds
						this.currentPlayerIdx = i;
						// set the activePlayer of the BigTwoUI object to the index of the player who holds the Three of Diamonds
						this.ui.setActivePlayer(this.getCurrentPlayerIdx());
					}
					
					this.getPlayerList().get(i).addCard(deck.getCard(0));
					deck.removeCard(0);
				}
			}
		}
		
		// sort players' hand for better display
		for (int i = 0; i < this.getNumOfPlayers(); i++)
		{
			this.getPlayerList().get(i).sortCardsInHand();
		}
		
		//  call the repaint() method of the BigTwoUI object to show the cards on the table
		this.ui.repaint();
		// call the promptActivePlayer() method of the BigTwoUI object to prompt user to select cards and make his/her move.
		this.ui.promptActivePlayer();
		
		// repeat the game until one use up his/her cards
		while (! this.endOfGame())
		{
			// update UI and current player and reset the active player for UI
			this.ui = new BigTwoUI(this);
			this.currentPlayerIdx = (this.getCurrentPlayerIdx() + 1) % this.getNumOfPlayers();
			this.ui.setActivePlayer(this.getCurrentPlayerIdx());
			
			// show cards on table and allow user input again
			this.ui.repaint();
			this.ui.promptActivePlayer();
		}
		
		// show all the cards on table
		this.ui.setActivePlayer(-1); 
		this.ui.repaint();
		System.out.println();
		
		System.out.println("Game ends");
		
		// print out the result
		for (int i = 0; i < this.getNumOfPlayers(); i++)
		{
			String name = this.getPlayerList().get(i).getName();
			int remainingCards = this.getPlayerList().get(i).getCardsInHand().size();
			if ( remainingCards > 0)
				System.out.println(name+" has "+remainingCards+" cards in hand.");
			else
				System.out.println(name+" wins the game.");
		}
	}
	
	/**
	 * a method for making a move by a player with the specified index using the cards specified by the list of indices. 
	 * This method should be called from the BigTwoUI after the active player has selected cards to  make  his/her move.  
	 * You  should  simply  call  the checkMove() method  with  the playerIdx and cardIdx as the arguments. 
	 * 
	 * @param playerIdx The index of a player.
	 * @param cardIdx The list indices of the card.
	 */
	public void makeMove(int playerIdx, int[] cardIdx)
	{
		this.checkMove(playerIdx, cardIdx);
		return;
	}
	
	/**
	 * a method for checking a move made by a player. 
	 * This method should be called from the makeMove() method.
	 * 
	 * @param playerIdx The index of a player
	 * @param cardIdx The list indices of the card.
	 */
	public void checkMove(int playerIdx, int[] cardIdx)
	{
		CardGamePlayer player = this.getPlayerList().get(playerIdx);
		CardList cards = new CardList();

		Hand lastHand = (this.handsOnTable.size() > 0)? this.handsOnTable.get(this.handsOnTable.size()-1) : null;
		CardGamePlayer playerOfLastHand = (lastHand != null)? lastHand.getPlayer() : null;
		
		// if the player want to pass
		if (cardIdx == null)
		{	
			// if the player is the first player with 3 of Diamonds
			if ((player.getCardsInHand().contains(new Card(0,2))))
			{
				System.out.print("Not a legal move!!!\n");
				this.ui.promptActivePlayer();
				return;
			}
			// if the player plays the last hand on the table
			else if ( player == playerOfLastHand )
			{
				System.out.print("Not a legal move!!!\n");
				this.ui.promptActivePlayer();
				return;
			}
			// if the player follows the rule
			else
			{
				System.out.print("{"+"pass"+"}\n\n");
				return;
			}
		}
		// if the player want to play a hand
		else
		{
			// check all the input are within the range or not
			for (int i = 0; i < cardIdx.length; i++)
			{
				if ( ! (cardIdx[i] < player.getCardsInHand().size()) )
				{
					System.out.print("Not a legal move!!!\n");
					this.ui.promptActivePlayer();
					return;
				}
			}
			
			// add the specified cards into the array list "cards"
			for (int i = 0; i < cardIdx.length; i++)
			{
				cards.addCard(player.getCardsInHand().getCard(cardIdx[i]));
			}
			
			boolean isValidCombination = (BigTwo.composeHand(player, cards) != null)? true:false;
			boolean playerHoldsThreeOfDiamond = (player.getCardsInHand().contains(new Card(0,2)))? true:false;
			boolean handContainsThreeOfDiamond = (cards.contains(new Card(0,2)))? true:false;
			boolean isPlayerOfLastHandOnTable = (player == playerOfLastHand)? true:false;
			boolean beatsHandOnTable = (isValidCombination && ! playerHoldsThreeOfDiamond && BigTwo.composeHand(player, cards).beats(lastHand))? true:false;
			
			if ( ! isValidCombination )
			{
				System.out.print("Not a legal move!!!\n");
				this.ui.promptActivePlayer();
				return;
			}
			
			if ( playerHoldsThreeOfDiamond && ! handContainsThreeOfDiamond )
			{
				System.out.print("Not a legal move!!!\n");
				this.ui.promptActivePlayer();
				return;
			}
			
			if ( ! playerHoldsThreeOfDiamond && ! isPlayerOfLastHandOnTable && !beatsHandOnTable )
			{
				System.out.print("Not a legal move!!!\n");
				this.ui.promptActivePlayer();
				return;
			}
			
			this.handsOnTable.add(BigTwo.composeHand(player, cards));
			player.removeCards(cards);
			
			System.out.print("{" + this.handsOnTable.get(handsOnTable.size() - 1).getType() + "} ");
			this.handsOnTable.get(handsOnTable.size() - 1).print(true,false);
			System.out.println();
			
			return;
		}
	}
	
	/**
	 * a method for checking if the game ends.
	 * 
	 * @return true if the game is ended
	 */
	public boolean endOfGame()
	{
		for (int i = 0; i < this.getNumOfPlayers(); i++)
		{
			if (this.getPlayerList().get(i).getCardsInHand().size() == 0)
				return true;
		}
		return false;
	}
	
	/**
	 * a method for starting a Big Two card game. 
	 * (i) create a Big Two card game, 
	 * (ii) create and shuffle a deck of cards, and 
	 * (iii) start the game with the deck of cards.
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args)
	{
		BigTwo game = new BigTwo();
		BigTwoDeck deck = new BigTwoDeck();
		deck.initialize();
		deck.shuffle();
		game.start(deck);
	}
	
	/**
	 * a method for returning a valid hand from the specified list of cards of the player. 
	 * Returns null if no valid hand can be composed from the specified list of cards.
	 * 
	 * @param player The player who play the hand.
	 * @param cards The cards of the player.
	 * @return A valid hand from the specified list of cards of the player
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{
		// check if the player own this card or not
		for (int i = 0; i < cards.size(); i++)
			if ( ! player.getCardsInHand().contains(cards.getCard(i)) )
				return null;
		
		Single single = new Single(player, cards);
		Pair pair = new Pair(player,cards);
		Triple triple = new Triple(player, cards);
		Straight straight = new Straight(player, cards);
		Flush flush = new Flush(player,cards);
		FullHouse fullhouse = new FullHouse(player,cards);
		Quad quad = new Quad(player,cards);
		StraightFlush straightflush = new StraightFlush(player,cards);
		
		if (single.isValid())
			return single;
		if (pair.isValid())
			return pair;
		if (triple.isValid())
			return triple;
		if (straight.isValid())
			return straight;
		if (flush.isValid())
			return flush;
		if (fullhouse.isValid())
			return fullhouse;
		if (quad.isValid())
			return quad;
		if (straightflush.isValid())
			return straightflush;
		
		return null;
	}
}
