package RUTINAPERSONALIZADA;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gimnasio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class personalizarRutinaClase extends Fragment implements RutinasAdaptador.OnRutinaClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    public RutinasAdaptador adaptador;
    private List<Rutina> listaRutinas;
    private FloatingActionButton aniadir;

    public static personalizarRutinaClase newInstance(String param1, String param2) {
        personalizarRutinaClase fragment = new personalizarRutinaClase();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalizar_rutina, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        aniadir = view.findViewById(R.id.btnAniadirEjercicio);
        recyclerView = view.findViewById(R.id.recyclerViewRutinas);
        listaRutinas = new ArrayList<>();
        adaptador = new RutinasAdaptador(getContext(), listaRutinas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptador);
        cargarDatos();

        aniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viajarFormulario = new Intent(view.getContext(), formularioAniadirEntrenoClase.class);
                startActivity(viajarFormulario);
            }
        });

        return view;
    }

    private void cargarDatos() {
        String correo = mAuth.getCurrentUser().getEmail();
        db.collection("rutinasPersonalizadas")
                .document(correo)
                .collection("entrenamientos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Rutina> nuevasRutinas = new ArrayList<>();
                            Set<String> fechasUnicas = new HashSet<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                String rutinaNombre = document.getString("rutina");
                                String fechaFormateada = document.getString("fecha");

                                if (fechasUnicas.contains(fechaFormateada)) {
                                    continue;
                                }

                                fechasUnicas.add(fechaFormateada);

                                List<Map<String, Object>> ejerciciosMap = (List<Map<String, Object>>) document.get("ejercicios");
                                List<ejercicio> ejercicios = new ArrayList<>();
                                for (Map<String, Object> ejercicioMap : ejerciciosMap) {
                                    String nombreEjercicio = (String) ejercicioMap.get("nombreEjercicio");
                                    List<Map<String, Object>> seriesList = (List<Map<String, Object>>) ejercicioMap.get("seriesList");
                                    List<series> series = new ArrayList<>();

                                    for (Map<String, Object> seriesMap : seriesList) {
                                        String numeroSerie = (String) seriesMap.get("numeroSerie");
                                        String peso = (String) seriesMap.get("peso");
                                        String repeticiones = (String) seriesMap.get("repeticiones");
                                        series nuevaSerie = new series(numeroSerie, peso, repeticiones);
                                        series.add(nuevaSerie);
                                    }

                                    ejercicio ejercicioSeleccionado = new ejercicio(nombreEjercicio, series);
                                    ejercicios.add(ejercicioSeleccionado);
                                }

                                Rutina rutina = new Rutina(rutinaNombre, fechaFormateada, ejercicios);
                                nuevasRutinas.add(rutina);
                            }


                            new Handler(Looper.getMainLooper()).post(() -> {
                                listaRutinas.clear();
                                listaRutinas.addAll(nuevasRutinas);
                                adaptador.notifyDataSetChanged();
                            });
                        } else {
                            Toast.makeText(getContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onRutinaClick(int position) {
        Rutina selectedRutina = listaRutinas.get(position);
        Intent intent = new Intent(getContext(), rutinaInformacionClass.class);
        intent.putExtra("rutina_nombre", selectedRutina.getNombre());
        intent.putExtra("rutina_fecha", selectedRutina.getFecha());
        intent.putParcelableArrayListExtra("series", new ArrayList<>(selectedRutina.getEjercicios()));
        startActivity(intent);
    }
}
