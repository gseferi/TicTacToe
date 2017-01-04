

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;


// Gets messages from other clients via the server (by the
// ServerSender thread).

public class ClientReceiver extends Thread {

	private BufferedReader server;
	private NoughtsCrossesModel model;
	private PrintStream toServer;
	private ClientSender sender;
	private JFrame frame;
	/**
	 * 
	 * @param server the server
	 * @param model the model of the NoughtsCrosses class 
	 * @param toServer sends data to the server
	 * @param sender sends messages to the server
	 * @param frame the frame containing the NoughtsCrosses game
	 */
	ClientReceiver(BufferedReader server, NoughtsCrossesModel model, PrintStream toServer, ClientSender sender, JFrame frame) {
		this.server = server;
		this.model = model;
		this.toServer = toServer;
		this.sender = sender;
		this.frame = frame;
	
	}

	public void run() {
		// Print to the user whatever we get from the server:
		try {
			loop:
			while (true) {
				String s = server.readLine();
				if (s != null) {
					if (s.contains("You've been chosen")) {
						System.out.println(s+ " Do you want to accept or reject? (type: Yes <opponentName or No>)");
					}
					else if (s.contains("The opponent is not available")) {
						System.out.println("The opponent is not available \n Please enter a different player");
	
					
						
					}
					else if (s.contains("You are player: ")) {
						model.setIsCross(s.charAt(16)=='1');
						String xs = (s.charAt(16) == '1') ? "Xs" : "Os";
						System.out.println("You are: " + xs);
						
					}
					else if(s.startsWith("s/")){
						frame.setVisible(true);
					}
					else if(s.startsWith("No")) {
						System.out.println("Choose another player or Type List or Quit");
					}
					else if(s.startsWith("Quit")){
						break loop;
					}
					else if (s.charAt(0) == 'm') {
						try {
							System.out.println(s);
							int X = Integer.parseInt("" + s.charAt(2));
							int Y = Integer.parseInt("" + s.charAt(4));
							model.turn2(X, Y);
						} catch (NumberFormatException e) {
							System.err.println("Not valid move");
							System.err.println(e.getMessage());

						}
					} else if(s.charAt(0) == 'o') {
						s = s.substring(2);
						System.out.println("connected players are: \n" + s);
					}
				}

				else {
					server.close(); // Probably no point.
					throw new IOException("Got null from server"); // Caught
																	// below.
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server seems to have died " + e.getMessage());
			System.exit(1); // Give up.
		}
	}
}
