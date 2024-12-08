package com.example.gimnasio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import REGISTRO.Registro;

public class MainActivity extends AppCompatActivity {

    ImageView gmailButton;
    EditText usuario, password;
    Button login, registro;

    private FirebaseAuth mAuth;

    private static final int RC_SIGN_IN = 1;

    private GoogleSignInClient mGoogleSignInClient;

    SignInButton botonLogeoGoogle;
    TextView mRespu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //referenciar los elementos del diseño
        botonLogeoGoogle = findViewById(R.id.loginGoogle);
        mAuth = FirebaseAuth.getInstance();
        mRespu = findViewById(R.id.txtmRespu);
        login = findViewById(R.id.btnLogin);
        registro = findViewById(R.id.btnRegistro);
        usuario = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAuth = new Intent(MainActivity.this , Registro.class);
                startActivity(goAuth);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = usuario.getText().toString();
                String pass = password.getText().toString();
                loginCorreoContra(mail , pass);

            }
        });

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        botonLogeoGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //error si no se logeo bien
                mRespu.setText(e.getMessage().toString());
            }
        }
    }

    public void loginCorreoContra(String usuarios , String pass) {
        if (usuarios != null && !usuarios.isEmpty() && pass != null && !pass.isEmpty()) {

            mAuth.signInWithEmailAndPassword(usuarios, pass).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // El registro fue exitoso
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    FirebaseUser user = mAuth.getCurrentUser();

                    String correoAcc = user.getEmail();

                    ProviderType proveedor = ProviderType.BASIC;

                    irHome();

                } else {
                    if (task.getException() != null) {
                        String errorMessage;
                        try {
                            throw task.getException(); // Lanza la excepción para manejarla
                        } catch (FirebaseAuthWeakPasswordException e) {
                            errorMessage = "La contraseña es demasiado débil. Usa al menos 6 caracteres.";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            errorMessage = "El correo electrónico está mal formateado.";
                        } catch (FirebaseAuthUserCollisionException e) {
                            errorMessage = "El correo electrónico ya está registrado.";
                        } catch (Exception e) {
                            errorMessage = "Error desconocido: " + e.getMessage();
                        }

                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    ;


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            irHome();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            mRespu.setText(task.getException().toString());
                            updateUI(null);
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {
        user = mAuth.getCurrentUser();
        if (user != null) {
            irHome();

        }

    }

    private void irHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void volverLogin(String email, ProviderType proveedor) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EMAIL", email);
        intent.putExtra("PROVIDER", proveedor);
        startActivity(intent);
        finish();
    }

    public enum ProviderType {
        BASIC,
        GOOGLE
    }

}