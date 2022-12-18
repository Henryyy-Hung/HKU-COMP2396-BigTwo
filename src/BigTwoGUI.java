import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

/**
 * It is used to build a GUI for the Big Two card game and handle all user actions. 
 * 
 * @author Hung Ka Hing UID:3035782750
 * 
 */
public class BigTwoGUI implements CardGameUI
{
	/**
	 * a constructor for creating a BigTwoGUI
	 * 
	 * @param game A reference to a Big Two card game associates with this GUI
	 */
	public BigTwoGUI(BigTwo game)
	{
		// link the GUI with the game.
		this.game = game;
		this.playerList = game.getPlayerList();
		this.handsOnTable = game.getHandsOnTable();
		
		// Load the images that will be used in the game.
		this.initializeImage();
		
		// initialize the components of the frame.
		this.frame = new JFrame();
		this.bigTwoPanel = new BigTwoPanel();
		this.playButton = new JButton("Play");
		this.passButton = new JButton("Pass");
		this.msgArea = new JTextArea();
		this.chatArea = new JTextArea();
		this.chatInput = new JTextField();
		this.restartItem = new JMenuItem("Restart");
		this.quitItem = new JMenuItem("Quit");
		
		// add the listeners to the components.
		this.playButton.addActionListener(new PlayButtonListener());
		this.passButton.addActionListener(new PassButtonListener());
		this.restartItem.addActionListener(new RestartMenuItemListener());
		this.quitItem.addActionListener(new QuitMenuItemListener());
		this.chatInput.addActionListener(new ChatInputListener());
		
		// setup the message area of the game.
		this.msgArea.setLineWrap(true);
		this.msgArea.setEditable(false);
		this.msgArea.setForeground(Color.BLACK);
		this.msgArea.setBackground(new Color(235,229,209));
		this.msgArea.setFont(TEXT_FONT);
		
		// setup the chat area of the game.
		this.chatArea.setLineWrap(true);
		this.chatArea.setEditable(false);
		this.chatArea.setForeground(Color.BLUE);
		this.chatArea.setBackground(new Color(235,229,209));
		this.chatArea.setFont(TEXT_FONT);
		
		// initialize the frame by arranging components into the correct position
		this.initializeFrame();
	}
	
	// max. no. of cards each player holds
	private final static int MAX_CARD_NUM = 13;
	
	// max no. of players
	private final static int MAX_PLAYER_NUM = 4;
	
	// max. no. of ranks
	private final static int MAX_RANK_NUM = 13;
	
	// max no. of suit
	private final static int MAX_SUIT_NUM = 4;
	
	// a image object referring to the background of card table
	private Image BACKGROUND_OF_TABLE;
	
	// the array storing image objects that referring to the face up icon of card
	private Image[][] FACE_UP_CARD_ICONS;
	
	// a image object referring to the face down icon of card
	private Image FACE_DOWN_CARD_ICON;
	
	// the array storing image objects. i.e. the players' icons(Avatar).
	private Image[] PLAYER_ICONS;
	
	// the initial size of the frame.
	private Dimension FRAME_DIMENSION = new Dimension(800,600);
	
	// the size of card components.
	private Dimension CARD_DIMENSION = new Dimension(50,75);
	
	// the size of the information panel.
	private Dimension INFO_PANEL_DIMENSION = new Dimension(70,100);
	
	// the size of the card panel.
	private Dimension CARD_PANEL_DIMENSION = new Dimension(300,100);
	
	// the separation between overlapped cards.
	private int SEPARATION_BETWEEN_CARDS = CARD_DIMENSION.width / 3;
	
	// the height of non-raised card components.
	private int ORIGINAL_HEIGHT_OF_CARD = 20;
	
	// the height of raised card components .
	private int RAISED_HEIGHT_OF_CARD = ORIGINAL_HEIGHT_OF_CARD - 10;
	
	// a font of text inside the game.
	private Font TEXT_FONT = new Font("Arial", Font.BOLD, 12);
	
	// a Big Two card game associates with this GUI.
	private BigTwo game;
	
	// the list of players
	private ArrayList<CardGamePlayer> playerList; 
	
	// the list of hands played on the table
	private ArrayList<Hand> handsOnTable;
	
	// a boolean array indicating which cards are being selected.
	private boolean[] selected = new boolean[MAX_CARD_NUM];
	
	// an integer specifying the index of the active player.
	private int activePlayer = -1;
	
	// the main window of the application.
	private JFrame frame;
	
	// a panel for showing the cards of each player and the cards played on the table.
	private JPanel bigTwoPanel;
	
	// a ¡°Play¡± button for the active player to play the selected cards.
	private JButton playButton;
	
	// a ¡°Pass¡± button for the active player to pass his/her turn to the next player.
	private JButton passButton; 
	
	// a text area for showing the current game status as well as end of game messages.
	private JTextArea msgArea;
	
	// a text area for showing chat messages sent by the players.
	private JTextArea chatArea;
	
	// a text field for players to input chat messages.
	private JTextField chatInput;
	
	// a menu item for player to restart the game.
	private JMenuItem restartItem;
	
	// a menu item for player to quit the game.
	private JMenuItem quitItem;
	
	// Load the images that will be used in the game.
	private void initializeImage()
	{
		// file name corresponding to the card value
		final String[] Suits = { "d", "c", "h", "s" };
		final String[] Ranks = { "a","2","3","4","5","6","7","8","9","t","j","q","k" };
		
		// load the image of the face up icon of cards
		this.FACE_UP_CARD_ICONS = new Image[this.MAX_RANK_NUM][this.MAX_SUIT_NUM];
		for (int rank = 0; rank < this.MAX_RANK_NUM; rank++)
		{
			for (int suit = 0; suit < this.MAX_SUIT_NUM; suit++)
			{
				String nameOfCard = Ranks[rank] + Suits[suit];
				this.FACE_UP_CARD_ICONS[rank][suit] = new ImageIcon("./image/cards/" + nameOfCard + ".gif").getImage();
			}
		}
		 
		// load the image of face down icon of cards
		this.FACE_DOWN_CARD_ICON = new ImageIcon("./image/cards/" + "b" + ".gif").getImage();
		
		// load the image of players' avatar
		this.PLAYER_ICONS = new Image[this.MAX_PLAYER_NUM];
		for (int i = 0; i < this.MAX_PLAYER_NUM; i++)
		{
			this.PLAYER_ICONS[i] = new ImageIcon("./image/icons/" + "Player " + Integer.toString(i) + ".jpg").getImage();
		}
		
		// load the image of the background of card table
		this.BACKGROUND_OF_TABLE = new ImageIcon("./image/background/table.jpg").getImage();
	}
	
	// initialize the frame by setup the components and arrange the components into correct position.
	private void initializeFrame()
	{
		// setup the frame.
		this.frame.setTitle("Big Two");
		this.frame.setSize(this.FRAME_DIMENSION);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(true); 
		
		// setup the menu bar.
		JMenu game = new JMenu("Game");
		game.add(this.restartItem);
		game.add(this.quitItem);
		JMenu message = new JMenu("Message");
		JMenuBar gameMenuBar = new JMenuBar();
		gameMenuBar.add(game);
		gameMenuBar.add(message);
		
		// setup the massage text area.
		JScrollPane msgAreaScrollPane = new JScrollPane(this.msgArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		msgAreaScrollPane.setMinimumSize(new Dimension(10,10));
		msgAreaScrollPane.setPreferredSize(new Dimension(10,10));
		
		// setup the chat text area.
		JScrollPane chatAreaScrollPane = new JScrollPane(this.chatArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatAreaScrollPane.setMinimumSize(new Dimension(10,10));
		chatAreaScrollPane.setPreferredSize(new Dimension(10,10));
		
		// setup the panel containing the "play" and "pass" button.
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.playButton);
		buttonPanel.add(this.passButton);
		
		// setup the panel containing the chat input bar.
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.X_AXIS));
		textFieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		textFieldPanel.add(new JLabel("Message: "));
		textFieldPanel.add(this.chatInput);
		
		// integrate the button panel and text field panel together into 1 panel. X Ratio = 7:3.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.add(buttonPanel, new GridBagConstraints(0,0,1,1,0.7,1.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(1, 1, 1, 1),0,0));
		bottomPanel.add(textFieldPanel, new GridBagConstraints(1,0,1,1,0.3,1.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(1, 1, 1, 1),0,0));

		// integrate the massage area and chat are together into 1 panel. Y Ratio = 1:1.
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new GridBagLayout());
		textAreaPanel.add(msgAreaScrollPane, new GridBagConstraints(0,0,1,1,1.0,0.5,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(1, 0, 1, 0),0,0));
		textAreaPanel.add(chatAreaScrollPane, new GridBagConstraints(0,1,1,1,1.0,0.5,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(1, 0, 1, 0),0,0));
		
		// integrate the Big Two panel and text area panel together into 1 main panel. X Ratio = 7:3.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.add(this.bigTwoPanel, new GridBagConstraints(0,0,1,1,0.7,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(3, 3, 4, 4),0,0));
		mainPanel.add(textAreaPanel, new GridBagConstraints(1,0,1,1,0.3,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(2, 0, 2, 2),0,0));
		
		// add the integrated panel to the frame.
		this.frame.add(gameMenuBar, BorderLayout.NORTH);
		this.frame.add(mainPanel , BorderLayout.CENTER);
		this.frame.add(bottomPanel, BorderLayout.SOUTH);
		
		// set the frame become visible.
		this.frame.setVisible(true);
	}
	
	// It is used to build the Big Two panel for demonstrating and selecting cards.
	class BigTwoPanel extends JPanel
	{
		// a array list storing rows of panel that placing the cards
		private ArrayList<JLayeredPane> rows = new ArrayList<JLayeredPane>();
		
		// the max. number of rows in the big two panel
		private final int NumOfRow = 5;
		
		// a constructor for creating a Big Two panel
		public BigTwoPanel()
		{
			this.setOpaque(true);
			this.setLayout(new GridBagLayout());
			
			// create 5 rows on the big two panel
			for (int i = 0; i < NumOfRow; i++)
			{
				JLayeredPane pane = new JLayeredPane();
				pane.setOpaque(false);
				pane.setBorder(BorderFactory.createLineBorder(Color.black));
				rows.add(pane);
			}
			
			// iterate through all rows containing player and add the information panel and card panel on them.
			for (int i = 0; i < playerList.size(); i++)
			{
				int playerID = i;
				String playerName = playerList.get(playerID).getName();
				
				PlayerInfo infoPanel = new PlayerInfo(playerName, playerID);
				infoPanel.setLocation(0,0);
				
				CardDisplayPanel cardPanel = new CardDisplayPanel(playerID);
				cardPanel.setLocation(infoPanel.getWidth(), 0);
				
				rows.get(i).add(infoPanel);
				rows.get(i).add(cardPanel);
			}
			
			// add the panel containing the hands on the table to the last row.
			HandOnTableDisplayPanel handOnTablePanel = new HandOnTableDisplayPanel();
			handOnTablePanel.setLocation(0,20);		
			rows.get(4).add(new HandOnTableDisplayPanel());
			
			// add the rows to the big two panel.
			for (int i = 0; i < rows.size(); i++)
			{
				this.add(rows.get(i), new GridBagConstraints(0,i,1,1,1.0,1.0/NumOfRow,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),0,0));
			}
		}
		
		// override the paintComponent method to draw background image on the Big Two Panel.
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(BACKGROUND_OF_TABLE,0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	
	// it is used to build the information panel of players that containing the name and avatar
	class PlayerInfo extends JPanel
	{
		// a constructor for creating a information panel.
		PlayerInfo(String playerName, int playerID)
		{
			this.setOpaque(false);
			this.setSize(INFO_PANEL_DIMENSION);
			this.setBackground(Color.black);
			this.setLayout(new GridBagLayout());
			this.add(new PlayerName(playerName,playerID), new GridBagConstraints(0,0,1,1,1.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 10, 0, 10),0,0));
			this.add(new PlayerIcon(playerID), new GridBagConstraints(0,1,1,1,1.0,0.9,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 10, 5, 10),0,0));
		}
	}
	
	// it is used to build the label containing player's name.
	// and change the name to "You" with green color when it is the round of specific player.
	class PlayerName extends JLabel
	{
		// the name of player.
		private String playerName;
		// the id of player.
		private int playerID;
		
		// a constructor for creating a PlayerName label.
		PlayerName(String playerName, int playerID)
		{
			this.playerName = playerName;
			this.playerID = playerID;
			this.setOpaque(false);
			this.setBackground(Color.black);
			this.setFont(TEXT_FONT);
		}
		
		// override the paintComponent for drawing and updating the label.
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if (playerID==activePlayer)
			{
				this.setText("You");
				this.setForeground(new Color(0, 255, 0));
			}
			else
			{
				this.setText(playerName);
				this.setForeground(Color.white);
			}
		}
	}
	
	// it is used to build the avatar of player
	class PlayerIcon extends JLabel
	{
		Image icon;
		
		// a constructor for creating the player icon.
		PlayerIcon(int playerID)
		{
			this.setOpaque(true);
			this.setSize(CARD_DIMENSION);
			this.setBackground(Color.black);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			
			// assign the image to the variable icon.
			icon = PLAYER_ICONS[playerID];
		}
		
		// override the paintComponent for drawing the image of avatar.
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);			
			g.drawImage(icon,0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	
	// it is used to build panel for displaying last hand on the table.
	class HandOnTableDisplayPanel extends JLayeredPane
	{
		// a constructor for creating a HandOnTableDisplayPanel.
		HandOnTableDisplayPanel()
		{
			this.setOpaque(false);
			this.setSize(CARD_PANEL_DIMENSION);
		}
		
		// override the paintComponent method for updating and drawing the last hands on the table.
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			this.removeAll();
			
			// ensure there is a last hand on the table.
			if (! handsOnTable.isEmpty())
			{
				// add a label containing the name of player of last hand on table to the top left corner of the panel.
				JLabel nameOfLastPlayer = new JLabel(handsOnTable.get(handsOnTable.size()-1).getPlayer().getName());
				nameOfLastPlayer.setSize(new Dimension(100,15));
				nameOfLastPlayer.setLocation(new Point(10,0));
				nameOfLastPlayer.setForeground(Color.white);
				nameOfLastPlayer.setFont(TEXT_FONT);
				this.add(nameOfLastPlayer, DRAG_LAYER);
				
				// add the cardComponent of last hand on table to the panel in overlapped manner.
				int size = handsOnTable.get(handsOnTable.size()-1).size();
				for (int i = 0; i < size; i++)
				{
					CardComponent currentCard = new CardComponent(handsOnTable.get(handsOnTable.size()-1).getCard(i));
					currentCard.setFaceUp(true);
					currentCard.setLocation(10 + i * SEPARATION_BETWEEN_CARDS, ORIGINAL_HEIGHT_OF_CARD);
					this.add(currentCard,Integer.valueOf(i));
				}
				this.repaint();
			}
		}
	}	
	
	// it is used to build a panel for displaying the card in hand of player.
	// it could receive the mouse action "raise" the card if user selected it.
	// it could allow user to select card.
	class CardDisplayPanel extends JLayeredPane implements MouseListener
	{
		private int playerID;
		// whether the entire cards in hand is face up or not.
		private boolean isFacedUp = false;
		// whether the entire cards in hand is selectable or not.
		private boolean isSelectable = false;
		// an array list storing the card components of this panel.
		private ArrayList<CardComponent> cardComponents = new ArrayList<CardComponent>();
		
		// a constructor for creating the card display panel.
		CardDisplayPanel(int playerID)
		{
			this.playerID = playerID;
			this.setOpaque(false);
			this.setSize(CARD_PANEL_DIMENSION);
			this.addMouseListener(this);		
		}
		
		// override the paintComponent method for drawing the cards on the panel.
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			// initiate the state of cards.
			if (activePlayer == -1)
			{
				this.isFacedUp = true;
				this.isSelectable = false;
			}
			else if (activePlayer == playerID)
			{
				this.isFacedUp = true;
				this.isSelectable = true;
			}
			else
			{
				this.isFacedUp = false;
				this.isSelectable = false;
			}
			
			// update the cards whenever repaint.
			this.removeAll();
			this.cardComponents.clear();
			
			// input protection.
			if ( -1 <= playerID && playerID < MAX_PLAYER_NUM)
			{
				// add the cards in hand of player to the panel in overlapped manner.
				for (int i = 0; i < playerList.get(playerID).getNumOfCards();i++)
				{
					CardComponent currentCard = new CardComponent(playerList.get(playerID).getCardsInHand().getCard(i));
					currentCard.setFaceUp(this.isFacedUp);
					if (selected[i] == true && this.isSelectable == true)
					{
						currentCard.setLocation(i * SEPARATION_BETWEEN_CARDS, RAISED_HEIGHT_OF_CARD);
					}
					else
					{
						currentCard.setLocation(i * SEPARATION_BETWEEN_CARDS, ORIGINAL_HEIGHT_OF_CARD);
					}
					this.cardComponents.add(currentCard);
					this.add(this.cardComponents.get(i),Integer.valueOf(i));
				}
			}
			
			this.repaint();
		}

		// override the mouseRelease for selecting cards according to the x-y coordinate of mouse.
		public void mouseReleased(MouseEvent e) {
			if ( this.isSelectable == true )
			{
				int x = e.getX();
				int y = e.getY();
				int numOfCards = this.cardComponents.size();
				
				// iterate from right-most card to left-most card.
				for (int i = numOfCards-1; i >= 0; i--)
				{
					int x_min = this.cardComponents.get(i).getX();
					int x_max = x_min + this.cardComponents.get(i).getWidth();
					int y_min = this.cardComponents.get(i).getY();
					int y_max = y_min + this.cardComponents.get(i).getHeight();	
					
					if (x>=x_min && x <= x_max && y>=y_min && y <= y_max)
					{
						selected[i] = (selected[i] == true)? false:true;
						break;
					}
				}
				this.repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	// it is used to build a label of a card components with image.
	class CardComponent extends JLabel
	{
		private Card card;
		private Image FaceUpIcon;
		private Image FaceDownIcon;
		private boolean isFacedUp = false;

		// a constructor for creating the CardComponent.
		CardComponent(Card card)
		{
			this.card = card;
			this.setOpaque(true);
			this.setSize(CARD_DIMENSION);
			this.setPreferredSize(CARD_DIMENSION);
			this.setMinimumSize(CARD_DIMENSION);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.FaceUpIcon = FACE_UP_CARD_ICONS[card.getRank()][card.getSuit()];
			this.FaceDownIcon = FACE_DOWN_CARD_ICON;
		}
		
		// override the paintComponent method for drawing the image of card according to its state.
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			if (this.isFacedUp)
				g.drawImage(this.FaceUpIcon,0,0,this.getWidth(),this.getHeight(),this);
			else
				g.drawImage(this.FaceDownIcon,0,0,this.getWidth(),this.getHeight(),this);
		}
		
		// set the card become face up.
		public void setFaceUp(boolean faceUp)
		{
			this.isFacedUp = faceUp;
			this.repaint();
		}
	}
	
	// an inner class that implements the ActionListener interface. 
	// Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the ¡°Play¡± button.
	class PlayButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if( getSelected() != null )
			{
				int[] cardIdx = getSelected();
				resetSelected();
				game.makeMove(activePlayer, cardIdx);
			} 
			else 
			{
				printDialog("Please Select Your Cards!");
			}
		}
	}
	
	// an inner class that implements the ActionListener interface. 
	//Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the ¡°Pass¡± button.
	class PassButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			resetSelected();
			game.makeMove(activePlayer, null);
		}
	}
	
	// an inner class that implements the ActionListener interface. 
	// Implements the actionPerformed() method from the ActionListener interface to handle the enter-message events for the chat input text field.
	class ChatInputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String player = "Player " + Integer.toString(activePlayer) + ": ";
			String input = chatInput.getText();
			
			// unable to send empty message.
			if (! input.isEmpty())
			{
				chatArea.append( player + input + "\n");
				chatInput.setText("");
			}
		}
	}
	
	//an inner class that implements the ActionListener interface. 
	//Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the ¡°Restart¡± menu item.
	class RestartMenuItemListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
			
		}
	}
	
	//an inner class that implements the ActionListener interface. 
	//Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the ¡°Quit¡± menu item.
	class QuitMenuItemListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			Runtime.getRuntime().exit(0);
		}
	}

	/**
	 * Sets the index of the active player.
	 * 
	 * @param activePlayer the index of the active player (i.e., the player who can make a move)
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= playerList.size()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
	}
	

	/**
	 * Redraws the GUI.
	 */
	public void repaint() 
	{
		this.frame.repaint(); 
	}

	/**
	 * Prints the specified string to the GUI massage area.
	 * 
	 * @param msg the string to be printed to the GUI
	 */
	public void printMsg(String msg) {
		this.msgArea.append(msg+"\n");
		this.msgArea.setCaretPosition(this.msgArea.getDocument().getLength());
	}
	
	/**
	 * Print the specified string to a dialog
	 * 
	 * @param msg the string to be printed to the dialog
	 */
	public void printDialog(String msg)
	{
		JOptionPane.showMessageDialog(null,msg );
	}

	/**
	 * Clears the message area of the GUI.
	 */
	public void clearMsgArea() {
		this.msgArea.setText(""); 
	}
	
	/**
	 * Clears the chat area of the GUI.
	 */
	public void clearChatArea()
	{
		this.chatArea.setText("");
	}
	
	/**
	 * Clears the chat input text field of the GUI.
	 */
	public void clearChatInput()
	{
		this.chatInput.setText("");
	}

	/**
	 * a method for resetting the GUI.
	 * (i) reset the list of selected cards; 
	 * (ii) clear the message area; and 
	 * (iii) enable user interactions.
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.clearChatArea();
		this.clearChatInput();
		activePlayer = -1;
		BigTwoDeck deck = new BigTwoDeck();
		deck.initialize();
		deck.shuffle();
		this.game.start(deck);
	}

	/**
	 * Enables user interactions.
	 */
	public void enable() 
	{
		this.bigTwoPanel.setEnabled(true);
		this.playButton.setEnabled(true);
		this.passButton.setEnabled(true);
		this.chatInput.setEditable(true);
		
	}

	/**
	 * Disables user interactions.
	 */
	public void disable() 
	{
		this.bigTwoPanel.setEnabled(false);
		this.playButton.setEnabled(false);
		this.passButton.setEnabled(false);
		this.chatInput.setEditable(false);
		
	}

	/**
	 * Prompts active player to select cards and make his/her move.
	 */
	public void promptActivePlayer() 
	{
	}
	
	/**
	 * Returns an array of indices of the cards selected through the UI.
	 * 
	 * @return an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
	public int[] getSelected()
	{
		int[] cardIdx = null;
		
		int count = 0;
		
		for (int i = 0; i < this.selected.length; i++)
		{
			if (this.selected[i] == true)
				count++;
		}
		
		if ( this.activePlayer != -1 && count > 0)
		{	
			int numOfCards = playerList.get(this.activePlayer).getNumOfCards();
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) 
			{
				if (selected[j]) 
				{
					cardIdx[count] = j;
					count++;
				}
			}
		}
			
		return cardIdx;
	}
	
	/**
	 * Resets the list of selected cards to an empty list.
	 */
	private void resetSelected() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
	}
}
