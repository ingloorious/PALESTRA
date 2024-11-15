package com.example.gimnasio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import CALCULADORAMACROS.calculadora;


public class macrosMain extends Fragment {

    calculadora calc;
    private int peso;
    private float altura;
    private int edad;
    private String sexo , frecuenciaEntreno , nivelActividad , objetivo;



    public macrosMain() {

    }


    public static macrosMain newInstance(String param1, String param2) {
        macrosMain fragment = new macrosMain();
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
        View view = inflater.inflate(R.layout.fragment_macros_main, container, false);

        SharedPreferences recogerDatos = getActivity().getSharedPreferences("DatosUsuario" , Context.MODE_PRIVATE);
        peso = recogerDatos.getInt("PESO", 0);
        altura = recogerDatos.getFloat("ALTURA", 0f);
        edad = recogerDatos.getInt("EDAD", 0);
        sexo = recogerDatos.getString("SEXO", "");
        frecuenciaEntreno = recogerDatos.getString("FRECUENCIA" , "");
        nivelActividad = recogerDatos.getString("ACTIVIDAD" , "");
        objetivo = recogerDatos.getString("OBJETIVO" , "");

        calc = new calculadora(edad , peso , sexo , frecuenciaEntreno , nivelActividad, objetivo , altura);
        String resultado = calc.calcularMacros();

        return view;
    }
}