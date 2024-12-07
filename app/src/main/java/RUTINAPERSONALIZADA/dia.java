package RUTINAPERSONALIZADA;

public class dia {
    private String fecha;
    private String ejercicio;

    public dia(String fecha, String ejercicio) {
        this.fecha = fecha;
        this.ejercicio = ejercicio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEjercicio() {
        return ejercicio;
    }
}
