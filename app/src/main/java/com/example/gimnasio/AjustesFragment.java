package com.example.gimnasio;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import AJUSTES.ajustesAdapter;
import AJUSTES.ajustesClass;
import AJUSTES.comidasAjustes.comidaAjustesClase;
import AJUSTES.contacto.contactoClase;
import AJUSTES.notificaciones.notificacionClass;
import AJUSTES.pantallasClase.informacion_personal;
import AJUSTES.preguntasFRECUENTES.preguntasFrecuentesClase;
import RECOGER_DATOS_MACROS.frecuenciaEntreno;
import RECOGER_DATOS_MACROS.nivelActividad;


public class AjustesFragment extends Fragment {

    Toolbar barra ;

    public AjustesFragment() {
        // Required empty public constructor
    }


    public static AjustesFragment newInstance(String param1, String param2) {
        AjustesFragment fragment = new AjustesFragment();
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
        View view = inflater.inflate(R.layout.fragment_ajustes,container, false);
        barra = view.findViewById(R.id.toolbar);
        barra.setTitle("AJUSTES");
        barra.setTitleTextColor(Color.WHITE);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAjustes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Object> items = new ArrayList<>();
        items.add("Cuenta");
        items.add(new ajustesClass("Información personal", R.drawable.infopersonal));
        items.add("Preferencias");
        items.add(new ajustesClass("Notificaciones", R.drawable.notifcacion));
        items.add(new ajustesClass("Ajustes de comida", R.drawable.ajustes));
        items.add("Soporte");
        items.add(new ajustesClass("Contáctanos", R.drawable.informe));
        items.add(new ajustesClass("Preguntas frecuentes", R.drawable.frecuentes));

        ajustesAdapter adapter = new ajustesAdapter(items, item -> {
            if (item instanceof ajustesClass) {
                ajustesClass ajuste = (ajustesClass) item;



                if ("Notificaciones".equals(ajuste.getTitulo())) {

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new notificacionClass());
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else if ("Ajustes de comida".equals(ajuste.getTitulo())) {

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new comidaAjustesClase());
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else if("Información personal".equals(ajuste.getTitulo())) {

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new informacion_personal());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if("Contáctanos".equals(ajuste.getTitulo())) {

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, new contactoClase());
                transaction.addToBackStack(null);
                transaction.commit();
            }else if("Preguntas frecuentes".equals(ajuste.getTitulo())) {

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new preguntasFrecuentesClase());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }


            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}