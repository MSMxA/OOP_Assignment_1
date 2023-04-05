package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import breakout.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;



class Test_BreakoutState {
	Point p1;
	Point p2;
	Point p3;
	Point center;
	Point center2;
	Circle circle;
	Circle circle2;
	Vector velocity;
	Vector velocity2;
	Ball ball;
	Ball ball2;
	Ball[] balls;
	Point bottomright;
	PaddleState paddle;
	Rect loc1;
	Rect loc2;
	BlockState block1;
	BlockState block2;
	BreakoutState bs;
	
	
	
	@BeforeEach
	void setUp() throws Exception{
	p1 = new Point(1,2);
	p2 = new Point(3,1);
	p3 = new Point(2,1);
	center = new Point(5000,5000);
	center2 = new Point(6000,6000);
	circle = new Circle(center,1);
	circle2 = new Circle(center2,1);
	velocity = new Vector(1,2);
	velocity2 = new Vector(2,2);
	ball = new Ball(circle,velocity);
	ball2 = new Ball(circle2,velocity2);
	balls = new Ball[] {ball};
	bottomright = new Point(20000,20000);
	Color[] color = {Color.black,Color.blue};
	paddle = new PaddleState(center,color);
	loc1 = new Rect(p1,p2);
	loc2 = new Rect(p1,p3);
	block1 = new BlockState(loc1);
	block2 = new BlockState(loc2);
	BlockState[] blocks = {block1};
	bs = new BreakoutState(balls,blocks,bottomright,paddle);
	}
	
	@Test
	void test() {
		assertEquals(bs.isWon(),false);
		assertEquals(bs.isDead(),false);
	}

}
