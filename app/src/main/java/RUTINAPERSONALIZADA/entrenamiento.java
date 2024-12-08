package RUTINAPERSONALIZADA;

import java.util.List;

public class entrenamiento {
    private String rutina;

    String fecha ;
    private List<ejercicio> ejercicios;

    public entrenamiento() {
        // Constructor vacío requerido para Firestore
    }

    public entrenamiento(String rutina ,String fecha, List<ejercicio> ejercicios) {
        this.rutina = rutina;
        this.fecha = fecha;
        this.ejercicios = ejercicios;
    }

    public String getRutina() {
        return rutina;
    }

    public void setRutina(String rutina) {
        this.rutina = rutina;
    }

    public List<ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
