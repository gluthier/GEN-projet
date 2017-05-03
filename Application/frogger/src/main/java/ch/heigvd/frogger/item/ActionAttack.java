package ch.heigvd.frogger.item;

import ch.heigvd.frogger.Constants;
import ch.heigvd.frogger.exception.CellAlreadyOccupiedException;

public class ActionAttack implements Actions{
	
	public static enum MoveType {
        LEFT,
        RIGHT,
        DOWN;
    };
    
    private final MoveType move;
    
    public ActionAttack(MoveType move) {
		this.move = move;
	}

	@Override
	public void act(Item i) {
		if(!(i instanceof Player)) {
			throw new IllegalArgumentException("Attack should be done with Player");
		}
		Player player = (Player) i;
		try {
            switch (move) {
                case LEFT:
                    player.setType(Constants.ItemType.SkierLeft);
                    player.moveLeft();
                    break;
                case DOWN:
                    player.setType(Constants.ItemType.Skier);
                    player.moveBottom();
                    break;
                case RIGHT:
                    player.setType(Constants.ItemType.SkierRight);
                    player.moveRight();
                    break;
                default:
                    break;
            }
        } catch (CellAlreadyOccupiedException e) {
            e.printStackTrace();
        }
	}
}
