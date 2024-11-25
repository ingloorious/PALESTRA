package AJUSTES;

public class ajustesClass {
    private final String titulo ;
    private final int icono ;

    public ajustesClass(String titulo, int icono) {
        this.titulo = titulo;
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getIcono() {
        return icono;
    }
}
