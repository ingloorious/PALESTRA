package com.example.gimnasio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.gimnasio.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gimnasio.databinding.ActivityHomeBinding;
import com.example.gimnasio.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import SQLITE.BBDD;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    BBDD base;

    private boolean isNavigatingFromAjustes = false; // Flag para detectar la navegación desde Ajustes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        remplazoFragmento(new HomeFragment(), R.anim.slide_to_izquierda,
                R.anim.slide_desde_izquierda,  // Animación de salida
                R.anim.slide_to_izquierda,   // Animación al volver
                R.anim.slide_desde_derecha);
        base = new BBDD(this);

        SharedPreferences recogerDatos = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        int peso = recogerDatos.getInt("PESO", 0);
        float altura = recogerDatos.getFloat("ALTURA", 0f);

        //LIMPIAR DATOS DEL SHARED DEPURAR

        SharedPreferences.Editor editor = recogerDatos.edit();
        editor.clear();  // Elimina todos los datos
        editor.apply();

        //asignar fragmento en funcion al id asignado en el menu
        binding.bottomnavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int enterAnim = 0;
            int exitAnim = 0;
            int popEnterAnim = 0;
            int popExitAnim = 0;

            // Determina el fragmento que debe mostrarse y las animaciones
            if (item.getItemId() == R.id.home) {
                fragment = new HomeFragment();
                enterAnim = R.anim.slide_desde_izquierda;
                exitAnim = R.anim.slide_to_derecha;
                popEnterAnim = R.anim.slide_desde_derecha;
                popExitAnim = R.anim.slide_to_izquierda;
            } else if (item.getItemId() == R.id.macros) {
                if (base.existeFila()) {
                    if (isNavigatingFromAjustes) {
                        fragment = new macrosMain();
                        enterAnim = R.anim.slide_desde_derecha;
                        exitAnim = R.anim.slide_to_izquierda;
                        popEnterAnim = R.anim.slide_desde_izquierda;
                        popExitAnim = R.anim.slide_to_derecha;
                        isNavigatingFromAjustes = false;  // Reset flag after navigation
                    } else {
                        fragment = new macrosMain();
                        enterAnim = R.anim.slide_desde_derecha;
                        exitAnim = R.anim.slide_to_izquierda;
                        popEnterAnim = R.anim.slide_desde_izquierda;
                        popExitAnim = R.anim.slide_to_derecha;
                    }
                } else {
                    fragment = new MacrosFragment();
                    enterAnim = R.anim.slide_desde_derecha;
                    exitAnim = R.anim.slide_to_izquierda;
                    popEnterAnim = R.anim.slide_desde_izquierda;
                    popExitAnim = R.anim.slide_to_derecha;
                }
            } else if (item.getItemId() == R.id.settings) {
                isNavigatingFromAjustes = true;
                fragment = new AjustesFragment();
                enterAnim = R.anim.slide_desde_derecha;
                exitAnim = R.anim.slide_to_izquierda;
                popEnterAnim = R.anim.slide_desde_izquierda;
                popExitAnim = R.anim.slide_to_derecha;
            }

            // Verifica si el fragmento actual es el mismo que el fragmento que se quiere cargar
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


    //metodo para el cambio de fragmento al seleccionar otro icono en la barra inferior
    private void remplazoFragmento(Fragment fragment, int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Configurar las animaciones
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);

        // Reemplazar el fragmento
        fragmentTransaction.replace(R.id.frame_layout, fragment);

        // Añadir a la pila para retroceso
        fragmentTransaction.addToBackStack(null);

        // Confirmar la transacción
        fragmentTransaction.commit();
    }
}