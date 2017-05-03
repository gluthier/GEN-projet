package ch.heigvd.frogger.item;

public class ActionDefend implements Actions{
	
	private final int row;
	
	public ActionDefend(int row) {
		if(row < 0 || row > 9) {
			throw new IllegalArgumentException("wrong row number");
		}
		
		this.row = row;
	}
	

	@Override
	public void act(Item i) {
		// TODO Auto-generated method stub
		
	}

}
