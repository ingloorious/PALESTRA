package EJERCIOS_SCREEN;

public class excerciseClase {
    private int gifId;
    private String descripcion , consejo  ;

    public excerciseClase(int gifId, String descripcion , String consejo) {
        this.gifId = gifId;
        this.descripcion = descripcion;
        this.consejo = consejo;
    }

    public int getGifId() {
        return gifId;
    }

    public void setGifId(int gifId) {
        this.gifId = gifId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }

    public String getConsejo() {
        return consejo;
    }

    public void setConsejo(String consejo) {
        this.consejo = consejo;
    }
}
