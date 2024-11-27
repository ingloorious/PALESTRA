package AJUSTES;

public class ajustesClass {
    private final String titulo ;

    private String title;
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

    public String getTitle() {
        return title;
    }
}
