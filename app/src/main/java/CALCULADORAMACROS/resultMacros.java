package CALCULADORAMACROS;

public class resultMacros {

        public double caloriasDiarias;
        public double proteinas;
        public double grasas;
        public double carbohidratos;

        public resultMacros(double caloriasDiarias, double proteinas, double grasas, double carbohidratos) {
            this.caloriasDiarias = caloriasDiarias;
            this.proteinas = proteinas;
            this.grasas = grasas;
            this.carbohidratos = carbohidratos;
        }

        @Override
        public String toString() {
            return "Calorías diarias: " + caloriasDiarias + " kcal\n" +
                    "Proteínas: " + proteinas + " g\n" +
                    "Grasas: " + grasas + " g\n" +
                    "Carbohidratos: " + carbohidratos + " g";
        }
    }

