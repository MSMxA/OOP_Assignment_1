package breakout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import breakout.utils.*;

import org.junit.jupiter.api.Test;

class Test_BlockState {
	Point p00;
	Point p14;
	Point pmin1min4;
	
	Rect r1;
	Rect r2;
	
	BlockState b1;
	BlockState b2;

	@BeforeEach
	void setUp() throws Exception{
		p00 = new Point(0,0);
		p14 = new Point(1,4);
		pmin1min4 = new Point(-1,-4);
		r1 = new Rect(p00, p14);
		r2 = new Rect(pmin1min4, p14);
		b1 = new BlockState(r1);
		b2 = new BlockState(r2);
		
	}
	
	@Test
	void blockTest() {
		assertEquals(r1, b1.getLocation());
		assertEquals(r2, b2.getLocation());
	}

}
