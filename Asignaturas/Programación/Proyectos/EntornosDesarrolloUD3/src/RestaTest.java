import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RestaTest {
	@Test
	public void testResta() {
		Resta resta = new Resta(10, 5);
		assertEquals(5, resta.restar());
	}
}
