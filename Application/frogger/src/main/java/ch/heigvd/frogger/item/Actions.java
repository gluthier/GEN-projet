package ch.heigvd.frogger.item;

/**
 * Define the basic actions doable in the game
 * @author Tony Clavien
 *
 */
public interface Actions {
	
	/** 
	 * Do the actions on an item
	 * @param i the item
	 */
	public void act(Item i);

}
