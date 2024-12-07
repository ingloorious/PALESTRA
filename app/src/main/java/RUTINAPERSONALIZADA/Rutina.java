package RUTINAPERSONALIZADA;

import java.util.List;

public class Rutina {
    private String id; // El ID del documento de Firestore
    private String nombre; // Nombre de la rutina
    private String fecha; // Fecha en que se hizo la rutina
    private List<ejercicio> ejercicios; // Lista de ejercicios relacionados con esta rutina

    // Constructor vacío necesario para Firestore
    public Rutina() {
    }

    // Constructor que inicializa los campos
    public Rutina(String nombre, String fecha, List<ejercicio> ejercicios) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ejercicios = ejercicios;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
