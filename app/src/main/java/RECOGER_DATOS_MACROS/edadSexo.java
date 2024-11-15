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
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gimnasio.R;


public class edadSexo extends Fragment {

    RadioGroup sexo;
    RadioButton selectedRadioButton;
    NumberPicker edad;
    Button continuar;
    String sexoAsignado;
    int edadAsignada;
    int idSeleccionado;
    public edadSexo() {

    }

    public static edadSexo newInstance(String param1, String param2) {
        edadSexo fragment = new edadSexo();
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
        View view = inflater.inflate(R.layout.fragment_edad_sexo, container, false);
        continuar = view.findViewById(R.id.btnContinuar);

        sexo = view.findViewById(R.id.sexo);
        edad = view.findViewById(R.id.edad);

        edad.setMinValue(10);
        edad.setMaxValue(100);
        edad.setValue(30);
        edad.setWrapSelectorWheel(true);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idSeleccionado = sexo.getCheckedRadioButtonId();
                //recoger el radioButton seleccionado
                if (idSeleccionado != -1) {
                    selectedRadioButton = view.findViewById(idSeleccionado);
                    sexoAsignado = selectedRadioButton.getText().toString();
                    edadAsignada = edad.getValue();

                    //GUARDAR DATOS
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("EDAD", edadAsignada);
                    editor.putString("SEXO", sexoAsignado);
                    editor.apply();

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, new nivelActividad()); // Cambiar a tu siguiente fragmento
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