import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultiplicacionTest {
	@Test
	public void testMultiplicacion() {
		Multiplicacion multiplicacion = new Multiplicacion(4, 3);
		assertEquals(12, multiplicacion.multiplicar());
	}
}
