package RECOGER_DATOS_MACROS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Button;

import com.example.gimnasio.MacrosFragment;
import com.example.gimnasio.R;

public class pesoAltura extends Fragment {

    private NumberPicker peso, altura;
    private Button btnContinuar;

    int pesoRecogido, alturaRecogida;
    float alturaReal;

    public pesoAltura() {
        // Constructor vacío requerido para fragmentos
    }

    public static pesoAltura newInstance(String param1, String param2) {
        pesoAltura fragment = new pesoAltura();
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
        // Infla la vista del fragmento
        View view = inflater.inflate(R.layout.fragment_peso_altura, container, false);

        // Inicialización de vistas
        btnContinuar = view.findViewById(R.id.btnContinuar);
        altura = view.findViewById(R.id.altura);
        peso = view.findViewById(R.id.peso);

        // Configuración de los NumberPickers
        peso.setMinValue(30);
        peso.setMaxValue(200);
        peso.setValue(70);

        altura.setMinValue(130);
        altura.setMaxValue(225);
        altura.setValue(175);
        altura.setWrapSelectorWheel(true);

        // Configuración del evento del botón
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recoger los valores
                pesoRecogido = peso.getValue();
                alturaRecogida = altura.getValue();

                // Convertir la altura de cm a metros
                alturaReal = alturaRecogida / 10f;

                //GUARDAR DATOS
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("PESO", pesoRecogido);
                editor.putFloat("ALTURA", alturaReal);
                editor.apply();

                // Realizar la transición al siguiente fragmento
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, new edadSexo()); // Cambiar a tu siguiente fragmento
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;  // Devolver el view inflado correctamente
    }
}
