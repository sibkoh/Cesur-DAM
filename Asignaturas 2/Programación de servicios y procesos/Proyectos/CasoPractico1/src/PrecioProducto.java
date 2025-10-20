public class PrecioProducto {
    private String proveedor;
    private float precio;

    public PrecioProducto(String proveedor, float precio) {
        this.proveedor = proveedor;
        this.precio = precio;
    }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    @Override
    public String toString() {
        return proveedor + ": " + precio;
    }
}
