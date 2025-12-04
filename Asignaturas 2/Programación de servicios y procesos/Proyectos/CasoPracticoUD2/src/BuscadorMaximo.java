public class BuscadorMaximo implements Runnable {

    private int[] datos;      // Array completo de temperaturas
    private int inicio;       // Índice de inicio del segmento
    private int fin;          // Índice de fin del segmento
    private int maxLocal;     // Resultado: máximo local encontrado

    // Constructor que recibe el array y los índices de segmento
    public BuscadorMaximo(int[] datos, int inicio, int fin) {
        this.datos = datos;
        this.inicio = inicio;
        this.fin = fin;
        this.maxLocal = Integer.MIN_VALUE; // Inicializamos con el valor más bajo posible
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +
            " buscando entre índices " + inicio + " y " + fin);

        // Recorremos solo la sección asignada a este hilo
        for (int i = inicio; i < fin; i++) {
            if (datos[i] > maxLocal) {
                maxLocal = datos[i];
            }
        }

        System.out.println(Thread.currentThread().getName() +
            " ha terminado. Máximo local = " + maxLocal);
    }

    // Método para obtener el máximo local desde el hilo principal
    public int getMaxLocal() {
        return maxLocal;
    }
}
