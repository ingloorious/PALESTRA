package com.example.gimnasio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import CALCULADORAMACROS.calculadora;
import CALCULADORAMACROS.resultMacros;





public class macrosMain extends Fragment {

    calculadora calc;
    private int peso;
    private float altura ;
    private int edad;
    private String sexo , frecuenciaEntreno , nivelActividad , objetivo;

    String carbos , calorias , grasas , prote;
    PieChart grafico ;
    //BarChart barraMacros;

    //List<BarEntry> entries = new ArrayList<>();
    TextView caloPorcentaje , carbsPorcentaje , protePorcentaje , grasaPorcentaje;


    public macrosMain() {

    }


    public static macrosMain newInstance(String param1, String param2) {
        macrosMain fragment = new macrosMain();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_macros_main, container, false);
        grafico = view.findViewById(R.id.graficoMacros);
        //barraMacros = view.findViewById(R.id.graficoBarChart);
        protePorcentaje = view.findViewById(R.id.txtProte);
        caloPorcentaje = view.findViewById(R.id.txtCalorias);
        carbsPorcentaje = view.findViewById(R.id.txtCarbs);
        grasaPorcentaje = view.findViewById(R.id.txtGrasas);


        SharedPreferences recogerDatos = getActivity().getSharedPreferences("DatosUsuario" , Context.MODE_PRIVATE);
        peso = recogerDatos.getInt("PESO", 0);
        altura = recogerDatos.getFloat("ALTURA", 0f);
        edad = recogerDatos.getInt("EDAD", 0);
        sexo = recogerDatos.getString("SEXO", "");
        frecuenciaEntreno = recogerDatos.getString("FRECUENCIA" , "");
        nivelActividad = recogerDatos.getString("ACTIVIDAD" , "");
        objetivo = recogerDatos.getString("OBJETIVO" , "");

        calculadora calc = new calculadora(edad, peso, sexo, frecuenciaEntreno, nivelActividad, objetivo, altura);
        resultMacros resultados = calc.calcularMacros();

        System.out.println(resultados);
        //BORRAR DECIMALES AL PARSEARLO A STRING
        int caloriasInt = (int) resultados.caloriasDiarias;
        int proteInt = (int) resultados.proteinas;
        int carbosInt = (int) resultados.carbohidratos;
        int grasasInt = (int) resultados.grasas;

        calorias = String.valueOf(caloriasInt);
        prote = String.valueOf(proteInt);
        carbos = String.valueOf(carbosInt);
        grasas = String.valueOf(grasasInt);

        int total = caloriasInt + proteInt + carbosInt + grasasInt;

        if (total > 0) {
            // Calcular porcentajes
            float porcentajeProte = (proteInt * 100.0f) / total;
            float porcentajeCarbos = (carbosInt * 100.0f) / total;
            float porcentajeCalorias = (caloriasInt* 100.0f) / total;
            float porcentajeGrasas = (grasasInt * 100.0f) / total;

            int protePorcentajeInt = (int) porcentajeProte;
            int carbsPorcentajeInt = (int) porcentajeCarbos;
            int caloriasPorcentajeInt = (int) porcentajeCalorias;
            int grasasPorcentajeInt = (int) porcentajeGrasas;

            String proteNombreConPorc = "Proteínas " + protePorcentajeInt + "%";
            String carbsNombreConPorc = "Carbohidratos " + carbsPorcentajeInt + "%";
            String caloriasNombreConPorc = "Calorias " + caloriasPorcentajeInt + "%";
            String grasasNombreConPorc = "Grasas " + grasasPorcentajeInt + "%";

            // Añadir datos al gráfico
            grafico.addPieSlice(new PieModel(proteNombreConPorc, protePorcentajeInt, Color.parseColor("#FFA726")));
            grafico.addPieSlice(new PieModel(carbsNombreConPorc, carbsPorcentajeInt, Color.parseColor("#66BB6A")));
            grafico.addPieSlice(new PieModel(caloriasNombreConPorc, caloriasPorcentajeInt, Color.parseColor("#EF5350")));
            grafico.addPieSlice(new PieModel(grasasNombreConPorc, grasasPorcentajeInt, Color.parseColor("#29B6F6")));

            protePorcentaje.setText(String.format("%d%%", (int) porcentajeProte));
            caloPorcentaje.setText(String.format("%d%%", (int) porcentajeCalorias));
            grasaPorcentaje.setText(String.format("%d%%", (int) porcentajeGrasas));
            carbsPorcentaje.setText(String.format("%d%%", (int) porcentajeCarbos));


        } else {
            // Manejo del caso donde el total es 0 (si todos los valores son 0)
            Log.e("Grafico", "El total de los valores es 0. No se puede calcular el porcentaje.");
        }

        grafico.startAnimation();




        return view;
    }
}