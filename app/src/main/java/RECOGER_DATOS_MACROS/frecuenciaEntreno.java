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


public class frecuenciaEntreno extends Fragment {

    RadioGroup opciones ;
    RadioButton seleccion ;
    int indice ;
    Button continuar;
    String obtenido ;


    public frecuenciaEntreno() {
        // Required empty public constructor
    }
    public static frecuenciaEntreno newInstance(String param1, String param2) {
        frecuenciaEntreno fragment = new frecuenciaEntreno();
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
        View view = inflater.inflate(R.layout.fragment_frecuencia_entreno, container, false);
        opciones = view.findViewById(R.id.opciones);
        continuar = view.findViewById(R.id.btnContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indice = opciones.getCheckedRadioButtonId();
                if (indice != -1) {
                    seleccion = view.findViewById(indice);
                    obtenido = seleccion.getText().toString();
                    // Guardar los datos
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("FRECUENCIA", obtenido);
                    editor.apply();


                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, new objetivoVolDef()); // Cambiar a tu siguiente fragmento
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