package breakout.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


class Test_Vector {
	Vector vec00;
	Vector vec14;
	Vector vecmin14;
	
	@BeforeEach
	void setUp() throws Exception {
		vec00 = new Vector(0,0);
		vec14 = new Vector(1,4);
		vecmin14 = new Vector(-1,4);
	}
	
	@Test
	void testVector() {
		
		assertEquals(0, vec00.getX());
		assertEquals(0, vec00.getY());
		assertEquals(1, vec14.getX());
		assertEquals(4, vec14.getY());
		assertEquals(-1, vecmin14.getX());
		assertEquals(4, vecmin14.getY());
	}
	
	@Test
	void testVectorScaling() {
		assertEquals(vec00, vec00.scaled(1));
		assertEquals(vec00, vec00.scaled(0));
		assertEquals(new Vector(3,12), vec14.scaled(3));
		assertEquals(new Vector(-5, 20), vecmin14.scaled(5));
		assertEquals(new Vector(1, -4), vecmin14.scaled(-1));
	}
	
	@Test
	void testVectorPlus() {
		assertEquals(vec00, vec00.plus(vec00));
		assertEquals(vec14, vec00.plus(vec14));
		assertEquals(new Vector(0,8), vec14.plus(vecmin14));
		
	}
	
	
}
