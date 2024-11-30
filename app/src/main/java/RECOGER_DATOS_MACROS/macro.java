package RECOGER_DATOS_MACROS;

public class macro {
    int edad , peso;
    float altura ;
    String sexo , frecuencia , nivelActividad , objetivo , email ;


    public macro(int edad, int peso, float altura, String sexo, String frecuencia, String nivelActividad, String objetivo, String email) {
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.frecuencia = frecuencia;
        this.nivelActividad = nivelActividad;
        this.objetivo = objetivo;
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getNivelActividad() {
        return nivelActividad;
    }

    public void setNivelActividad(String nivelActividad) {
        this.nivelActividad = nivelActividad;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
