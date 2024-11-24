package REGISTRO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gimnasio.MainActivity;
import com.example.gimnasio.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Registro extends AppCompatActivity {

    EditText nombre, correo, telefono, password;
    Button registro;
    FirebaseAuth mAuth; // Instancia de FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        nombre = findViewById(R.id.txtCorreo);
        correo = findViewById(R.id.txtCorreo);
        telefono = findViewById(R.id.txtName);
        password = findViewById(R.id.txtContra); // Asegúrate de agregar el campo en el diseño XML
        registro = findViewById(R.id.btnSiguiente);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Configurar listeners
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = correo.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String name = nombre.getText().toString().trim();

                if (validateInputs(email, pass, name)) {
                    registerUser(email, pass);
                }
            }
        });

        nombre.setOnClickListener(view -> resetText(nombre, "Nombre"));
        correo.setOnClickListener(view -> resetText(correo, "Correo Electrónico"));
        telefono.setOnClickListener(view -> resetText(telefono, "Teléfono"));
    }

    private boolean validateInputs(String email, String pass, String name) {
        if (email.isEmpty() || pass.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        irHome();
                        finish(); // Finaliza la actividad después del registro
                    } else {
                        if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                            Toast.makeText(this, "Contraseña débil. Usa al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "El correo ya está registrado.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void resetText(EditText text, String comparacion) {
        if (text.getText().toString().equals(comparacion)) {
            text.setText("");
        }

        }
    public void irHome() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
