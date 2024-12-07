package RUTINAPERSONALIZADA;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.gimnasio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class personalizarRutinaClase extends Fragment {



    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private RutinasAdaptador adaptador;
    private List<Rutina> listaRutinas;
    FloatingActionButton aniadir ;
    public personalizarRutinaClase() {
        // Required empty public constructor
    }


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
        adaptador = new RutinasAdaptador(listaRutinas);
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
        db.collection("rutinasPersonalizada")
                .document(correo)
                .collection("entrenamientos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaRutinas.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                String rutinaNombre = document.getString("rutina"); // Extrae solo el nombre
                                listaRutinas.add(new Rutina(rutinaNombre, null, null));
                            }
                            adaptador.notifyDataSetChanged();
                        } else {
                            // Manejar error
                        }
                    }
                });
    }




}