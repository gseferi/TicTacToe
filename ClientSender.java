

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


// Repeatedly reads recipient's nickname and text from the user in two
// separate lines, sending them to the server (read by ServerReceiver
// thread).

public class ClientSender extends Thread {

  private String nickname;
  private PrintStream server;
  private String opponent;
  private boolean running = true;
 
  public void run() {
	  	System.out.println("Choose a player by typing List or (request <opponentName>)");	
	    Scanner scanner = new Scanner(System.in);
	    
	  while(running ){
		  String st = scanner.nextLine();
		  if(st.startsWith("request")) {
			  opponent = st.substring(8);
			  server.println("p/"+opponent);
		  }
		  else if(st.startsWith("Quit")) {
			  server.println("Quit");
			  break;
		  }
		  else if(st.startsWith("List")){
			  server.println("List");
		  }
		  else if(st.startsWith("Yes")){
			  server.println(st);
			  opponent = st.substring(4);
		  }
		  else if(st.startsWith("No")){
			  server.println("No"); 
		  }
		  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  }


  ClientSender(PrintStream server, String nickname) {
    this.nickname = nickname;
    this.server = server;

  }
  /**
   * it sends the move to the server
   * @param X the row
   * @param Y the column
   */
  public void sendMove(int X, int Y){
  
	  server.println("m/" + opponent);
	  server.println( X + "," + Y);
  }
  
  /**
   * it changes the opponent
   * @param opponent the opponent
   */
  public void setOpponent(String opponent)
  {
	  this.opponent = opponent;
	  server.println(opponent);
  }

  /**
   * it disconnects the client
   */
  public void quit() {
	 server.println("Quit");
	 running = false;
	
  }
 /**
  * it changes the running
  * @param b the value running is changed to
  */
  public void setRunning(boolean b) {
	this.running = b;
	
}
}


