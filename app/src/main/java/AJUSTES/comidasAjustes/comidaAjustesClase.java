package AJUSTES.comidasAjustes;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.gimnasio.R;


public class comidaAjustesClase extends Fragment {

    Toolbar barra ;



    public comidaAjustesClase() {
    }


    public static comidaAjustesClase newInstance() {
        comidaAjustesClase fragment = new comidaAjustesClase();
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
        View view = inflater.inflate(R.layout.fragment_comida_ajustes,container, false);
        barra = view.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(barra);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("Comida");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

            // meter icono de la flecha para ir atras
            Drawable arrowDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_flecha);
            activity.getSupportActionBar().setHomeAsUpIndicator(arrowDrawable);
        }

        return view;
    }
}