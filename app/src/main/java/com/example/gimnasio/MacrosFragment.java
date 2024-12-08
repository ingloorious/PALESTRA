package com.example.gimnasio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import RECOGER_DATOS_MACROS.edadSexo;
import RECOGER_DATOS_MACROS.pesoAltura;


public class MacrosFragment extends Fragment {

    Button pedirDatos;

    public MacrosFragment() {

    }

    public static MacrosFragment newInstance(String param1, String param2) {
        MacrosFragment fragment = new MacrosFragment();
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
        View view = inflater.inflate(R.layout.fragment_macros, container, false);
        final View fragmentContainerView = view.findViewById(R.id.fragmentContainerView);
        pedirDatos = view.findViewById(R.id.btnMacros);

        pedirDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentContainerView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = fragmentContainerView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                fragmentContainerView.setLayoutParams(params);

                FragmentTransaction transicion =getParentFragmentManager().beginTransaction();
                transicion.replace(R.id.fragmentContainerView , new pesoAltura());
                transicion.addToBackStack(null);
                transicion.commit();
                pedirDatos.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}