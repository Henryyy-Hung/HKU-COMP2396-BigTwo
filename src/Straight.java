/**
 * Straight is a subclass of the Hand class and are used to model a hand of Straight.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class Straight extends Hand
{
	/**
	 * a constructor for building a Straight hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public Straight(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for checking if this hand beats a specified hand if this hand is Straight.
	 * 
	 * @param hand This hand
	 * 
	 * @return True It means this hand beats a specified hand.
	 * @return False It means the specified hand beats this hand.
	 */
	public boolean beats(Hand hand)
	{
		// if the specified hand are Straight hand
		if (hand.getType() == "Straight")
		{
			if ( this.getTopCard().compareTo(hand.getTopCard()) > 0 )
				return true;
		}
		else if (hand.getType() == "Flush")
		{
			return false;
		}
		else if (hand.getType() == "FullHouse")
		{
			return false;
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
	 * @return true This object is a valid Straight hand
	 * @return false This object is not a valid Straight hand
	 */
	public boolean isValid()
	{
		// This hand consists of five cards with consecutive ranks and these cards don't belong to same suit
		if (this.size() == 5 && this.maxLengthOfConsecutiveRanks() == 5 && this.numOfSuits() != 1)
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
	 * @return "Straight" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "Straight";
	}
}