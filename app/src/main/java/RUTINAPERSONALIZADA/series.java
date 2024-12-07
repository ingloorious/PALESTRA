package RUTINAPERSONALIZADA;

public class series {

    private String peso;
    private String repeticiones;
    private String numeroSerie;

    public series() {}

    public series(String peso, String repeticiones, String numeroSerie) {
        this.peso = peso;
        this.repeticiones = repeticiones;
        this.numeroSerie = numeroSerie;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
}