package breakout;

import breakout.utils.Rect;

/**
 * Represents the state of a block in the breakout.good game.
 *
 * TODO spec
 * @immutable
 * @invar | getLocation() != null
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
	 * @post | getLocation().equals(location)
	 * 
	 * 
	 */
	public BlockState(Rect location) {
		this.location = location;
	}

	/**
	 * TODO
	 * Method & Specs by Martijn
	 * returns the location of the Block
	 * @post @ result != null
	 */
	public Rect getLocation() {
		return  location;
	}
	
}
