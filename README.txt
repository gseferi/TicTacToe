Commands

	1."List"  -----> the server tells the client who is connected 
	2."Quit"  -----> the client is disconnected
 	3."Yes"   -----> the client accepts the request to start the game
	4."No"	  -----> the client rejectes the request to start the game
	5."request" -----> the server sends a request to another client to ask if he accepts/ rejects to play the game



Running the client

	1.The client is asked to enter a name.
	2.The client is asked to enter a hostname.
	3.The client has to type "list" to see the list of the connected players or "request" followed by the opponent's name.
	4.If the client types "request" and the opponent's name, the opponent will be asked if he wants to accept(by typing "Yes" followed by the opponent's name) or reject(by typing "no").
	5.After that the GUI appears and they can start playing.
	6.If the player types "Quit" then they are disconnected.(Works properly)


I am implementing a Tic-Tac-Toe game with the list/quit/request/accept commands in the terminal, but plays the game in a GUI.

	
Instructions to compile and run the files from the command line

	Running the server
	1.cd NoughtsCrosses
	2.javac *.java
	3.java Server
	
	Running the client
	1.Open a new terminal
	2.cd NoughtsCrosses
	3.java Client <clientName> hostname
	
	Running another client
	1.Open a new terminal
	2.cd NoughtsCrosses
	3.java Client <clientName> hostname
