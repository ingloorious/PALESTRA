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

import SQLITE.BBDD;

public class nivelActividad extends Fragment {

    RadioGroup nivelAct ;

    RadioButton elegido ;

    String recogerValor;

    int idBotonSeleccionado;

    Button continuar ;

    BBDD base ;

    public nivelActividad() {

    }


    public static nivelActividad newInstance(String param1, String param2) {
        nivelActividad fragment = new nivelActividad();
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
        View view = inflater.inflate(R.layout.fragment_nivel_actividad, container, false);
        base =  new BBDD(getActivity());
        nivelAct = view.findViewById(R.id.opciones);
        continuar = view.findViewById(R.id.btnContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            idBotonSeleccionado = nivelAct.getCheckedRadioButtonId();
            if (idBotonSeleccionado != -1) {
             elegido = view.findViewById(idBotonSeleccionado);
             recogerValor = elegido.getText().toString();

                if (recogerValor.equals("SEDENTARIO (0 a 1 día de actividad física)")) {
                    recogerValor = "SEDENTARIO";
                } else if (recogerValor.equals("MODERADO (2 a 4 días de actividad física)")) {
                    recogerValor = "MODERADO";
                } else if (recogerValor.equals("ACTIVO (5 a 7 días de actividad física)")) {
                    recogerValor = "ACTIVO";
                }

                //GUARDAR DATOS
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ACTIVIDAD", recogerValor);
                editor.apply();



                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, new frecuenciaEntreno()); // Cambiar a tu siguiente fragmento
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                // Si no hay ninguna opción seleccionada
                Toast.makeText(getActivity(), "ERRORR", Toast.LENGTH_SHORT).show();
            }
            }
        });


        return view;
    }

}