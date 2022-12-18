/**
 * Quad is a subclass of the Hand class and are used to model a hand of Quad.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class Quad extends Hand
{
	/**
	 * a constructor for building a Quad hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public Quad(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for retrieving the top card of this Quad hand.
	 * 
	 * @return max The card of maximum value of the quadruplet.
	 */
	public Card getTopCard()
	{
		// The rank where the quadruplet belongs to.
		int quadrupletRank = 0;
		// To store the quadruplet.
		CardList quadruplet = new CardList();
		
		int[] ranks = this.frequencyOfRanks();
		
		// locate the rank where quadruplet belongs to.
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i] == 4)
			{
				quadrupletRank = (i+2)%13;
				break;
			}
		}
		
		// insert all the cards that belongs to quadruplet rank to the list.
		for (int i = 0; i < this.size(); i++)
		{
			if (this.getCard(i).getRank() == quadrupletRank)
				quadruplet.addCard(this.getCard(i));
		}
		
		// Search for the largest card in the quadruplet and store it in max.
		Card max = quadruplet.getCard(0);
		for (int i = 0; i < quadruplet.size(); i++)
		{
			if (quadruplet.getCard(i).compareTo(max) > 0)
			{
				max = quadruplet.getCard(i);
			}
		}
		return max;
	}
	
	/**
	 * a method for checking if this hand beats a specified hand if this hand is Quad.
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
			return true;
		}
		else if (hand.getType() == "Quad")
		{
			if ( this.getTopCard().compareTo(hand.getTopCard()) > 0 )
				return true;
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
	 * @return true This object is a valid Quad hand
	 * @return false This object is not a valid Quad hand
	 */
	public boolean isValid()
	{
		// This hand consists of five cards, with four having the same rank. 
		if ( this.size() == 5 && this.numOfRanks() == 2 && this.maxFrequencyOfRanks() == 4)
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
	 * @return "Quad" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "Quad";
	}
}