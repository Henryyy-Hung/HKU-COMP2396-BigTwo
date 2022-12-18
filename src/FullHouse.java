import java.util.ArrayList;

/**
 * FullHouse is a subclass of the Hand class and are used to model a hand of FullHouse.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class FullHouse extends Hand
{
	/**
	 * a constructor for building a FullHouse hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for retrieving the top card of this FullHouse hand.
	 * 
	 * @return max The card of maximum value of the triplet.
	 */
	public Card getTopCard()
	{
		// The rank where the triplet belongs to.
		int tripletRank = 0;
		// To store the triplet.
		CardList triplet = new CardList();
		
		int[] ranks = this.frequencyOfRanks();
		
		// locate the rank where triplet belongs to.
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i] == 3)
			{
				tripletRank = (i+2)%13;
				break;
			}
		}
		
		// insert all the cards that belongs to triplet rank to the list.
		for (int i = 0; i < this.size(); i++)
		{
			if (this.getCard(i).getRank() == tripletRank)
				triplet.addCard(this.getCard(i));
		}
		
		// Search for the largest card in the triplet and store it in max.
		Card max = triplet.getCard(0);
		for (int i = 0; i < triplet.size(); i++)
		{
			if (triplet.getCard(i).compareTo(max) > 0)
			{
				max = triplet.getCard(i);
			}
		}
		return max;
	}
	
	/**
	 * a method for checking if this hand beats a specified hand if this hand is FullHouse.
	 * 
	 * @param hand This hand
	 * 
	 * @return True It means this hand beats a specified hand.
	 * @return False It means the specified hand beats this hand.
	 */
	public boolean beats(Hand hand)
	{
		if (hand.getType() == "Straight")
		{
			return true;
		}
		else if (hand.getType() == "Flush")
		{
			return true;
		}
		else if (hand.getType() == "FullHouse")
		{
			if ( this.getTopCard().compareTo(hand.getTopCard()) > 0 )
				return true;
		}
		else if (hand.getType() == "Quad")
		{
			return false;
		}
		else if (hand.getType() == "StraightFlush")
		{
			return false;
		}
		return false;
	}
	
	/**
	 * a method for checking if this is a valid hand.
	 * 
	 * @return true This object is a valid FullHouse hand
	 * @return false This object is not a valid FullHouse hand
	 */
	public boolean isValid()
	{
		// This hand consists of five cards, with two having the same rank and three having another same rank
		if ( this.size() == 5 && this.numOfRanks() == 2 && this.maxFrequencyOfRanks() == 3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return "FullHouse" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "FullHouse";
	}
}