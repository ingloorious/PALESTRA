package com.example.gimnasio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import AJUSTES.contacto.datosPeticion;
import CALCULADORAMACROS.calculadora;
import CALCULADORAMACROS.resultMacros;
import SQLITE.BBDD;
import RECOGER_DATOS_MACROS.macro;


public class macrosMain extends Fragment {

    calculadora calc;
    private int peso;
    private float altura;
    private int edad;
    private String sexo, frecuenciaEntreno, nivelActividad, objetivo;

    String carbos, calorias, grasas, prote;
    public PieChart grafico;
    public BarChart barraMacros;
    public TextView caloPorcentaje, carbsPorcentaje, protePorcentaje, grasaPorcentaje;

    BBDD base;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    int colorProteinas;
    int colorCarbohidratos;
    int colorGrasas;
    int colorCalorias;

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
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        base = new BBDD(getActivity());
        Context context = getActivity().getApplicationContext();
        grafico = view.findViewById(R.id.graficoMacros);
        barraMacros = view.findViewById(R.id.graficoBarChart);
        protePorcentaje = view.findViewById(R.id.txtProte);
        caloPorcentaje = view.findViewById(R.id.txtCalorias);
        carbsPorcentaje = view.findViewById(R.id.txtCarbs);
        grasaPorcentaje = view.findViewById(R.id.txtGrasas);

         colorProteinas = ContextCompat.getColor(context, R.color.proteinas);
         colorCarbohidratos = ContextCompat.getColor(context, R.color.carbohidratos);
         colorGrasas = ContextCompat.getColor(context, R.color.grasas);
         colorCalorias = ContextCompat.getColor(context, R.color.calorias);


        SharedPreferences recogerDatos = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);

        if (recogerDatos.contains("PESO") && recogerDatos.contains("ALTURA") &&
                recogerDatos.contains("EDAD") && recogerDatos.contains("SEXO") &&
                recogerDatos.contains("FRECUENCIA") && recogerDatos.contains("ACTIVIDAD") &&
                recogerDatos.contains("OBJETIVO")) {

            peso = recogerDatos.getInt("PESO", 0);
            altura = recogerDatos.getFloat("ALTURA", 0f);
            edad = recogerDatos.getInt("EDAD", 0);
            sexo = recogerDatos.getString("SEXO", "");
            frecuenciaEntreno = recogerDatos.getString("FRECUENCIA", "");
            nivelActividad = recogerDatos.getString("ACTIVIDAD", "");
            objetivo = recogerDatos.getString("OBJETIVO", "");

            calculadora calc = new calculadora(edad, peso, sexo, frecuenciaEntreno, nivelActividad, objetivo, altura);
            resultMacros resultados = calc.calcularMacros();
            GuardarDatos(calc);
            actualizarGraficos(resultados, colorProteinas, colorCarbohidratos, colorCalorias, colorGrasas);

        } else {
            leerDatosYGuardarEnVariables();
        }

        return view;
    }


    public void GuardarDatos(calculadora datos) {
        try {
            if (mAuth.getCurrentUser() != null) {
                String correo = mAuth.getCurrentUser().getEmail();
                CollectionReference coleccion = db.collection("macros");
                coleccion.whereEqualTo("email", correo)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    Toast.makeText(getActivity(), "El correo ya tiene datos guardados.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Si no existen registros, procedemos a guardar
                                    macro data = new macro(
                                            datos.getEdad(),
                                            datos.getPeso(),
                                            datos.getAltura(),
                                            datos.getSexo(),
                                            datos.getFrecuenciaEntrenamiento(),
                                            datos.getNivelActividad(),
                                            datos.getObjetivo(),
                                            correo
                                    );

                                    coleccion.add(data)
                                            .addOnSuccessListener(documentReference -> {
                                                Toast.makeText(getActivity(), "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(getActivity(), "Error al guardar los datos.", Toast.LENGTH_SHORT).show();
                                            });
                                }
                            } else {
                                Exception e = task.getException();
                                if (e != null) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getActivity(), "Error al verificar datos existentes.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Se produjo un error.", Toast.LENGTH_SHORT).show();
        }
    }

        public void leerDatosYGuardarEnVariables() {
            try {
                if (mAuth.getCurrentUser() != null) {
                    String correo = mAuth.getCurrentUser().getEmail(); // Obtén el correo del usuario actual
                    CollectionReference coleccion = db.collection("macros");

                    // Consulta para obtener los datos del correo
                    coleccion.whereEqualTo("email", correo)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        // Si la consulta tiene resultados, guarda los datos en la clase calculadora
                                        DocumentSnapshot document = task.getResult().getDocuments().get(0); // Se asume que hay un único resultado

                                        int edad = document.getLong("edad").intValue();
                                        float peso = document.getDouble("peso").floatValue();
                                        float altura = document.getDouble("altura").floatValue();
                                        String sexo = document.getString("sexo");
                                        String frecuenciaEntrenamiento = document.getString("frecuenciaEntrenamiento");
                                        String nivelActividad = document.getString("nivelActividad");
                                        String objetivo = document.getString("objetivo");

                                        calculadora datoss = new calculadora(edad, (int)peso, sexo, frecuenciaEntrenamiento, nivelActividad, objetivo, altura);
                                        resultMacros resultadosZ = datoss.calcularMacros();
                                        actualizarGraficos(resultadosZ , colorProteinas, colorCarbohidratos, colorCalorias, colorGrasas );
                                    } else {
                                        Toast.makeText(getActivity(), "No se encontraron datos para este correo.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Exception e = task.getException();
                                    if (e != null) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getActivity(), "Error al obtener los datos.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Se produjo un error.", Toast.LENGTH_SHORT).show();
            }
        }

    public void actualizarGraficos(resultMacros resultados, int colorProteinas, int colorCarbohidratos, int colorCalorias, int colorGrasas) {
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
            float porcentajeCalorias = (caloriasInt * 100.0f) / total;
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
            grafico.clearChart();
            barraMacros.clearChart();

            grafico.addPieSlice(new PieModel(proteNombreConPorc, protePorcentajeInt, colorProteinas));
            grafico.addPieSlice(new PieModel(carbsNombreConPorc, carbsPorcentajeInt, colorCarbohidratos));
            grafico.addPieSlice(new PieModel(caloriasNombreConPorc, caloriasPorcentajeInt, colorCalorias));
            grafico.addPieSlice(new PieModel(grasasNombreConPorc, grasasPorcentajeInt, colorGrasas));

            barraMacros.addBar(new BarModel("Proteinas", proteInt, colorProteinas));
            barraMacros.addBar(new BarModel("Carbohidratos", carbosInt, colorCarbohidratos));
            barraMacros.addBar(new BarModel("Calorías", caloriasInt, colorCalorias));
            barraMacros.addBar(new BarModel("Grasas", grasasInt, colorGrasas));

            protePorcentaje.setText(String.format("%d%%", (int) porcentajeProte));
            caloPorcentaje.setText(String.format("%d%%", (int) porcentajeCalorias));
            grasaPorcentaje.setText(String.format("%d%%", (int) porcentajeGrasas));
            carbsPorcentaje.setText(String.format("%d%%", (int) porcentajeCarbos));

        } else {
            Log.e("Grafico", "El total de los valores es 0. No se puede calcular el porcentaje.");
        }

        grafico.startAnimation();
        barraMacros.startAnimation();
    }
    }

