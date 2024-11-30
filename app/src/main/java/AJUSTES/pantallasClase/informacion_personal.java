package AJUSTES.pantallasClase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.gimnasio.MacrosFragment;
import com.example.gimnasio.R;

import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.Calendar;

import CALCULADORAMACROS.calculadora;
import CALCULADORAMACROS.resultMacros;

import com.example.gimnasio.macrosMain;


public class informacion_personal extends Fragment {

    Button save ;
    private Toolbar barra;

    macrosMain macrosMainClase;

    EditText etNombre, etCorreoElectronico;

    TextView txtPeso, txtAltura, txtpesoValor, txtAlturaValor;

    LinearLayout contenedorAltura, contenedorPeso;
    NumberPicker npAltura, npPeso;
    DatePicker dpFechaNacimiento;
    int pesoRecogido, alturaRecogida, edad, anio, edadReal;
    float alturaReal;
    private int peso;
    private float altura;
    private String sexo;
    private String frecuenciaEntreno;
    private String nivelActividad;
    private String objetivo;
    private String calorias;
    private String prote;
    private String carbos;
    private String grasas;


    public informacion_personal() {

    }


    public static informacion_personal newInstance(String param1, String param2) {
        informacion_personal fragment = new informacion_personal();
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
        View view = inflater.inflate(R.layout.fragment_informacion_personal, container, false);
        SharedPreferences recogerDatosPrevios = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        peso = recogerDatosPrevios.getInt("PESO", 0);
        altura = recogerDatosPrevios.getFloat("ALTURA", 0f);

        barra = view.findViewById(R.id.toolbar);
        etNombre = view.findViewById(R.id.etNombre);
        etCorreoElectronico = view.findViewById(R.id.etCorreoElectronico);
        txtAltura = view.findViewById(R.id.tvValorAltura);
        txtPeso = view.findViewById(R.id.txtPesoPlu);
        txtAlturaValor = view.findViewById(R.id.tvValorAltura);
        txtpesoValor = view.findViewById(R.id.tvValorPeso);
        contenedorAltura = view.findViewById(R.id.linearAltura);
        contenedorPeso = view.findViewById(R.id.linearPeso);
        save = view.findViewById(R.id.btnSave);

        txtAlturaValor.setText(String.valueOf((int) altura));
        txtpesoValor.setText(String.valueOf(peso));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences actualizarValores = getActivity().getSharedPreferences("DatosUsuario" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = actualizarValores.edit();
                float alt = Float.parseFloat(txtAlturaValor.getText().toString());
                int pesaje = Integer.parseInt(txtPeso.getText().toString());
                editor.putFloat("ALTURA" , alt/100);
                editor.putInt("PESO" , pesaje);
                editor.apply();

                calcular();
            }
        });

        etCorreoElectronico.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {  // Si el EditText pierde el foco
                    String email = etCorreoElectronico.getText().toString();

                    // Validar el correo electrónico
                    if (correoValido(email)) {
                        // Guardar el correo solo si es válido
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("CORREO_ELECTRONICO", email);
                        editor.apply();  // Aplica los cambios
                    } else {
                        // Mostrar un mensaje de error o advertencia si el correo no es válido
                        Toast.makeText(getContext(), "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        contenedorPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NumberPicker numberPicker = new NumberPicker(getContext());
                numberPicker.setMinValue(30);  // Mínimo valor
                numberPicker.setMaxValue(200);  // Máximo valor
                numberPicker.setValue(60);     // Valor por defecto (si lo tienes)

                // Crear un layout para agregar el NumberPicker al diálogo
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(numberPicker);

                // Crear el AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Seleccionar el peso corporal")
                        .setView(layout)  // Establecer el layout con el NumberPicker
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener el valor seleccionado por el usuario
                                int pesoSeleccionado = numberPicker.getValue();

                                // Actualizar el TextView con el valor seleccionado
                                txtpesoValor.setText(String.valueOf(pesoSeleccionado));
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();  // Mostrar el diálogo
            }
        });

        contenedorAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NumberPicker numberPicker = new NumberPicker(getContext());
                numberPicker.setMinValue(100);  // Mínimo valor
                numberPicker.setMaxValue(250);  // Máximo valor
                numberPicker.setValue(175);     // Valor por defecto (si lo tienes)

                // Crear un layout para agregar el NumberPicker al diálogo
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(numberPicker);

                // Crear el AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Seleccionar Altura")
                        .setView(layout)  // Establecer el layout con el NumberPicker
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener el valor seleccionado por el usuario
                                int selectedAltura = numberPicker.getValue();

                                // Actualizar el TextView con el valor seleccionado
                                txtAlturaValor.setText(String.valueOf(selectedAltura));
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();  // Mostrar el diálogo
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(barra);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("Información personal");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

            // meter icono de la flecha para ir atras
            Drawable arrowDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_flecha);
            activity.getSupportActionBar().setHomeAsUpIndicator(arrowDrawable);
        }

        //volver atras
        barra.setNavigationOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });


        return view;
    }

    private void calcular() {

        macrosMainClase = new macrosMain();

        int colorProteinas = ContextCompat.getColor(getContext(), R.color.proteinas);
        int colorCarbohidratos = ContextCompat.getColor(getContext(), R.color.carbohidratos);
        int colorGrasas = ContextCompat.getColor(getContext(), R.color.grasas);
        int colorCalorias = ContextCompat.getColor(getContext(), R.color.calorias);

        SharedPreferences recogerDatos = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        peso = recogerDatos.getInt("PESO", 0);
        altura = recogerDatos.getFloat("ALTURA", 0f);
        edad = recogerDatos.getInt("EDAD", 0);
        sexo = recogerDatos.getString("SEXO", "");
        frecuenciaEntreno = recogerDatos.getString("FRECUENCIA", "");
        nivelActividad = recogerDatos.getString("ACTIVIDAD", "");
        objetivo = recogerDatos.getString("OBJETIVO", "");

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
            macrosMainClase.grafico.clearChart();
            macrosMainClase.grafico.addPieSlice(new PieModel(proteNombreConPorc, protePorcentajeInt, colorProteinas));
            macrosMainClase.grafico.addPieSlice(new PieModel(carbsNombreConPorc, carbsPorcentajeInt, colorCarbohidratos));
            macrosMainClase.grafico.addPieSlice(new PieModel(caloriasNombreConPorc, caloriasPorcentajeInt, colorCalorias));
            macrosMainClase.grafico.addPieSlice(new PieModel(grasasNombreConPorc, grasasPorcentajeInt, colorGrasas));

            macrosMainClase.barraMacros.clearChart();
            macrosMainClase.barraMacros.addBar(new BarModel("Proteinas", proteInt, colorProteinas));
            macrosMainClase.barraMacros.addBar(new BarModel("Carbohidratos", carbosInt, colorCarbohidratos));
            macrosMainClase.barraMacros.addBar(new BarModel("Calorías", caloriasInt, colorCalorias));
            macrosMainClase.barraMacros.addBar(new BarModel("Grasas", grasasInt, colorGrasas));

            macrosMainClase.protePorcentaje.setText(String.format("%d%%", (int) porcentajeProte));
            macrosMainClase.caloPorcentaje.setText(String.format("%d%%", (int) porcentajeCalorias));
            macrosMainClase.grasaPorcentaje.setText(String.format("%d%%", (int) porcentajeGrasas));
            macrosMainClase.carbsPorcentaje.setText(String.format("%d%%", (int) porcentajeCarbos));


        } else {
            // Manejo del caso donde el total es 0 (si todos los valores son 0)
            Log.e("Grafico", "El total de los valores es 0. No se puede calcular el porcentaje.");
        }

        macrosMainClase.grafico.startAnimation();
        macrosMainClase.barraMacros.startAnimation();


    }

    private boolean correoValido(String email) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"; // ejemplo. correo@loquesea.com
        return email.matches(emailPattern);
    }


}