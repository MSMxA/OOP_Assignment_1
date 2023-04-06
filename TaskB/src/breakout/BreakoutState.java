package breakout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;



/**
 * Represents the current state of a breakout.good game.
 *  
 * @invar | getBalls() != null
 * @invar | getBlocks() != null
 * @invar | getPaddle() != null
 * @invar | getCurPaddleColor() != null
 * @invar | getBottomRight() != null
 * @invar | Constants.ORIGIN.isUpAndLeftFrom(getBottomRight())
 * @invar | Arrays.stream(getBalls()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | getField().contains(getPaddle().getLocation())
 */
public class BreakoutState {

	
	/**
	 * @invar | bottomRight != null
	 * @invar | Constants.ORIGIN.isUpAndLeftFrom(bottomRight)
	 */
	private final Point bottomRight;
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 * @representationObjects
	 */
	private Ball[] balls;	
	/**
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private BlockState[] blocks;
	/**
	 * @invar | paddle != null
	 * @invar | getFieldInternal().contains(paddle.getLocation())
	 */
	private PaddleState paddle;
	/**
	 * @invar | curPaddleColor != null
	 */
	private Color curPaddleColor;

	private final Rect topWall;
	private final Rect rightWall;
	private final Rect leftWall;
	private final Rect[] walls;

	/**
	 * Construct a new BreakoutState with the given balls, blocks, paddle.

	 * @throws IllegalArgumentException | !Constants.ORIGIN.isUpAndLeftFrom(bottomRight)
	 * @throws IllegalArgumentException | !(new Rect(Constants.ORIGIN,bottomRight)).contains(paddle.getLocation())
	 * @throws IllegalArgumentException | !Arrays.stream(blocks).allMatch(b -> (new Rect(Constants.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @throws IllegalArgumentException | !Arrays.stream(balls).allMatch(b -> (new Rect(Constants.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @throws IllegalArgumentException | balls == null
	 * @throws IllegalArgumentException | blocks == null
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * @post | Arrays.equals(getBalls(),balls)
	 * @post | Arrays.equals(getBlocks(),blocks)
	 * @post | getBottomRight().equals(bottomRight)
	 * @post | getPaddle().equals(paddle)
	 */
	
	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		if (balls == null) 
			throw new IllegalArgumentException("balls is null");
		if (blocks == null) 
			throw new IllegalArgumentException("blocks is null");
		if (bottomRight == null) 
			throw new IllegalArgumentException("bottomRight is null");
		if (paddle == null) 
			throw new IllegalArgumentException("paddle is null");
		
		if(!Constants.ORIGIN.isUpAndLeftFrom(bottomRight)) throw new IllegalArgumentException();
		this.bottomRight = bottomRight;
		if(!getFieldInternal().contains(paddle.getLocation())) throw new IllegalArgumentException();
		if(!Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
		if(!Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
	
	    this.balls= new Ball[balls.length];
	    for (int i = 0 ; i < balls.length ; i++) {
	    	this.balls[i] = balls[i];
	    }
		this.blocks = blocks;
		this.paddle = paddle;
		this.curPaddleColor = paddle.getPossibleColors()[0];

		this.topWall = new Rect( new Point(0,-1000), new Point(bottomRight.getX(),0));
		this.rightWall = new Rect( new Point(bottomRight.getX(),0), new Point(bottomRight.getX()+1000,bottomRight.getY()));
		this.leftWall = new Rect( new Point(-1000,0), new Point(0,bottomRight.getY()));
		this.walls = new Rect[] {topWall,rightWall, leftWall };
	}

	/**
	 * Return the balls of this BreakoutState.
	 */
	public Ball[] getBalls() {
		return balls.clone();
	}

	/**
	 * Return the blocks of this BreakoutState. 
	 *
	 * @creates | result
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	/**
	 * Return the paddle of this BreakoutState. 
	 */
	public PaddleState getPaddle() {
		return paddle;
	}
	
	public Color getCurPaddleColor() {
		return curPaddleColor;
	}
	
	/**
	 * randomly choose color within paddle.getPossibleColors() 
	 */
	public void tossPaddleColor() {
		Random rand = new Random();
		int randInt = rand.nextInt( paddle.getPossibleColors().length );
		curPaddleColor = paddle.getPossibleColors()[randInt];
	}

	/**
	 * Return the point representing the bottom right corner of this BreakoutState.
	 * The top-left corner is always at Coordinate(0,0). 
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	/**
	 * LEGIT
	 */
	private Rect getFieldInternal() {
		return new Rect(Constants.ORIGIN, bottomRight);
	}
	
	/**
	 * LEGIT
	 * 
	 * Return a rectangle representing the game field.
	 * @post | result != null
	 * @post | result.getTopLeft().equals(Constants.ORIGIN)
	 * @post | result.getBottomRight().equals(getBottomRight())
	 */
	public Rect getField() {
		return getFieldInternal();
	}
	
	/*
	 * Martijn: I changed this to prevent the ball from respawning at the middle (deleted '/2' after bottomright.getX())
	 */
	private void bounceWalls(Ball ball) {
		for (Rect wall : walls) {
			boolean res = ball.hitRect(wall);
			if (res) {
				boolean hitRightWall = ball.hitRect(rightWall);
				boolean hitLeftWall = ball.hitRect(leftWall);
				boolean hitTopWall = ball.hitRect(topWall);
				
				if( hitRightWall) {
					int newX = bottomRight.getX();
					ball.setLocation( new Circle( new Point(newX, ball.getCenter().getY()) , Constants.INIT_BALL_DIAMETER) );
				}
				if(hitLeftWall) {
					int newX = ball.getLocation().getLeftmostPoint().getX();
					ball.setLocation( new Circle( new Point(newX, ball.getCenter().getY()) , Constants.INIT_BALL_DIAMETER) );
				}
				if(hitTopWall) {
					int newY = ball.getLocation().getTopmostPoint().getY();
					ball.setLocation( new Circle( new Point(newY, ball.getCenter().getY()) , Constants.INIT_BALL_DIAMETER) );
				}
			}
		}
	}
	

	/**
	 * Returns null if ball is below the game field.
	 * Else just returns ball
	 * Method & spces by Martijn (could be made shorter, maybe i'll edit later)
	 * @pre | ball != null
	 * @post | ball.getLocation().getBottommostPoint().getY() <= bottomRight.getY() ? result.equals(ball) : result.equals(null)
	 * TODO
	 */
	private Ball removeDead(Ball ball) {
		if  (ball.getLocation().getBottommostPoint().getY() <= bottomRight.getY()){
			return ball;
			}
		else{
			return null;
		}
	}

	/**
	 * LEGIT
	 */
	private void clampBall(Ball b) {
		Circle loc = getFieldInternal().constrain(b.getLocation());
		b.setLocation(loc);
	}
	
	/**
	 * 
	 * Martijn: 'I changed the movePaddleRight(200) to (0), because it makes no sense to move the paddle when you hit a block
	 */
	private void collideBallBlocks(Ball ball) {
		for (BlockState block : blocks) {
			boolean res = ball.hitRect( block.getLocation() );
			if (res) {
				if (ball.getCenter().getX() <= bottomRight.getX() / 2) {
					movePaddleRight(0);
				}
				else {
					movePaddleLeft(0);
				}
				removeBlock(block);
			}
		}
	}

	/**
	 * 
	 * attempted by Renkun(not sure how to change the color every collide)
	 * Vector new_velocity = new Vector(ball.getVelocity().getX(),-ball.getVelocity().getY());
			ball.setVelocity(new_velocity);
			tossPaddleColor();
			paddle = new PaddleState(paddle.getCenter(),curPaddleColor);
			
	 * Martijn: "I think you just need to change the color, everything else is done by 'bounceBallsOnPaddle()'
	 * I tried this and it works in-game :)
	 * 
	 * @pre | paddleVel != null
	 * @pre | ball != null
	 * TODO
	 */
	private void collideBallPaddle(Ball ball, Vector paddleVel) {
		boolean changed = ball.hitPaddle(paddle.getLocation(), paddleVel);
		if (changed == true) {
			tossPaddleColor();
		}		
	}

	/**
	 * LEGIT
	 */
	private void removeBlock(BlockState block) {
		ArrayList<BlockState> nblocks = new ArrayList<BlockState>();
		for( BlockState b : blocks ) {
			if(b != block) {
				nblocks.add(b);
			}
		}
		blocks = nblocks.toArray(new BlockState[] {});
	}

	/**
	 * LEGIT
	 * 
	 * Move all moving objects one step forward.
	 * 
	 * @pre | paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 * 
	 * @pre | elapsedTime >= 0
	 * @pre | elapsedTime <= Constants.MAX_ELAPSED_TIME
	 * 
	 * @mutates | this
	 * @mutates | ...getBalls()
	 */
	public void tick(int paddleDir, int elapsedTime) {
		stepBalls(elapsedTime); //move balls
		bounceBallsOnWalls(); //change some ball speeds
		removeDeadBalls();
		bounceBallsOnBlocks(); //change some ball speeds
		bounceBallsOnPaddle(paddleDir); //change some ball speeds
		clampBalls(); //balls must remain within the field
		balls = Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new);
	}
	
	/**
	 * LEGIT
	 * 
	 * ticking by 20ms increments
	 */
	public void tickDuring(int elapsedTime) {
		for (int i = 0 ; i + 20 <= elapsedTime ; i += 20) {
			tick(0, 20);
		}
		if( elapsedTime % 20 != 0) { 
		  tick(0, elapsedTime % 20);
		}
	}

	/**
	 * LEGIT
	 */
	private void clampBalls() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				clampBall(balls[i]);
			}		
		}
	}

	/**
	 * LEGIT
	 * 
	 * @pre | paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 */
	private void bounceBallsOnPaddle(int paddleDir) {
		Vector paddleVel = Constants.PADDLE_VEL.scaled(paddleDir);
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				collideBallPaddle(balls[i], paddleVel);
			}
		}
	}

	/**
	 * LEGIT
	 */
	private void bounceBallsOnBlocks() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				collideBallBlocks(balls[i]);
			}
		}
	}

	/**
	 * LEGIT
	 */
	private void removeDeadBalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = removeDead(balls[i]);
		}
	}

	/**
	 * LEGIT
	 */
	private void bounceBallsOnWalls() {
		for(int i = 0; i < balls.length; ++i) {
			bounceWalls(balls[i]);
		}
	}

	/**
	 * LEGIT
	 */
	private void stepBalls(int elapsedTime) {
		for(int i = 0; i < balls.length; ++i) {
			balls[i].move(balls[i].getVelocity().scaled(elapsedTime));
		}
	}


	/**
	 * Move the paddle right.
	 */
	public void movePaddleRight(int elapsedTime) {
		Point ncenter = paddle.getCenter().plus(Constants.PADDLE_VEL.scaled(elapsedTime));
		this.paddle = new PaddleState(
				getField().minusMargin(Constants.PADDLE_WIDTH/2,0).constrain(ncenter),
				this.paddle.getPossibleColors()
			);
	}

	/**
	 * Move the paddle left.
	 */
	public void movePaddleLeft(int elapsedTime) {
		Point ncenter = paddle.getCenter().plus(Constants.PADDLE_VEL.scaled(-elapsedTime));
		this.paddle = new PaddleState(
				getField().minusMargin(Constants.PADDLE_WIDTH/2,0).constrain(ncenter),
				this.paddle.getPossibleColors()
			);
	}

	/**
	 * method by Renkun, edit by Martijn
	 * TODO
	 * @post | result == (getBlocks().length == 0)
	 */
	public boolean isWon() {
		if (blocks.length == 0) {
			return true;
			}
		else {
			return false;
		}
	}
	

	/**
	 * method by Renkun, edit by Martijn
	 * TODO
	 * @post | result == (getBalls().length == 0)
	 */
	public boolean isDead() {
		if (balls.length == 0) {
		return true;
		}
		else {
			return false;	
		}
	}
}
