package breakout.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class Test_Circle {
	Point p00;
	Point p14;
	Point pmin14;
	Circle c1;
	Circle c2;
	

	@BeforeEach
	void setUp() throws Exception{
		p00 = new Point(0,0);
		p14 = new Point(1,4);
		pmin14 = new Point(-1,4);
		c1 = new Circle(p00, 5);
		c2 = new Circle(p14, 6);
		
	}
	
	@Test
	void testCircle() {
		assertEquals(p00, c1.getCenter());
		assertEquals(p14, c2.getCenter());
		assertEquals(5, c1.getDiameter());
		assertEquals(6, c2.getDiameter());	
	}
	
	

}
