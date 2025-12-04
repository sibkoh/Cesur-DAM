public class Estudiante {

    private String nombre;
    private int edad;
    private double notaMedia;

    // Constructor
    public Estudiante(String nombre, int edad, double notaMedia) {
        this.nombre = nombre;
        this.edad = edad;
        this.notaMedia = notaMedia;
    }

    // Getter para el nombre
    public String getNombre() {
        return nombre;
    }

    // Getter para la edad
    public int getEdad() {
        return edad;
    }

    // Getter para la nota final
    public double getNotaMedia() {
        return notaMedia;
    }

    // Método toString para representar el estudiante de manera legible
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Edad: " + edad + ", Nota final: " + notaMedia;
    }
}
