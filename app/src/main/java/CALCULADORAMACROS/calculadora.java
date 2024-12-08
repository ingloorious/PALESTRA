package CALCULADORAMACROS;

public class calculadora {

  public static calculadora ResultadosMacros;
  int edad , peso , frecuenciaNum , nivelActividadNum ;
  String sexo , frecuenciaEntrenamiento , nivelActividad , objetivo;
  float altura;


  public calculadora(int edad, int peso, String sexo, String frecuenciaEntrenamiento, String nivelActividad, String objetivo, float altura) {
    this.edad = edad;
    this.peso = peso;
    this.sexo = sexo;
    this.frecuenciaEntrenamiento = frecuenciaEntrenamiento;
    this.nivelActividad = nivelActividad;
    this.objetivo = objetivo;
    this.altura = altura;
  }


    public resultMacros calcularMacros() {
      double tmb;
      if (sexo.equalsIgnoreCase("Masculino")) {
        tmb = 88.362 + (13.397 * peso) + (4.799 * altura) - (5.677 * edad);
      } else {
        tmb = 447.593 + (9.247 * peso) + (3.098 * altura) - (4.330 * edad);
      }

      // Ajustar el TMB por nivel de actividad
      double factorActividad = 1.2;
      switch (nivelActividad.toLowerCase()) {
        case "sedentario":
          factorActividad = 1.2;
          break;
        case "moderada":
          factorActividad = 1.55;
          break;
        case "activo":
          factorActividad = 1.725;
          break;
      }

      // Calorías diarias ajustadas por nivel de actividad
      double caloriasDiarias = tmb * factorActividad;

      // Ajustar según objetivo (perder peso, mantener o ganar masa muscular)
      if (objetivo.equalsIgnoreCase("definicion")) {
        caloriasDiarias -= caloriasDiarias * 0.2;  // Restar un 20% para perder peso
      } else if (objetivo.equalsIgnoreCase("volumen")) {
        caloriasDiarias += caloriasDiarias * 0.2;  // Añadir un 20% para ganar músculo
      }

      if (caloriasDiarias < 1000 || caloriasDiarias > 4000) {
        System.out.println("Advertencia: Calorías diarias fuera de rango: " + caloriasDiarias);
      }

      // Calcular los macros
      double proteinas = peso * 2;  // en gramos
      double grasas = caloriasDiarias * 0.25 / 9;  // 1 gramo de grasa = 9 calorías
      double carbohidratos = (caloriasDiarias - (proteinas * 4 + grasas * 9)) / 4;  // 1 gramo de carbohidrato = 4 calorías

      return new resultMacros(caloriasDiarias, proteinas, grasas, carbohidratos);
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

  public int getFrecuenciaNum() {
    return frecuenciaNum;
  }

  public void setFrecuenciaNum(int frecuenciaNum) {
    this.frecuenciaNum = frecuenciaNum;
  }

  public int getNivelActividadNum() {
    return nivelActividadNum;
  }

  public void setNivelActividadNum(int nivelActividadNum) {
    this.nivelActividadNum = nivelActividadNum;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getFrecuenciaEntrenamiento() {
    return frecuenciaEntrenamiento;
  }

  public void setFrecuenciaEntrenamiento(String frecuenciaEntrenamiento) {
    this.frecuenciaEntrenamiento = frecuenciaEntrenamiento;
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

  public float getAltura() {
    return altura;
  }

  public void setAltura(float altura) {
    this.altura = altura;
  }
}
