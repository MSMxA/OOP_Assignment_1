package breakout;

import breakout.utils.Rect;

/**
 * Represents the state of a block in the breakout.good game.
 *
 * TODO spec
 */
public class BlockState {
	
	/**
	 * TODO spec
	 * Specs by Martijn
	 * @invar | location != null
	 */
	private final Rect location;

	/**
	 * TODO
	 * Method & specs by Martijn
	 * Construct a new Blockstate, represented by a rectangle at a given location
	 * @pre | location != null
	 * 
	 */
	public BlockState(Rect location) {
		this.location = location;
	}

	/**
	 * TODO
	 * Method & Specs by Martijn
	 * returns the location of the Block
	 */
	public Rect getLocation() {
		return location;
	}
	
}
