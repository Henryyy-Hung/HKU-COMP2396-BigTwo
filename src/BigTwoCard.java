
/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a Big Two card game. 
 * It should override the compareTo() method it inherits from the Card class to reflect the ordering of cards used in a Big Two card game.
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class BigTwoCard extends Card
{
	/**
	 * a constructor for building a card with the specified suit and rank. suit is an integer between 0 and 3, and rank is an integer between 0 and 12.
	 * 
	 * @param suit an int value between 0 and 3 representing the suit of a card:
	 *             <p>
	 *             0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank an int value between 0 and 12 representing the rank of a card:
	 *             <p>
	 *             0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11 =
	 *             'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank)
	{
		super(suit, rank);
	}
	
	/**
	 * a method for comparing the order of this card with the specified card. 
	 * Returns a negative integer, zero, or a positive integer when this card is less than, equal to, or greater than the specified card.
	 * 
	 * @param card The card to be compared with this card
	 * 
	 * @return -1 This card is less than the specified card
	 * @return 0 This card is equal to the specified card
	 * @return 1 This card is greater than the specified card
	 */
	public int compareTo(Card card)
	{
		// convert the rank into "A" -> 11, "2" -> 12, "3" -> 0, ... , "J" -> 8, "Q" -> 9, "K" -> 10.
		Card cardA = new Card(this.getSuit(), (((this.getRank()-2)+13)%13));
		Card cardB = new Card(card.getSuit(), (((card.getRank()-2)+13)%13));
		return cardA.compareTo(cardB); 
	}
}
