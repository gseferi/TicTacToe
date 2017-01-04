

import java.net.*;
import java.io.*;

// Continuously reads from message queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread {
  private MessageQueue queue;
  private PrintStream client;

  /**
   * 
   * @param q is the queue of messages to be sent to the client
   * @param c the output that goes to the client
   */
  public ServerSender(MessageQueue q, PrintStream c) {
    queue = q;   
    client = c;
  }

  public void run() {
    while (true) {
      Message msg = queue.take();
      client.println(msg);
    }
  }
}
