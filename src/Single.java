/**
 * Single is a subclass of the Hand class and are used to model a hand of Single.
 * It override methods of the Hand class as appropriate. 
 * In particular, the getType() method should return the name of the class as a 
 * String object in these classes modelling legal hands in a Big Two card game. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class Single extends Hand
{
	/**
	 * a constructor for building a Single hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public Single(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	
	/**
	 * a method for checking if this is a valid hand.
	 * 
	 * @return true This object is a valid Single hand
	 * @return false This object is not a valid Single hand
	 */
	public boolean isValid()
	{
		// There is only 1 card in hand
		if ( this.size() == 1 )
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
	 * @return "Single" The string specifying the type of this hand
	 */
	public String getType()
	{
		return "Single";
	}
}
