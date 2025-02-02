import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DivisionTest {
	@Test
	public void testDivision() {
		Division division = new Division(10, 2);
		assertEquals(5.0, division.dividir(), 0.001);
	}

	@Test(expected = ArithmeticException.class)
	public void testDivisionPorCero() {
		Division division = new Division(10, 0);
		division.dividir();
	}
}
