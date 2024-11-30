package AJUSTES.contacto;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.gimnasio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class contactoClase extends Fragment {

    Spinner problemaCombo;

    Toolbar barra;

    EditText descript, nombre;

    Button enviar;

    String descri, nombreUsuario, problema;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public contactoClase() {

    }

    public static contactoClase newInstance(String param1, String param2) {
        contactoClase fragment = new contactoClase();
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
        View view = inflater.inflate(R.layout.fragment_contacto, container, false);
        problemaCombo = view.findViewById(R.id.spnCategoria);
        descript = view.findViewById(R.id.etDescripcion);
        nombre = view.findViewById(R.id.etName);
        enviar = view.findViewById(R.id.btnEnviar);
        barra = view.findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreUsuario = nombre.getText().toString();
                descri = descript.getText().toString();

                problema = problemaCombo.getSelectedItem().toString();

                if (nombreUsuario.isEmpty() || descri.isEmpty() || problema.isEmpty()) {
                    Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    // Enviar datos a Firestore
                    enviarDatos(nombreUsuario, descri, problema);
                }

            }
        });


        String[] opciones = {"Mi cuenta", "funcionalidad", "Sugerencias", "Problemas técnicos", "Otras"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, opciones);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        problemaCombo.setAdapter(adapter);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(barra);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("Contactar");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

            // meter icono de la flecha para ir atras
            Drawable arrowDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_flecha);
            activity.getSupportActionBar().setHomeAsUpIndicator(arrowDrawable);
        }


        return view;
    }


    private void enviarDatos(String name, String descripcion, String categoria) {
        try {
            if (mAuth.getCurrentUser() != null) {
                String userEmail = mAuth.getCurrentUser().getEmail();  // Obtén el email del usuario autenticado
                CollectionReference dbColl = db.collection("reportes");
                datosPeticion datos = new datosPeticion(name, descripcion, categoria, userEmail);
                dbColl.add(datos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Petición enviada con éxito.", Toast.LENGTH_SHORT).show();
                        nombre.setText("");
                        descript.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error al enviar la petición.", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(getActivity(), "Debes estar logueado para enviar una petición.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Ha ocurrido un error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();  // Imprime detalles del error en el logcat para depuración
        }
    }
}