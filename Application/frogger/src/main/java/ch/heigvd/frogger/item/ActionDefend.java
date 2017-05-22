package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.GameController;

/**
 * Define a Defensive Action for Player 2
 * @author Tony Clavien
 *
 */
public class ActionDefend implements Actions{
	
	private final int row;
	
	public ActionDefend(int row) {
		if(row < 0 || row > 9) {
			throw new IllegalArgumentException("wrong row number");
		}
		
		this.row = row;
	}
	

	@Override
	public void act() {
		try {
			GameController.getInstance().addDynamicObstacle(Constants.OBSTACLE_ROW.get(row));
		} catch (Exception e) {
			// TODO: manage exception
			e.printStackTrace();
		}
	}

}
