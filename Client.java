
// Usage:

//        java Client user-nickname hostname
//
// After initializing and opening appropriate sockets, we start two
// client threads, one to send messages, and another one to get
// messages.
//
// A limitation of our implementation is that there is no provision
// for a client to end after we start it. However, we implemented
// things so that pressing ctrl-c will cause the client to end
// gracefully without causing the server to fail.
//
// Another limitation is that there is no provision to terminate when
// the server dies.

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;

class Client {

	private static Scanner scanner = new Scanner(System.in);
	private static String nickname = "";
	private static String hostname = "";
	private static String opponentName = "";

	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 2) {

			System.out.println("Enter a name: ");
			nickname = scanner.nextLine();
			while (nickname == null || nickname.isEmpty()) {
				System.out.println("Enter your name again");
				nickname = scanner.nextLine();
			}
			System.out.println("Enter a hostname: ");
			hostname = scanner.nextLine();
			while (hostname == null || hostname.isEmpty()) {
				System.out.println("Enter a hostname again");
				hostname = scanner.nextLine();
			}
		} else {
			nickname = args[0];
			hostname = args[1];
		}
		// Open sockets:
		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;

		try {
			server = new Socket(hostname, Port.number);
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}
		toServer.println(nickname);

		NoughtsCrosses game = new NoughtsCrosses();

		NoughtsCrossesComponent comp = new NoughtsCrossesComponent(game);

		JFrame frame = new JFrame("Noughts and Crosses" + nickname);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(comp);

		// Create two client threads:
		ClientSender sender = new ClientSender(toServer, nickname);
		comp.setSender(sender);
		ClientReceiver receiver = new ClientReceiver(fromServer, comp.getModel(), toServer, sender, frame);

		// Run them in parallel:

		sender.start();
		receiver.start();

		// Wait for them to end and close sockets.
		try {

			receiver.join();
			sender.join();
			toServer.close();

			fromServer.close();
			server.close();
		} catch (IOException e) {
			System.err.println("Something wrong " + e.getMessage());
			System.exit(1); // Give up.
		} catch (InterruptedException e) {
			System.err.println("Unexpected interruption " + e.getMessage());
			System.exit(1); // Give up.
		}
		System.exit(0);
	}
}
