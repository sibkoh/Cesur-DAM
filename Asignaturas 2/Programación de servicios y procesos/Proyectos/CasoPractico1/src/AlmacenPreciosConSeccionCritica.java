public class AlmacenPreciosConSeccionCritica {
    private PrecioProducto[] producto;

    // Candado para la sección crítica
    private final Object Candado = new Object();

    public AlmacenPreciosConSeccionCritica(int tamaño) {
        this.producto = new PrecioProducto[tamaño];
    }

    // Entrada a sección crítica
    public void entrada_seccion_critica(String hilo) {
        System.out.println(hilo + " está intentando entrar en la sección crítica...");
    }

    // Salida de sección crítica
    public void salida_seccion_critica(String hilo) {
        System.out.println(hilo + " ha salido de la sección crítica");
    }

    // Método para escribir precio en sección crítica
    public void escribirPrecio(int indice, PrecioProducto p, String hilo) {
        entrada_seccion_critica(hilo);

        synchronized (Candado) { // bloque crítico
            System.out.println(hilo + " está escribiendo en la posición " + indice + ": " + p);

            if (indice < 0 || indice >= producto.length) {
                System.out.println(hilo + " intentó un índice fuera de rango");
                return;
            }
            producto[indice] = p;

            System.out.println(hilo + " terminó de escribir en la posición " + indice);
        }

        salida_seccion_critica(hilo);
    }

    // Mostrar estado del array
    public void mostrarPrecios() {
        System.out.println("Estado actual del array:");
        for (int i = 0; i < producto.length; i++) {
            System.out.println("[" + i + "] " + (producto[i] == null ? "null" : producto[i]));
        }
    }
}
