package AJUSTES.contacto;

public class datosPeticion {

    private String categoria, nombre, descripcion, email;

    public datosPeticion(String categoria, String nombre, String descripcion, String email) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
