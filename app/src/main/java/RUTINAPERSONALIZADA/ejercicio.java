package RUTINAPERSONALIZADA;

import java.util.List;

public class ejercicio {

    private String nombreEjercicio;
    private List<series> seriesList;

    public ejercicio(String nombreEjercicio, List<series> seriesList) {
        this.nombreEjercicio = nombreEjercicio;
        this.seriesList = seriesList;
    }

    public ejercicio() {}

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public List<series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<series> seriesList) {
        this.seriesList = seriesList;
    }
}
