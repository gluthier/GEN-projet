package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.Constants.ItemType;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

public class DynamicObstacle extends Obstacle{
	
	//TODO add a local Timer or a global one who update the position of the obstacle

	public DynamicObstacle(int getPosY, ItemType type) throws CellAlreadyOccupiedException {
			super(2, getPosY, type);
	}
	
}
