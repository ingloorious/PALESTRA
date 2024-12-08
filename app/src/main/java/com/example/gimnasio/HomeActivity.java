package com.example.gimnasio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.gimnasio.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gimnasio.databinding.ActivityHomeBinding;
import com.example.gimnasio.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import RECOGER_DATOS_MACROS.frecuenciaEntreno;
import RUTINAPERSONALIZADA.personalizarRutinaClase;
import SQLITE.BBDD;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    BBDD base;

    private boolean isNavigatingFromAjustes = false;

    private boolean isNavigatingFromHome = false;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        remplazoFragmento(new HomeFragment(), R.anim.slide_to_izquierda,
                R.anim.slide_desde_izquierda,
                R.anim.slide_to_izquierda,
                R.anim.slide_desde_derecha);
        base = new BBDD(this);

        SharedPreferences recogerDatos = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        int peso = recogerDatos.getInt("PESO", 0);
        float altura = recogerDatos.getFloat("ALTURA", 0f);



        SharedPreferences.Editor editor = recogerDatos.edit();
        editor.clear();
        editor.apply();


        binding.bottomnavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int enterAnim = 0;
            int exitAnim = 0;
            int popEnterAnim = 0;
            int popExitAnim = 0;

            if (item.getItemId() == R.id.home) {
                isNavigatingFromHome = true;
                fragment = new HomeFragment();
                enterAnim = R.anim.slide_desde_izquierda;
                exitAnim = R.anim.slide_to_derecha;
                popEnterAnim = R.anim.slide_desde_derecha;
                popExitAnim = R.anim.slide_to_izquierda;
            } else if (item.getItemId() == R.id.macros) {
                isNavigatingFromHome = false;
                chequear();
                enterAnim = R.anim.slide_desde_derecha;
                exitAnim = R.anim.slide_to_izquierda;
                popEnterAnim = R.anim.slide_desde_izquierda;
                popExitAnim = R.anim.slide_to_derecha;
            } else if (item.getItemId() == R.id.settings) {
                isNavigatingFromAjustes = true;
                isNavigatingFromHome = false;
                fragment = new AjustesFragment();
                enterAnim = R.anim.slide_desde_derecha;
                exitAnim = R.anim.slide_to_izquierda;
                popEnterAnim = R.anim.slide_desde_izquierda;
                popExitAnim = R.anim.slide_to_derecha;
            } else if (item.getItemId() == R.id.RutinaRegistro) {
                fragment = new personalizarRutinaClase();


                if (isNavigatingFromAjustes) {
                    enterAnim = R.anim.slide_desde_izquierda;
                    exitAnim = R.anim.slide_to_derecha;
                    popEnterAnim = R.anim.slide_desde_derecha;
                    popExitAnim = R.anim.slide_to_izquierda;
                } else if (isNavigatingFromHome) {
                    enterAnim = R.anim.slide_desde_derecha;
                    exitAnim = R.anim.slide_to_izquierda;
                    popEnterAnim = R.anim.slide_desde_izquierda;
                    popExitAnim = R.anim.slide_to_derecha;
                } else {

                    enterAnim = R.anim.slide_desde_derecha;
                    exitAnim = R.anim.slide_to_izquierda;
                    popEnterAnim = R.anim.slide_desde_izquierda;
                    popExitAnim = R.anim.slide_to_derecha;
                }

                isNavigatingFromAjustes = false;
                isNavigatingFromHome = false;
            }



            if (fragment != null && !isFragmentVisible(fragment)) {
                remplazoFragmento(fragment, enterAnim, exitAnim, popEnterAnim, popExitAnim);
            }

            return true;
        });
    }

    private boolean isFragmentVisible(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_layout);
        return currentFragment != null && currentFragment.getClass().equals(fragment.getClass());
    }



    private void remplazoFragmento(Fragment fragment, int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);

        // Reemplazar el fragmento
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Método para verificar datos en Firestore
    private void chequear() {
        String userEmail = mAuth.getCurrentUser().getEmail(); // Obtener el correo electrónico

        if (userEmail == null) {
            Log.e("Firestore", "El correo electrónico del usuario es nulo");
            return;
        }

        // Realizar la consulta en Firestore
        db.collection("macros")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            Fragment nuevoFragment = new macrosMain();

                            // Transición al nuevo fragmento
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, nuevoFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {

                            Fragment nuevoFragment = new MacrosFragment();
                            remplazoFragmento(nuevoFragment, R.anim.slide_desde_derecha, R.anim.slide_to_izquierda,
                                    R.anim.slide_desde_izquierda, R.anim.slide_to_derecha);
                        }
                    } else {
                        Log.e("Firestore", "Error al obtener los documentos", task.getException());
                    }
                });
    }

    public void obtenerTodosLosCorreos() {
        // Acceder a la colección "macros"
        CollectionReference coleccion = db.collection("macros");

        // Obtener todos los documentos de la colección
        coleccion.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documento : task.getResult()) {
                        String correo = documento.getString("correo");

                        if (correo != null) {
                            Log.d("Firestore", "Correo encontrado: " + correo);
                        } else {
                            Log.d("Firestore", "Documento sin campo 'correo': " + documento.getId());
                        }
                    }
                } else {
                    // Si hubo un error, registrar el problema
                    Log.e("Firestore", "Error al obtener correos", task.getException());
                }
            }
        });
    }


}
