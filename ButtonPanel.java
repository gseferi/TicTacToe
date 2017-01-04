

import javax.swing.JButton;
import javax.swing.JPanel;


import java.awt.event.*;

public class ButtonPanel extends JPanel
{

	private ClientSender sender;
	/**
	 * Create buttons for new game and exit
	 * @param model the model of the NoughtsCrosses class
	 */
	public ButtonPanel(NoughtsCrossesModel model)
	{
		super();
		
		JButton reset = new JButton("New Game");
		reset.addActionListener(e -> model.newGame());
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(e -> sender.quit());
		
		add(reset);
		add(exit);
	}

	public void setSender(ClientSender sender) {
		this.sender = sender;
		
	}
}
