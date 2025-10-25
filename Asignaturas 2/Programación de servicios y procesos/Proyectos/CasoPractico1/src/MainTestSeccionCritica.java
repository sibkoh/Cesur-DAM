import java.util.Random;

public class MainTestSeccionCritica {
    public static void main(String[] args) {
        AlmacenPreciosConSeccionCritica almacen = new AlmacenPreciosConSeccionCritica(5);
        Random rnd = new Random();

        // Lanzamos 3 hilos que escriben precios
        for (int i = 0; i < 3; i++) {
            final int hiloId = i;
            new Thread(() -> {
                String hilo = "Hilo-" + hiloId;
                for (int j = 0; j < 5; j++) {
                    int indice = rnd.nextInt(5);
                    float precio = 10 + rnd.nextFloat() * 90;
                    PrecioProducto p = new PrecioProducto("Proveedor-" + hiloId + " escritura-numero " + j, precio);

                    almacen.escribirPrecio(indice, p, hilo);

                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }
            }).start();
        }

        // Esperamos un poco y mostramos resultados finales
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        almacen.mostrarPrecios();
    }
}
