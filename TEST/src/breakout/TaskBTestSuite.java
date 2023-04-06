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
	private Color[] typicalColors = {Color.green, Color.magenta, Color.orange};
	private Point p14;
	private BreakoutState BS;
	private static final String initMap = """
		       
		       
			 o	       
			###	       
				       
		     =

				""";

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
		BS = new BreakoutState(someballs, someblocks, BR, apad);
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
	
	
	
//NOTE: We did the tests both with new variables in each test as well as the variables in Setup()
// So it might look a bit weird sometimes, but it works 
	
	@Test
	void dummyTest() {
		
		assertEquals(35, aball.dummy() );
	}
	@Test
	void balltest() {
		Point center = new Point(5,5);
		Circle circle = new Circle(center,1);
		Vector velocity = new Vector(1,2);
		Ball ball = new Ball(circle,velocity);
		assertThrows(IllegalArgumentException.class,() -> new Ball(circle, null));
		assertThrows(IllegalArgumentException.class,() -> new Ball(null, velocity));
		assertThrows(IllegalArgumentException.class,() -> {ball.setLocation(null);});
		assertThrows(IllegalArgumentException.class,() -> {ball.setVelocity(null);});
		
		//test getters
		assertEquals(ball.getLocation(), circle);
		assertEquals(ball.getVelocity(), velocity);
		
		
		//test setters
		Ball ball2 = new Ball(circle,velocity);
		Point center2 = new Point(10,10);
		Circle circle2 = new Circle(center2,1);
		ball2.setLocation(circle2);
		assertEquals(ball2.getLocation(), circle2);
		
		Vector velocity2 = new Vector(100,200);
		ball2.setVelocity(velocity2);
		assertEquals(ball2.getVelocity(), velocity2);
		
	}
	
	@Test
	void moveBalltest() {
		Vector velocity = new Vector(1,2);
		aball.move(velocity);
		assertEquals(new Circle(new Point((BR.getX()/2)+1 , (Constants.HEIGHT/2)+2), Constants.INIT_BALL_DIAMETER), aball.getLocation());
		
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
		//assertEquals(paddle1.getLocation(), new Rect(new Point(5-(Constants.PADDLE_WIDTH/2), 5-(Constants.PADDLE_HEIGHT/2)),
		//			new Point(5+(Constants.PADDLE_WIDTH/2), 5+(Constants.PADDLE_HEIGHT/2))));
		assertThrows(IllegalArgumentException.class,() -> {new PaddleState(p14,null);});
		assertThrows(IllegalArgumentException.class,() -> {new PaddleState(null, typicalColors);});
		assertNotNull(apad.getCenter());
		assertNotNull(apad.getPossibleColors());
		}
		
		//Representation leaking and Exception test in paddlestate class
		
	
	@Test
	void breakouttest() {
		Point p1 = new Point(1,2);
		Point p2 = new Point(3,1);
		Rect loc1 = new Rect(p1,p2);
		
		Point center = new Point(5000,5000);
		Circle circle = new Circle(center,1);
		
		Vector velocity = new Vector(1,2);
		Ball ball = new Ball(circle,velocity);
		Ball[] balls = new Ball[] {ball};
		
		Point bottomright = new Point(20000,20000);
		Color[] color = {Color.black,Color.blue};
		PaddleState paddle = new PaddleState(center,color);

		BlockState block1 = new BlockState(loc1);
		BlockState[] blocks = {block1};
		BreakoutState bs = new BreakoutState(balls,blocks,bottomright,paddle);
		bs.movePaddleLeft(1);
		//paddle_vel is (15,0), dus result moet 4985 zijn
		assertEquals(bs.getPaddle().getCenter(),new Point(4985,5000));
		BreakoutState bs2 = new BreakoutState(balls,blocks,bottomright,paddle);
		
		bs2.movePaddleRight(1);
		assertEquals(bs2.getPaddle().getCenter(),new Point(5015,5000));
		
		
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(null, someblocks, BR, apad));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs, null , BR, apad));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs, someblocks, null, apad));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs, someblocks, BR, null));
		assertArrayEquals(someballs, BS.getBalls() );
		assertArrayEquals(someblocks, BS.getBlocks());
		assertEquals(bs2.getBottomRight(), bottomright);
		assertEquals(BS.getPaddle(), apad);
	}
	
	
		
	@Test
	void testPaddleColors() {
		BS.tickDuring(100);
		assertArrayEquals(apad.getPossibleColors(), Constants.TYPICAL_PADDLE_COLORS());
        assertTrue(Arrays.asList(typicalColors).contains(BS.getCurPaddleColor()));
	}
		
	@Test
    public void testBounceWalls() {
		Circle loc = aball.getLocation();
		BreakoutState bs = GameMap.createStateFromDescription(initMap);
		bs.tickDuring(100);
		assertEquals(aball.getLocation(), loc);
    }
	
	
}