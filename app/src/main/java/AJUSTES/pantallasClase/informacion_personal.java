package AJUSTES.pantallasClase;

import android.app.DatePickerDialog;
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
import android.util.Patterns;
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
import org.w3c.dom.Text;

import java.util.Calendar;

import CALCULADORAMACROS.calculadora;
import CALCULADORAMACROS.resultMacros;

import com.example.gimnasio.macrosMain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class informacion_personal extends Fragment {

    Button save, fecha;
    private Toolbar barra;

    EditText etNombre, etCorreoElectronico;
    TextView txtPeso, txtAltura, txtpesoValor, txtAlturaValor, txtValorEdad;
    LinearLayout contenedorAltura, contenedorPeso;
    private int peso;
    private float altura;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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
        barra = view.findViewById(R.id.toolbar);
        etNombre = view.findViewById(R.id.etNombre);
        txtAltura = view.findViewById(R.id.tvValorAltura);
        txtPeso = view.findViewById(R.id.txtPesoPlu);
        txtAlturaValor = view.findViewById(R.id.tvValorAltura);
        txtpesoValor = view.findViewById(R.id.tvValorPeso);
        contenedorAltura = view.findViewById(R.id.linearAltura);
        contenedorPeso = view.findViewById(R.id.linearPeso);
        save = view.findViewById(R.id.btnSave);
        fecha = view.findViewById(R.id.btnSeleccionarFecha);
        txtValorEdad = view.findViewById(R.id.txtValorEdad);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        cargarDatos(txtAlturaValor, txtpesoValor, txtValorEdad);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int anioActual = calendar.get(Calendar.YEAR);
                                // Calcular la diferencia de años
                                int edad = anioActual - year;
                                // Formatear la fecha seleccionada
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                txtValorEdad.setText(String.valueOf(edad));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();

            }
        });

        contenedorPeso.setOnClickListener(view12 -> seleccionarPeso());
        contenedorAltura.setOnClickListener(view13 -> seleccionarAltura());

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(barra);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("Información personal");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable arrowDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_flecha);
            activity.getSupportActionBar().setHomeAsUpIndicator(arrowDrawable);
        }

        barra.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }

    private void seleccionarPeso() {
        final NumberPicker numberPicker = new NumberPicker(getContext());
        numberPicker.setMinValue(30);
        numberPicker.setMaxValue(200);
        numberPicker.setValue(50);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(numberPicker);

        new AlertDialog.Builder(getContext())
                .setTitle("Seleccionar el peso corporal")
                .setView(layout)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    int pesoSeleccionado = numberPicker.getValue();
                    txtpesoValor.setText(String.valueOf(pesoSeleccionado));
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void seleccionarAltura() {
        final NumberPicker numberPicker = new NumberPicker(getContext());
        numberPicker.setMinValue(100);
        numberPicker.setMaxValue(250);
        numberPicker.setValue((int) altura);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(numberPicker);

        new AlertDialog.Builder(getContext())
                .setTitle("Seleccionar Altura")
                .setView(layout)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    int selectedAltura = numberPicker.getValue();
                    txtAlturaValor.setText(String.valueOf(selectedAltura));
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void actualizarDatos() {

        int alt = Integer.parseInt(txtAlturaValor.getText().toString());
        int pesoextraido = Integer.parseInt(txtpesoValor.getText().toString());
        int edadExtraida = Integer.parseInt(txtValorEdad.getText().toString());


        if (mAuth.getCurrentUser() != null) {
            String correo = mAuth.getCurrentUser().getEmail();
            CollectionReference coleccion = db.collection("macros");

            // Consulta para obtener los datos del correo
            coleccion.whereEqualTo("email", correo)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            coleccion.document(document.getId()).update(
                                    "altura", alt,
                                    "peso", pesoextraido,
                                    "edad" ,edadExtraida
                            ).addOnSuccessListener(aVoid -> {
                                Toast.makeText(getActivity(), "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Error al actualizar los datos.", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            if (task.getException() != null) {
                                task.getException().printStackTrace();
                            }
                            Toast.makeText(getActivity(), "Error al obtener los datos del usuario.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void calcular() {
        SharedPreferences prefs = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        peso = prefs.getInt("PESO", 0);
        altura = prefs.getFloat("ALTURA", 0f);
        int edad = prefs.getInt("EDAD", 0);
        String sexo = prefs.getString("SEXO", "");
        String frecuenciaEntreno = prefs.getString("FRECUENCIA", "");
        String nivelActividad = prefs.getString("ACTIVIDAD", "");
        String objetivo = prefs.getString("OBJETIVO", "");

        calculadora calc = new calculadora(edad, peso, sexo, frecuenciaEntreno, nivelActividad, objetivo, altura);
        resultMacros resultados = calc.calcularMacros();

        int caloriasInt = (int) resultados.caloriasDiarias;
        int proteInt = (int) resultados.proteinas;
        int carbosInt = (int) resultados.carbohidratos;
        int grasasInt = (int) resultados.grasas;

        String calorias = String.valueOf(caloriasInt);
        String prote = String.valueOf(proteInt);
        String carbos = String.valueOf(carbosInt);
        String grasas = String.valueOf(grasasInt);

        int total = caloriasInt + proteInt + carbosInt + grasasInt;

        if (total > 0) {
            float porcentajeProte = (proteInt * 100.0f) / total;
            float porcentajeCarbos = (carbosInt * 100.0f) / total;
            float porcentajeCalorias = (caloriasInt * 100.0f) / total;
            float porcentajeGrasas = (grasasInt * 100.0f) / total;

            int protePorcentajeInt = (int) porcentajeProte;
            int carbsPorcentajeInt = (int) porcentajeCarbos;
            int caloriasPorcentajeInt = (int) porcentajeCalorias;
            int grasasPorcentajeInt = (int) porcentajeGrasas;

            String proteNombreConPorc = "Proteínas " + protePorcentajeInt + "%";
            String carboNombreConPorc = "Carbohidratos " + carbsPorcentajeInt + "%";
            String caloriasNombreConPorc = "Calorías " + caloriasPorcentajeInt + "%";
            String grasasNombreConPorc = "Grasas " + grasasPorcentajeInt + "%";

            actualizarGráfico(proteNombreConPorc, caloriasNombreConPorc, carboNombreConPorc, grasasNombreConPorc);
        } else {
            Toast.makeText(getContext(), "Faltan valores para calcular los macros", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarGráfico(String prote, String calorias, String carbo, String grasas) {
        // Configurar los gráficos aquí con los valores calculados
        // Usa una biblioteca de gráficos, como MPAndroidChart, para representar los datos visualmente
    }

    private boolean correoValido(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void cargarDatos(TextView altura, TextView peso, TextView edad) {
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

                                    int edadBBDD = document.getLong("edad").intValue();
                                    float pesoBBDD = document.getDouble("peso").floatValue();
                                    float alturaBBDD = document.getDouble("altura").floatValue();
                                    String sexoBBDD = document.getString("sexo");
                                    String frecuenciaEntrenamientoBBDD = document.getString("frecuenciaEntrenamiento");
                                    String nivelActividadBBDD = document.getString("nivelActividad");
                                    String objetivoBBDD = document.getString("objetivo");

                                    altura.setText(String.valueOf((int) alturaBBDD));
                                    peso.setText(String.valueOf((int)pesoBBDD));
                                    edad.setText(String.valueOf(edadBBDD));

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
    }