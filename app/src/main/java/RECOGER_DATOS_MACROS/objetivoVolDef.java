package RECOGER_DATOS_MACROS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gimnasio.R;
import com.example.gimnasio.macrosMain;

import SQLITE.BBDD;


public class objetivoVolDef extends Fragment {
    RadioGroup opciones ;

    RadioButton seleccion;

    String obtenido;

    int indice;

    Button continuar ;

    BBDD base ;

    public objetivoVolDef() {
        // Required empty public constructor
    }



    public static objetivoVolDef newInstance(String param1, String param2) {
        objetivoVolDef fragment = new objetivoVolDef();
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
        View view = inflater.inflate(R.layout.fragment_objetivo_vol_def ,container, false);
        base = new BBDD(getActivity());
        opciones = view.findViewById(R.id.opciones);
        continuar = view.findViewById(R.id.btnContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indice = opciones.getCheckedRadioButtonId();
                if (indice != -1) {
                    seleccion = view.findViewById(indice);
                    obtenido = seleccion.getText().toString();

                    if (obtenido.equals("VOLUMEN (SUBIR DE PESO)")) {
                        obtenido = "VOLUMEN";
                    } else if (obtenido.equals("DEFINICIÓN (BAJAR DE PESO)")) {
                        obtenido = "DEFINICION";
                    }

                    //GUARDAR DATOS
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("OBJETIVO", obtenido);
                    editor.apply();

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, new macrosMain());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    // Si no hay ninguna opción seleccionada
                    Toast.makeText(getActivity(), "Por favor, selecciona una opción", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}