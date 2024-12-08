package PANTALLA_INTRO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;

import com.example.gimnasio.HomeFragment;
import com.example.gimnasio.MainActivity;
import com.example.gimnasio.R;

public class intro_pantallaClase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_pantalla);

        ConstraintLayout layout = findViewById(R.id.introLayout);
        AnimationDrawable animacion = (AnimationDrawable) layout.getBackground();
        animacion.setEnterFadeDuration(4000);
        animacion.setExitFadeDuration(1500);
        animacion.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(intro_pantallaClase.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        } , 4000);
    }
}