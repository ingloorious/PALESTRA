package RUTINAPERSONALIZADA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gimnasio.R;

import java.util.ArrayList;

public class rutinaInformacionClass extends AppCompatActivity {

    Toolbar barra;

    TextView rutina , fecha ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_informacion);

        barra = findViewById(R.id.toolbar);


        Intent intent = getIntent();
        String rutinaNombre = intent.getStringExtra("rutina_nombre");
        String rutinaFecha = intent.getStringExtra("rutina_fecha");

        fecha = findViewById(R.id.txtRutinaFecha);
        rutina = findViewById(R.id.txtRutinaNombre);

        fecha.setText(rutinaFecha);
        rutina.setText(rutinaFecha);

        ArrayList<ejercicio> ejercicios = intent.getParcelableArrayListExtra("series");

        RecyclerView recyclerViewEjercicios = findViewById(R.id.recyclerViewEjercicios);
        recyclerViewEjercicios.setLayoutManager(new LinearLayoutManager(this));

        EjercicioAdaptador adaptador = new EjercicioAdaptador(this, ejercicios);
        recyclerViewEjercicios.setAdapter(adaptador);


        setSupportActionBar(barra);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Notificaciones");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            Drawable arrowDrawable = ContextCompat.getDrawable(this, R.drawable.ic_flecha);
            getSupportActionBar().setHomeAsUpIndicator(arrowDrawable);
        }

        barra.setNavigationOnClickListener(v -> onBackPressed());
    }
}
