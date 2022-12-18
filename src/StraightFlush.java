/**
 * StraightFlush is a subclass of the Hand class and are used to model a hand of StraightFlush.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class StraightFlush extends Hand
{
	/**
	 * a constructor for building a StraightFlush hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public StraightFlush(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for checking if this hand beats a specified hand if this hand is StraightFlush.
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
			return true;
		}
		else if (hand.getType() == "StraightFlush")
		{
			if ( this.getTopCard().compareTo(hand.getTopCard()) > 0 )
				return true;
		}
		return false;
	}
	
	/**
	 * a method for checking if this is a valid hand.
	 * 
	 * @return true This object is a valid StraightFlush hand
	 * @return false This object is not a valid StraightFlush hand
	 */
	public boolean isValid()
	{
		// This hand consists of five cards with consecutive ranks and the same suit.
		if (this.size() == 5 && this.maxLengthOfConsecutiveRanks() == 5 && this.numOfSuits() == 1)
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
	 * @return "StraightFlush" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "StraightFlush";
	}
}