package RUTINAPERSONALIZADA;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gimnasio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class formularioAniadirEntrenoClase extends AppCompatActivity {

    EditText ejercicio, series, repeticiones, peso, kilos;

    String serieNumero, repes, pesoSeleccionado, rutina;

    TextView panelInfo;

    String fechaConcatenada, nameEjercicio;

    int seriesNumero, repesNumero, pesoNumero;

    LinearLayout layoutDatos;

    Button guardar , subirDatabase;
    LocalDateTime fecha;
    personalizarRutinaClase clase;
    Spinner rutinaCombo, numSeriesCombo, repesCombo;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private List<ejercicio> listaEjercicios;
    private List<series> seriesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aniadir_entreno);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        clase = new personalizarRutinaClase();
        ejercicio = findViewById(R.id.etEjercicio);
        guardar = findViewById(R.id.btnAniadir);
        fecha = LocalDateTime.now();
        layoutDatos = findViewById(R.id.linearDatos);
        repesCombo = findViewById(R.id.spinnerRepes);
        kilos = findViewById(R.id.etkilos);
        rutinaCombo = findViewById(R.id.spinnRutina);
        numSeriesCombo = findViewById(R.id.spinnSerie);
        panelInfo = findViewById(R.id.txtEjercicios);
        subirDatabase = findViewById(R.id.btnSubirData);
        numSeriesCombo.setEnabled(false);

        String[] opciones = {"", "ESPALDA , BICEPS ", "PECHO TRICEPS", "BRAZO", "TORSO", "PIERNA"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rutinaCombo.setAdapter(adapter);

        String[] numeroSeries = {"Serie 1", "Serie 2", "Serie 3", "Serie 4", "Serie 5"};

        ArrayAdapter<String> adapterSeries = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numeroSeries);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numSeriesCombo.setAdapter(adapterSeries);

        String[] numeroRepes = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> adapterRepes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numeroRepes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        repesCombo.setAdapter(adapterRepes);

        rutinaCombo.setSelection(0);
        numSeriesCombo.setSelection(0);
        repesCombo.setSelection(0);

        seriesList = new ArrayList<>();
        listaEjercicios = new ArrayList<>();

        subirDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aniadirEjercicio();
            }
        });

        ejercicio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                numSeriesCombo.setSelection(0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rutinaCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    rutina = rutinaCombo.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
                repesCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // Obtiene el texto actual de la TextView
                        if (i > 0) {
                            String textActualizado = panelInfo.getText().toString();
                            repes = repesCombo.getSelectedItem().toString();

                            if (kilos.getText().toString() != "") {
                                pesoSeleccionado = kilos.getText().toString();

                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
        ;

        // Agregar TextWatcher a EditText kilos
        kilos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No necesitamos hacer nada antes de que cambie el texto
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Se ejecuta cuando el texto cambia
                String textActualizado = panelInfo.getText().toString();
                repes = repesCombo.getSelectedItem().toString();
                serieNumero = numSeriesCombo.getSelectedItem().toString();

                if (!charSequence.toString().isEmpty() && !repes.isEmpty() && !serieNumero.isEmpty()) {
                    pesoSeleccionado = charSequence.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No necesitamos hacer nada después de que cambie el texto
            }
        });

        // Agregar TextWatcher a repesCombo (si es necesario manejar texto como números)
        repesCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String repes = repesCombo.getSelectedItem().toString();
                String serieNumero = numSeriesCombo.getSelectedItem().toString();

                if (!repes.isEmpty() && !serieNumero.isEmpty()) {
                    pesoSeleccionado = kilos.getText().toString() + " KG";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        int mes = fecha.getMonthValue(); // Mes (1-12)
        int dia = fecha.getDayOfMonth(); // Día (1-31)

        // Concatenar el mes y el día en un String
        fechaConcatenada = dia + "/" + mes;

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEjercicio = ejercicio.getText().toString();

                // Verificar si los campos de series, repeticiones y peso están vacíos
                if (nameEjercicio.trim().isEmpty() ||
                        serieNumero == null || serieNumero.trim().isEmpty() ||
                        repes == null || repes.trim().isEmpty() ||
                        pesoSeleccionado == null || pesoSeleccionado.trim().isEmpty()) {
                    Toast.makeText(formularioAniadirEntrenoClase.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    guardarLista(rutina, nameEjercicio, pesoSeleccionado, repes, serieNumero);

                } catch (NumberFormatException e) {
                    // En caso de que se intente convertir una cadena no válida
                    Toast.makeText(formularioAniadirEntrenoClase.this, "Por favor, ingrese números válidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void actualizarDatosUI(String rutina) {
        panelInfo.setText(""); // Limpiar la TextView
        for (ejercicio ejercicio : listaEjercicios) {
            panelInfo.append("RUTINA: " + rutina + "\n");
            panelInfo.append("Ejercicio: " + ejercicio.getNombreEjercicio() + "\n");
            for (series serie : ejercicio.getSeriesList()) {
                panelInfo.append("Serie: " + serie.getNumeroSerie() + "\n");
                panelInfo.append("Repeticiones: " + serie.getRepeticiones() + "\n");
                panelInfo.append("Peso: " + serie.getPeso() + "\n");
            }
            panelInfo.append("\n");
        }
    }



    private void aniadirEjercicio() {
        if (listaEjercicios.isEmpty()) {
            Toast.makeText(formularioAniadirEntrenoClase.this, "No hay datos para guardar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el correo del usuario autenticado
        String correo = mAuth.getCurrentUser().getEmail();

        // Crear el documento de entrenamiento con ID único
        db.collection("rutinasPersonalizadas")
                .document(correo)
                .collection("entrenamientos")
                .add(new entrenamiento(rutina, listaEjercicios))
                .addOnSuccessListener(documentReference -> {
                    // Obtenemos el ID del documento recién creado
                    String entrenamientoID = documentReference.getId();

                    // Ahora podemos usar el ID para asociar los ejercicios con el documento
                    db.collection("rutinasPersonalizada")
                            .document(correo)
                            .collection("entrenamientos")
                            .document(entrenamientoID)
                            .set(new entrenamiento(rutina, listaEjercicios))
                            .addOnSuccessListener(aVoid -> {
                                // Mostrar un mensaje de éxito si la operación se completó correctamente
                                Toast.makeText(formularioAniadirEntrenoClase.this, "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Mostrar un mensaje de error si ocurrió un problema al guardar los datos
                                Toast.makeText(formularioAniadirEntrenoClase.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    // Manejar posibles errores al crear el documento
                    Toast.makeText(formularioAniadirEntrenoClase.this, "Error al crear el documento", Toast.LENGTH_SHORT).show();
                });
    }

    private void guardarLista (String rutina, String nombreEjercicio, String peso, String repes, String serieNumero) {
        ejercicio ejercicio = new ejercicio(nombreEjercicio, new ArrayList<series>());
        series serie = new series(peso, repes, serieNumero);
        ejercicio.getSeriesList().add(serie);
        listaEjercicios.add(ejercicio);
        actualizarDatosUI(rutina);
        int posicionActual = numSeriesCombo.getSelectedItemPosition();
        int nuevaPosicion = posicionActual + 1;
        if (nuevaPosicion < numSeriesCombo.getAdapter().getCount()) {
            numSeriesCombo.setSelection(nuevaPosicion);
        } else {
            numSeriesCombo.setSelection(0);
        }
    }

}