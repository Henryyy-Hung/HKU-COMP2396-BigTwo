/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards. 
 * It has a private instance variable for storing the player who plays this hand. 
 * It also has methods for getting the player of this hand, checking if it is a valid hand, 
 * getting the type of this hand, getting the top card of this hand, and checking if it beats a specified hand. 
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public abstract class Hand extends CardList
{
	/**
	 * a constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player A specified player
	 * @param cards A list of cards
	 */
	public Hand(CardGamePlayer player, CardList cards)
	{
		this.player = player;
		for (int i = 0; i < cards.size(); i++)
		{
			this.addCard(cards.getCard(i));
		}
	}
	
	// the player who plays this hand.
	private CardGamePlayer player;
	
	/**
	 * a method for retrieving the player of this hand.
	 * 
	 * @return this.player The player of this hand.
	 */
	public CardGamePlayer getPlayer()
	{
		return this.player;
	}
	
	/**
	 * a method for finding the card with greatest value in the Hand
	 * 
	 * @return max The card with greatest value in the Hand
	 */
	public Card maxCard()
	{
		Card max = this.getCard(0);
		
		for (int i = 0; i < this.size(); i++)
		{
			if (this.getCard(i).compareTo(max) > 0)
			{
				max = this.getCard(i);
			}
		}
		return max;
	}
	
	/**
	 * a method for retrieving the top card of this hand.
	 * 
	 * @return this.maxCard() 
	 */
	public Card getTopCard()
	{
		return this.maxCard();
	}
	
	/**
	 * a method for checking if this hand beats a specified hand.
	 * 
	 * @param hand This hand
	 * 
	 * @return True  It means this hand beats a specified hand.
	 * @return False It means the specified hand beats this hand.
	 */
	public boolean beats(Hand hand)
	{
		if ( this.getTopCard().compareTo(hand.getTopCard()) > 0 && this.size() == hand.size())
			return true;
		return false;
	}
	
	/**
	 * a method return the frequency of cards in each rank from 0 to 12 in a BigTwo way.
	 * i.e. 3 -> ranks[0], 4 -> ranks[1], ..., K -> ranks[10], A -> ranks[11], 2 -> ranks[12]
	 * 
	 * @return ranks The frequency of cards in each rank represented by the value at different list indices.
	 */
	public int[] frequencyOfRanks()
	{
		int[] ranks = new int[13];
		for (int i = 0; i < 13; i++)
			ranks[i] = 0;
		for (int i = 0; i < this.size(); i++)
		{
			ranks[ (((this.getCard(i).getRank()-2)+13)%13) ]++;
		}
		return ranks;
	}
	
	/**
	 * a method return the frequency of cards in each suits from 0 to 3.
	 * i.e. Diamond = suits[0], Club = suits[1], Heart = suits[2], Spade = suits[3]
	 * 
	 * @return suits The frequency of cards in each suits represented by the value at different list indices.
	 */
	public int[] frequencyOfSuits()
	{
		int[] suits = new int[4];
		for (int i = 0; i < 4; i++)
			suits[i] = 0;
		for (int i = 0; i < this.size(); i++)
		{
			suits[ (this.getCard(i).getSuit()) ]++;
		}
		return suits;
	}
	
	/**
	 * a method counts the number of different ranks in this hand
	 * 
	 * @return count The number of different ranks in this hand
	 */
	public int numOfRanks()
	{
		int[] ranks = this.frequencyOfRanks();
		int count = 0;
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i] > 0)
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * a method counts the number of different suits in this hand
	 * 
	 * @return count The number of different suits in this hand
	 */
	public int numOfSuits()
	{
		int[] suits = this.frequencyOfSuits();
		int count = 0;
		for (int i = 0; i < suits.length; i++)
		{
			if (suits[i] > 0)
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * a method search for the rank with highest frequency in this hand and return its frequency.
	 * 
	 * @return max The frequency (a integer) of the rank with highest frequency in this hand.
	 */
	public int maxFrequencyOfRanks()
	{
		int[] ranks = this.frequencyOfRanks();
		int max = 0;
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i] > max)
			{
				max = ranks[i];
			}
		}
		return max;
	}
	
	/**
	 * a method counts the maximum length of card with consecutive ranks
	 * e.g. 34567 = 5, 567 = 3 and etc.
	 * 
	 * @return max The maximum length of card with consecutive ranks
	 */
	public int maxLengthOfConsecutiveRanks()
	{
		int[] ranks = this.frequencyOfRanks();
		int count = 0;
		int max = 0;
		
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i] > 0)
				count++;
			else
				count = 0;
			
			if (count > max)
				max = count;
		}
		return max;
	}
	
	/**
	 * a method for checking if this is a valid hand.
	 */
	public abstract boolean isValid();
	
	/**
	 * a method for returning a string specifying the type of this hand.
	 */
	public abstract String getType();
}
