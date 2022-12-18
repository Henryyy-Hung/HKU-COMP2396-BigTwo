import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * The BigTwoClient class implements the NetworkGame interface. 
 * It is used to model a Big Two game client that is responsible 
 * for establishing a connection and communicating with the Big Two game server.
 * 
 * @author Hung Ka Hing UID:3035782750
 *
 */
public class BigTwoClient implements NetworkGame
{
	// a BigTwo object for the Big Two card game.
	private BigTwo game;
	// a BigTwoGUI object for the Big Two card game.
	private BigTwoGUI gui;
	// a socket connection to the game server.
	private Socket sock;	
	// an ObjectOutputStream for sending messages to the server. 
	private ObjectOutputStream oos;	
	// an integer specifying the playerID (i.e., index) of the local player.
	private int playerID;
	// a string specifying the name of the local player.
	private String playerName;	
	// a string specifying the IP address of the game server. 
	private String serverIP;	
	// an integer specifying the TCP port of the game server.
	private int serverPort;
	// an boolean indicate whether the client is connected or not
	private boolean isConnected = false;
	
	/**
	 * a constructor for creating a Big Two client.
	 * 
	 * @param game A reference to a BigTwo object associated with this client 
	 * @param gui A reference to a BigTwoGUI object associated the BigTwo object.
	 */
	public BigTwoClient(BigTwo game, BigTwoGUI gui)
	{
		// set up the client with the BigTwo Game
		this.game = game;
		this.game.setBigTwoClient(this); 
		
		// set up the client with the GUI
		this.gui = gui;
		this.gui.disable();
		
		// allow user to input his/her name
		while (this.getPlayerName() == null || this.getPlayerName() .isEmpty())
		{
			this.setPlayerName( (String) JOptionPane.showInputDialog("Please Enter Your Name:  ") );
		}
		
		// set up server IP and Port for the client
		this.setServerIP("127.0.0.1");
		this.setServerPort(2396);
		// build up the socket connection 
		this.connect();
	}

	@Override
	/**
	 * a method for getting the playerID (i.e., index) of the local player.
	 * 
	 * @return index of the local player
	 */
	public int getPlayerID() 
	{
		return this.playerID;
	}

	@Override
	/**
	 * a method for setting the playerID (i.e., index) of the local player. 
	 * This method should be called from the parseMessage() method when a 
	 * message of the type PLAYER_LIST is received from the game server. 
	 * 
	 * @param playerID  The index of the local player
	 */
	public void setPlayerID(int playerID) 
	{
		this.playerID = playerID;
		
	}

	@Override
	/**
	 * a method for getting the name of the local player.
	 * 
	 * @return name of the local player
	 */
	public String getPlayerName() 
	{
		return this.playerName;
	}

	@Override
	/**
	 * a method for setting the name of the local player. 
	 * 
	 * @param playerName The name of the local player
	 */
	public void setPlayerName(String playerName) 
	{
		this.playerName = playerName;
		
	}

	@Override
	/**
	 * a method for getting the IP address of the game server.
	 * 
	 * @return IP address of the game server
	 */
	public String getServerIP() 
	{
		return this.serverIP;
	}

	@Override
	/**
	 * a method for setting the IP address of the game server.
	 * 
	 * @param serverIP IP address of the game server
	 */
	public void setServerIP(String serverIP) 
	{
		this.serverIP = serverIP;
		
	}

	@Override
	/**
	 * a method for getting the TCP port of the game server.
	 * 
	 * @return TCP port of the game server.
	 */
	public int getServerPort() 
	{
		return this.serverPort;
	}

	@Override
	/**
	 * a method for setting the TCP port of the game server.
	 * 
	 * @param serverPort TCP port of the game server
	 */
	public void setServerPort(int serverPort) 
	{
		this.serverPort = serverPort;
		
	}
	
	/**
	 * method for setting the state of isConnected
	 * 
	 * @param bool is connected or not
	 */
	public void setIsConnected(boolean bool)
	{
		if (bool == true)
		{
			this.isConnected = true;
		}
		else
		{
			this.isConnected = false;
		}
	}
	
	/**
	 * method for getting the state of isConnected
	 * 
	 * @return the state of isConnected
	 */
	public boolean getIsConnected()
	{
		return this.isConnected;
	}

	@Override
	/**
	 *  a method for making a socket connection with the game server. 
	 */
	public void connect() 
	{
		// connect with the server.
		try
		{
			this.sock = new Socket(this.serverIP, this.serverPort);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			this.gui.printDialog("Fail to connect with the server");
			this.disconnect(); 
			return;
		}
		
		// create an ObjectOutputStream for sending messages to the game server
		try
		{
			this.oos = new ObjectOutputStream(sock.getOutputStream());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			this.gui.printDialog("Unable to build ObjectOutputStream for sending messages");
			this.disconnect(); 
			return;
		}
		
		try
		{
			// create a new thread for receiving messages from the game server
			Thread thread = new Thread(new ServerHandler());
			thread.start();
			setIsConnected(true);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			this.gui.printDialog("Unable to create new thread");
			this.disconnect(); 
			return;
		}
		
		// send a message of type JOIN, with playerID and data being -1 and a reference to a string representing the name of the local player, respectively, to the server.
		this.sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, this.playerName));
	}
	
	/**
	 * setup for the disconnected situation
	 */
	public void disconnect()
	{
		this.sock = null;
		this.oos = null;
		this.gui.disable();
		this.gui.diableChat();
		this.isConnected = false;
	}
	
	@Override
	/**
	 * a  method  for  parsing  the  messages received from the game server.  
	 * This  method  should  be  called  from  the  thread responsible for receiving messages from the game server. 
	 * Based on the message type, different actions will be carried out 
	 * (please refer to the general behavior of the client described in the previous section).
	 * 
	 * @param message The  messages received from the game server
	 */
	public void parseMessage(GameMessage message) 
	{
		// parses the message based on it type
		switch (message.getType()) 
		{
		case CardGameMessage.PLAYER_LIST:
			// set the playerID of the local player
			this.setPlayerID( message.getPlayerID());
			this.game.setClientPlayerIdx(this.playerID);
			this.gui.setClientPlayer(this.playerID);
			// set the name for players who already connected to the server
			for (int i = 0; i < game.getNumOfPlayers(); i++)
			{
				String name = ((String[])message.getData())[i];
				if (name != null && name != "")
					game.getPlayerList().get(i).setName( ((String[])message.getData())[i]);
			}
			// update the names in the player list
			game.getPlayerList().get(this.getPlayerID()).setName(this.getPlayerName());
			break;
			
		case CardGameMessage.JOIN:
			// update the names in the player list
			game.getPlayerList().get(message.getPlayerID()).setName((String) message.getData());
			// tell the server that the player is ready
			if (message.getPlayerID() == this.playerID)
			{
				this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			} 
			break;
			
		case CardGameMessage.FULL:
			this.gui.printDialog("The server is full and cannot  join  the  game");
			this.disconnect();
			return;
			
		case CardGameMessage.QUIT:
			String quitPlayer = "Player "+Integer.toString(message.getPlayerID());
			// removes a player by setting his name to empty string
			game.getPlayerList().get(message.getPlayerID()).setName(quitPlayer);
			
			// if a game is in progress
			if (! game.endOfGame())
			{
				this.gui.printMsg(quitPlayer + " has Exit the the game." + "\nWaiting for new player...");
				// stop the game
				this.gui.disable(); 
				// end a message of type READY, with playerID and data being -1 and null, respectively, to the server.
				this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			}
			break;
			
		case CardGameMessage.READY:
			//  display a message in the game message area of the BigTwoGUI that the specified player is ready
			this.gui.printMsg(game.getPlayerList().get(message.getPlayerID()).getName() + " is ready.");
			break;
			
		case CardGameMessage.START:
			// start a new game with the given deck of cards (already shuffled).
			game.start((BigTwoDeck) message.getData());
			break;
			
		case CardGameMessage.MOVE:
			game.checkMove(message.getPlayerID(), (int[]) message.getData());
			break;
			
		case CardGameMessage.MSG:
			String output = (String) message.getData();
			gui.printChat(output);
			break;
			
		default:
			break;
		}
		gui.repaint();
	}

	@Override
	/**
	 * a method  for  sending the specified message to the game server. 
	 * This method should be called whenever the client wants to communicate with the game server or other clients. 
	 * 
	 * @param message The specified message to the game server
	 */
	public void sendMessage(GameMessage message) 
	{
		try
		{
			this.oos.writeObject(message);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			gui.printDialog("Lost connection to the server");
			disconnect();
			return;
		}
	}
	
	/**
	 * an inner class that implements the Runnable interface. 
	 * You should implement the run() method from the Runnable interface 
	 * and create a thread with an instance of this class as its job in the connect() method 
	 * from the NetworkGame interface for receiving messages from the game server. Upon receiving a message, 
	 * the parseMessage() method from the NetworkGame interface should be called to parse the messages accordingly.
	 */
	class ServerHandler implements Runnable
	{

		@Override
		public void run() 
		{
			CardGameMessage msg = null;
			ObjectInputStream ois = null;
				
			try
			{
				ois = new ObjectInputStream(sock.getInputStream());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				gui.printDialog("Unable to build ObjectInputStream for receiving messages");
				disconnect();
				return;
			}
			
			try
			{
				while ( (msg = (CardGameMessage) ois.readObject()) != null)
				{
					parseMessage(msg);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				gui.printDialog("Lost connection to the server");
				disconnect();
				return;
			}
		}
	}
}
