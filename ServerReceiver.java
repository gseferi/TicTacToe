

import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

// Gets messages from client and puts them in a queue, for another
// thread to forward to the appropriate client.

public class ServerReceiver extends Thread {
	private String myClientsName;
	private BufferedReader myClient;
	private ClientTable clientTable;
	
	/**
	 * Receives Messages from the client
	 * @param n the client's name
	 * @param c the input from the client
	 * @param t is the table of clients
	 */
	public ServerReceiver(String n, BufferedReader c, ClientTable t) {
		myClientsName = n;
		myClient = c;
		clientTable = t;
	}

	public void run() {
		try {
			String opponent = "";
			while (true) {
				String recipient = myClient.readLine();

				//System.out.println("input from client: " + recipient); // "p/john"
				if (recipient != null) {

					if (recipient.charAt(0) == ('p')) {
						opponent = recipient.substring(2); // "john"
						System.out.println(clientTable.getQueue(opponent));
						if(clientTable.getQueue(opponent).getAvailable()){
						System.out.println("Player: " + opponent);
						String sender = "";// myClient.readLine();
							
						//System.out.println(nicknames.toString());
						if (clientTable.getQueue(opponent)!= null) {
							clientTable.getQueue(opponent).offer(new Message(myClientsName, "You've been chosen"));
						}
						
					
						MessageQueue recipientsQueue = clientTable.getQueue(sender);
						}
						else {
							clientTable.getQueue(myClientsName).offer(new Message(myClientsName, "The opponent is not available"));
						}
					} 
					if (recipient.charAt(0) == 'm') {
						
						// substring "m/1,2" -> "1,2"
						// send to opponent m/
						String move = myClient.readLine();
						System.out.println(move);
						Message message = new Message(myClientsName, "m/" + move);
						MessageQueue msgqueue = clientTable.getQueue(opponent);
						if (msgqueue != null)
							msgqueue.offer(message);
						else
							System.err.println("Message for unexistent client " + opponent + ": " + move);

					}
					if(recipient.equals("Quit")) {
						Message message = new Message(myClientsName, "Quit");
						MessageQueue msgqueue = clientTable.getQueue(myClientsName);
						msgqueue.offer(message);
						while(!msgqueue.isEmpty()) {}
						clientTable.getQueueTable().remove(myClientsName);
						break;
					}
					if(recipient.equals("List")){
						Set<String> nicknames = clientTable.getQueueTable().keySet();
						clientTable.getQueue(myClientsName).offer(new Message(myClientsName, "o/" + nicknames.toString()));
					}
					
					if(recipient.startsWith("Yes")) {
						opponent = recipient.substring(4);
						clientTable.getQueue(myClientsName).setPlayer(0);
						clientTable.getQueue(opponent).setPlayer(1);
						
						System.out.println(myClientsName + " , " +clientTable.getQueue(myClientsName).getPlayer());
						clientTable.getQueue(myClientsName).offer(new Message(myClientsName, "You are player: " + clientTable.getQueue(myClientsName).getPlayer()));
						clientTable.getQueue(opponent).offer(new Message(opponent, "You are player: " + clientTable.getQueue(opponent).getPlayer()));
						clientTable.getQueue(myClientsName).setAvailable(false);
						clientTable.getQueue(opponent).setAvailable(false);
						clientTable.getQueue(myClientsName).offer(new Message(myClientsName, "s/"));
						clientTable.getQueue(opponent).offer(new Message(myClientsName, "s/"));
					}
					if(recipient.startsWith("No")) {
						clientTable.getQueue(myClientsName).offer(new Message(myClientsName, "No"));
						clientTable.getQueue(opponent).offer(new Message(myClientsName, "No"));
						clientTable.getQueue(myClientsName).setAvailable(true);
						clientTable.getQueue(opponent).setAvailable(true);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Something went wrong with the client " + myClientsName + " " + e.getMessage());
			// No point in trying to close sockets. Just give up.
			// We end this thread (we don't do System.exit(1)).
		}

	}
}
