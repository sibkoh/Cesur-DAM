import java.util.Random;

public class MainTemperaturas {

    public static void main(String[] args) {

        // 1. Simulación de temperaturas
        int tamaño = 3650;
        int[] temperaturas = new int[tamaño];
        Random rnd = new Random();

        for (int i = 0; i < tamaño; i++) {
            temperaturas[i] = rnd.nextInt(81) - 30; // Generamos valores entre -30 y 50
        }

        System.out.println("Array de temperaturas generado correctamente.\n");

        // 2. Configuración de 3 hilos
        int numHilos = 3;
        Thread[] hilos = new Thread[numHilos];
        BuscadorMaximo[] tareas = new BuscadorMaximo[numHilos];

        int tamañoSegmento = tamaño / numHilos;

        // 3. Creación y lanzamiento de hilos
        for (int i = 0; i < numHilos; i++) {
            int inicio = i * tamañoSegmento;
            int fin = (i == numHilos - 1) ? tamaño : inicio + tamañoSegmento;

            tareas[i] = new BuscadorMaximo(temperaturas, inicio, fin);
            hilos[i] = new Thread(tareas[i], "Hilo-" + i);
            hilos[i].start();
        }

        // 4. Espera a que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 5. Cálculo del máximo global
        int maximoGlobal = Integer.MIN_VALUE;

        for (BuscadorMaximo tarea : tareas) {
            if (tarea.getMaxLocal() > maximoGlobal) {
                maximoGlobal = tarea.getMaxLocal();
            }
        }

        System.out.println("\n===============================");
        System.out.println("Temperatura máxima en 10 años: " + maximoGlobal + "°C");
        System.out.println("===============================\n");
    }
}
