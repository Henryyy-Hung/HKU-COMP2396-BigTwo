/**
 * Flush is a subclass of the Hand class and are used to model a hand of Flush.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class Flush extends Hand
{
	/**
	 * a constructor for building a Flush hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public Flush(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for checking if this hand beats a specified hand if this hand is Flush.
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
			int suitA = this.getTopCard().getSuit(); 
			int suitB = hand.getTopCard().getSuit();
			
			if ( suitA > suitB )
				return true;
			else if ( suitA < suitB)
				return false;
			else if ( this.getTopCard().compareTo(hand.getTopCard()) > 0 )
				return true;
			else
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
	 * @return true This object is a valid Flush hand
	 * @return false This object is not a valid Flush hand
	 */
	public boolean isValid()
	{
		// if there are 5 cards in hand and they belong to same suit.
		if ( this.size() == 5 && this.numOfSuits() == 1)
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
	 * @return "Flush" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "Flush";
	}
}