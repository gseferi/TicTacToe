

import javax.swing.JPanel;


import java.awt.BorderLayout;

public class NoughtsCrossesComponent extends JPanel
{
	private NoughtsCrossesModel model;
	private BoardView board;
	private ButtonPanel controls;
	
	/**
	 * It creates the model, the boardView and the buttonPanel and adds observers
	 * @param game
	 */
	public NoughtsCrossesComponent(NoughtsCrosses game)
	{
		super();
		model = new NoughtsCrossesModel(game);
		
		board = new BoardView(model);
		
		controls = new ButtonPanel(model);
		
		model.addObserver(board);
		
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
	}
	public NoughtsCrossesModel getModel() {
		return model;
		
	}
	/**
	 * it changes the sender
	 * @param sender the sender 
	 */
	public void setSender(ClientSender sender)
	{
		board.setSender(sender);
		controls.setSender(sender);
	}
	
}
