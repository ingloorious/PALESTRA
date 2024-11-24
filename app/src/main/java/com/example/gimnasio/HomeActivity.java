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

    ActivityHomeBinding binding ;

    BBDD base ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        remplazoFragmento(new HomeFragment());
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

            if (item.getItemId() == R.id.home) {
                remplazoFragmento(new HomeFragment());

                return true;
            } else if (item.getItemId() == R.id.macros) {
                if (base.existeFila()) {
                    remplazoFragmento(new macrosMain());
                    return true;
                } else {
                    remplazoFragmento(new MacrosFragment());
                    return true;
                }


            } else if (item.getItemId() == R.id.settings) {
                remplazoFragmento(new AjustesFragment());
                return true;
            }
            return false;
        });
    }

    //metodo para el cambio de fragmento al seleccionar otro icono en la barra inferior
    private void remplazoFragmento (Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();



    }
}