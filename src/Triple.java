/**
 * Triple is a subclass of the Hand class and are used to model a hand of Triple.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class Triple extends Hand
{
	/**
	 * a constructor for building a Triple hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public Triple(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for checking if this is a valid hand.
	 * 
	 * @return true This object is a valid Triple hand
	 * @return false This object is not a valid Triple hand
	 */
	public boolean isValid()
	{
		// There is only 3 card in hand, and they belong to same rank.
		if ( this.size() == 3 && this.numOfRanks() == 1)
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
	 * @return "Triple" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "Triple";
	}
}