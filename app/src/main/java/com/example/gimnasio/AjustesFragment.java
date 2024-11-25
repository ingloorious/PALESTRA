package com.example.gimnasio;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import AJUSTES.ajustesAdapter;
import AJUSTES.ajustesClass;



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

        List<ajustesClass> ajustesList = new ArrayList<>();
        ajustesList.add(new ajustesClass("Información personal", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Notificaciones", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Guías", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Suscripciones", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Configuración de audio", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Ajustes de comida", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Preguntas frecuentes", R.drawable.ic_launcher_foreground));
        ajustesList.add(new ajustesClass("Contáctanos", R.drawable.ic_launcher_foreground));

        ajustesAdapter adapter = new ajustesAdapter(ajustesList, item -> {
            // Manejar clics en los elementos
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}