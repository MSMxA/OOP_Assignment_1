package breakout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import breakout.utils.*;

import org.junit.jupiter.api.Test;

class Test_BreakoutState {
	Point p14;
	Point p28;
	Rect r1;
	BlockState b1;
	Circle c1;

	@BeforeEach
	void setUp() throws Exception{
		p14 = new Point(1,4);
		p28 = new Point(2,8);
		
	}
	
	@Test
	void testRemoveDead() {
		;
	}

}
