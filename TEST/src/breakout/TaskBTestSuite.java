package breakout;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import breakout.BlockState;
import breakout.BreakoutState;
import breakout.GameMap;
import breakout.PaddleState;
import breakout.utils.*;

/**
 * Those tests should fail on the provided bad implementaiton, succeed on the model solution.
 */
class TaskBTestSuite {
	

	private Point BR;
	private BlockState ablock;
	private BlockState[] someblocks;
	private Ball aball;
	private Ball[] someballs;
	private PaddleState apad;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	/**
	 * some basic values to help us write tests
	 */
	void setUp() {
		BR = new Point(Constants.WIDTH, Constants.HEIGHT);
		
		ablock = new BlockState(
				new Rect( Constants.ORIGIN, new Point(Constants.BLOCK_WIDTH,Constants.BLOCK_HEIGHT)) );
		someblocks = new BlockState[] { ablock };
		
		apad = new PaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS());
		
		aball =
				new Ball(
					new Circle(
						new Point(BR.getX() / 2 , Constants.HEIGHT / 2)
						, Constants.INIT_BALL_DIAMETER)
					, Constants.INIT_BALL_VELOCITY);
		someballs = new Ball[] { aball };
	}

//	@Test
//	void exampleTest1() {
//		assertThrows( IllegalArgumentException.class,
//				() -> insertcodehere );
//      assertTrue ( 1 == 1 );
//      assertEquals( expected, actual );
//      assertNotEquals( unexpected, actual );
//      assertNotSame( unexpected, actual );
//      abreakoutState.tickDuring( 200 );
//	}
	
	@Test
	void dummyTest() {
		
		assertEquals( 35, aball.dummy() );
	}
	@Test
	void balltest() {
		
		
		Point center = new Point(5,5);
		Point ncenter = new Point(6,7);
		Circle circle = new Circle(center,1);
		Circle circlen = null;
		Vector velocity = new Vector(1,2);
		Vector velocity2 = new Vector(1,2);
		Vector velocityn = null;
		Ball ball = new Ball(circle,velocity);
		assertThrows(IllegalArgumentException.class,() -> {Ball balln = new Ball(circlen,velocityn);});
		assertThrows(IllegalArgumentException.class,() -> {ball.setLocation(circlen);});
		assertThrows(IllegalArgumentException.class,() -> {ball.setVelocity(velocityn);});
		//Exception test in ball class
		
		
	}
	@Test
	void paddletest() {
		Point center1 = new Point(5,5);
		Point center2 = new Point(4,4); 
		Color[] color = {Color.black,Color.blue};
		Color[] ncolor = {Color.black,Color.blue};
		PaddleState paddle1 = new PaddleState(center1,color);
		PaddleState paddle2 = new PaddleState(center2,ncolor);
		assertEquals( paddle1.getPossibleColors()[0].getRGB(), paddle2.getPossibleColors()[0].getRGB());
		assertEquals( color[0].getRGB(), ncolor[0].getRGB());
		color[0] = Color.green;
		assertEquals( paddle1.getPossibleColors()[0].getRGB(), paddle2.getPossibleColors()[0].getRGB());
		assertThrows(IllegalArgumentException.class,() -> {PaddleState paddlen = new PaddleState(null,null);});
		//Representation leaking and Exception test in paddlestate class
		
	}
	//not finished, cause don't know which function need to check for defensive programming, there exists exception and encapsulation in the class.
	@Test
	void breakouttest() {
		Point p1 = new Point(1,2);
		Point p2 = new Point(3,1);
		Point p3 = new Point(2,1);
		Point center = new Point(5000,5000);
		Point center2 = new Point(6000,6000);
		Circle circle = new Circle(center,1);
		Circle circle2 = new Circle(center2,1);
		Vector velocity = new Vector(1,2);
		Vector velocity2 = new Vector(2,2);
		Ball ball = new Ball(circle,velocity);
		Ball ball2 = new Ball(circle2,velocity2);
		Ball[] balls = new Ball[] {ball};
		Point bottomright = new Point(20000,20000);
		Color[] color = {Color.black,Color.blue};
		PaddleState paddle = new PaddleState(center,color);
		Rect loc1 = new Rect(p1,p2);
		Rect loc2 = new Rect(p1,p3);
		BlockState block1 = new BlockState(loc1);
		BlockState block2 = new BlockState(loc2);
		BlockState[] blocks = {block1};
		BreakoutState bs = new BreakoutState(balls,blocks,bottomright,paddle);
		
		
		
		
	}
	


	
}

