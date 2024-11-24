package REGISTRO;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.gimnasio.R;

public class Registro extends AppCompatActivity {

    EditText nombre , correo , telefono ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.txtNombre);
        correo = findViewById(R.id.txtCorreo);
        telefono = findViewById(R.id.txtTelefono);

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetText(nombre);
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetText(correo);
            }
        });

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetText(telefono);
            }
        });

    }

    public void resetText(EditText text) {
        text.setText("");
    }
}