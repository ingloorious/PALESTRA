package EJERCIOS_SCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.gimnasio.R;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ejercicios extends AppCompatActivity {

    private ViewPager2 viewPager;
    private adaptadorEjercicios adapter;
    private List<excerciseClase> ejerciciosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        //RECIBIR EL TIPO DE DATO PASADO POR EL INTENT
        String ejercicioTipo = getIntent().getStringExtra("ejercicioTipo");
        viewPager = findViewById(R.id.viewPager);

        ejerciciosList = new ArrayList<>();

        if ("PECHO".equals(ejercicioTipo)) {
            // Ejercicios para PECHO
            ejerciciosList.add(new excerciseClase(R.drawable.banca, "Press banca plano",
                    "Consejos\n1. Mantén los pies firmes en el suelo.\n2. Baja el peso de forma controlada.\n3. No dejes que la barra rebote en tu pecho."));
            ejerciciosList.add(new excerciseClase(R.drawable.mancuernas, "Inclinado con mancuernas",
                    "Consejos\n1. Mantén la espalda bien apoyada en el banco.\n2. Realiza el movimiento de forma lenta y controlada.\n3. Evita arquear demasiado la espalda."));
            ejerciciosList.add(new excerciseClase(R.drawable.polealta, "Polea alta para pecho inferior",
                    "Consejos\n1. Mantén los codos ligeramente flexionados.\n2. Controla el movimiento de subida y bajada.\n3. No uses demasiado peso, ya que se pierde la forma."));
            ejerciciosList.add(new excerciseClase(R.drawable.pecdeck, "Pec Deck",
                    "Consejos\n1. Mantén el torso recto en todo momento.\n2. No bloquees los codos al final del movimiento.\n3. Evita movimientos bruscos."));
            ejerciciosList.add(new excerciseClase(R.drawable.extenssiontriceps, "Extensión de tríceps en polea",
                    "Consejos\n1. Mantén los codos pegados al cuerpo.\n2. No dejes que la polea te jale el brazo.\n3. Evita hacer un movimiento de balanceo."));
            ejerciciosList.add(new excerciseClase(R.drawable.frances, "Press francés",
                    "Consejos\n1. Baja la barra hasta la frente sin mover los codos.\n2. Mantén los codos fijos.\n3. No levantes el peso rápidamente, controla el movimiento."));
            ejerciciosList.add(new excerciseClase(R.drawable.elevaciones, "Elevaciones laterales",
                    "Consejos\n1. Realiza el movimiento sin balancear el cuerpo.\n2. Mantén los codos ligeramente doblados.\n3. No uses un peso excesivo que no puedas controlar."));
            ejerciciosList.add(new excerciseClase(R.drawable.pressmilitar, "Press militar",
                    "Consejos\n1. Mantén los pies firmes en el suelo.\n2. No arquees la espalda al levantar el peso.\n3. Evita usar el impulso para levantar el peso."));
        } else if ("ESPALDA".equals(ejercicioTipo)) {
            // Ejercicios para ESPALDA
            ejerciciosList.add(new excerciseClase(R.drawable.jalon, "Jalón al pecho",
                    "Consejos\n1. Mantén la espalda recta durante todo el movimiento.\n2. Evita balancear el torso para jalar el peso.\n3. No dejes que el peso te arrastre hacia atrás."));
            ejerciciosList.add(new excerciseClase(R.drawable.gironda, "Gironda",
                    "Consejos\n1. Controla el movimiento y no uses el impulso.\n2. Mantén la espalda recta.\n3. Evita usar demasiado peso que pueda comprometer la forma."));
            ejerciciosList.add(new excerciseClase(R.drawable.remounilateral, "Remo unilateral",
                    "Consejos\n1. Mantén el torso firme y paralelo al suelo.\n2. Evita usar el brazo para impulsarte.\n3. No arquees la espalda al jalar el peso."));
            ejerciciosList.add(new excerciseClase(R.drawable.remot, "Remo en T con el pecho apoyado",
                    "Consejos\n1. Mantén el pecho apoyado sobre el banco en todo momento.\n2. Controla el peso mientras lo levantas y bajas.\n3. Evita que tus muñecas se doblen excesivamente."));
            ejerciciosList.add(new excerciseClase(R.drawable.posterior, "Posterior del hombro",
                    "Consejos\n1. Usa un peso que puedas controlar.\n2. Mantén una ligera flexión en los codos.\n3. No uses un peso tan grande que comprometa la forma."));
            ejerciciosList.add(new excerciseClase(R.drawable.predicador, "Curl predicador",
                    "Consejos\n1. Mantén los codos fijos en todo momento.\n2. Baja el peso lentamente.\n3. Evita hacer un movimiento de balanceo."));
            ejerciciosList.add(new excerciseClase(R.drawable.martillo, "Curl martillo",
                    "Consejos\n1. Mantén la muñeca en posición neutral.\n2. Evita balancear los codos.\n3. Realiza el movimiento de forma controlada."));
            ejerciciosList.add(new excerciseClase(R.drawable.bayes, "Curl Bayes",
                    "Consejos\n1. Mantén los codos fijos en su lugar.\n2. Controla el peso durante todo el movimiento.\n3. Evita que la espalda se arque demasiado."));
        } else if ("PIERNA".equals(ejercicioTipo)) {
            // Ejercicios para PIERNA
            ejerciciosList.add(new excerciseClase(R.drawable.sentadilla, "Sentadilla",
                    "Consejos\n1. Mantén la espalda recta al hacer el movimiento.\n2. No dejes que las rodillas se desplacen hacia adelante.\n3. Evita elevar los talones durante el movimiento."));
            ejerciciosList.add(new excerciseClase(R.drawable.prensa, "Prensa de pierna",
                    "Consejos\n1. Mantén las piernas a un ángulo de 90 grados.\n2. No bloquees las rodillas al final del movimiento.\n3. Evita usar un peso que no puedas controlar."));
            ejerciciosList.add(new excerciseClase(R.drawable.extensioncuadriceps, "Extensiones de cuadríceps",
                    "Consejos\n1. No levantes el peso excesivamente.\n2. Evita bloquear las rodillas al final del movimiento.\n3. Mantén la espalda pegada al asiento."));
            ejerciciosList.add(new excerciseClase(R.drawable.femoral, "Curl femoral",
                    "Consejos\n1. Mantén la pelvis neutra.\n2. Evita que tus caderas se levanten del banco.\n3. Controla el peso al levantarse y al bajar."));
            ejerciciosList.add(new excerciseClase(R.drawable.gemelos, "Elevación de gemelos",
                    "Consejos\n1. Realiza el movimiento con control.\n2. Mantén el torso erguido durante el movimiento.\n3. Evita el impulso de las piernas."));
            ejerciciosList.add(new excerciseClase(R.drawable.crunchabdomen, "Crunch abdominal",
                    "Consejos\n1. Contrae el abdomen mientras subes.\n2. Evita que el cuello se tense.\n3. No dejes que la espalda se arque demasiado."));
        } else if ("TORSO".equals(ejercicioTipo)) {
            // Ejercicios para TORSO
            ejerciciosList.add(new excerciseClase(R.drawable.banca, "Press banca plano",
                    "Consejos\n1. Mantén los pies firmes en el suelo.\n2. Baja el peso de forma controlada.\n3. No dejes que la barra rebote en tu pecho."));
            ejerciciosList.add(new excerciseClase(R.drawable.mancuernas, "Inclinado con mancuernas",
                    "Consejos\n1. Mantén la espalda bien apoyada en el banco.\n2. Realiza el movimiento de forma lenta y controlada.\n3. Evita arquear demasiado la espalda."));
            ejerciciosList.add(new excerciseClase(R.drawable.polealta, "Polea alta para pecho inferior",
                    "Consejos\n1. Mantén los codos ligeramente flexionados.\n2. Controla el movimiento de subida y bajada.\n3. No uses demasiado peso, ya que se pierde la forma."));
        } else if ("BRAZO".equals(ejercicioTipo)) {
            // Ejercicios para BRAZO
            ejerciciosList.add(new excerciseClase(R.drawable.elevaciones, "Elevaciones laterales con mancuernas",
                    "Consejos\n1. Mantén los codos ligeramente doblados.\n2. No uses demasiado peso para evitar balancear el cuerpo.\n3. Controla el movimiento y evita el impulso."));
            ejerciciosList.add(new excerciseClase(R.drawable.pressmilitar, "Press militar",
                    "Consejos\n1. Mantén los pies firmes en el suelo.\n2. No arquees la espalda al levantar el peso.\n3. Evita usar el impulso para levantar el peso."));
            ejerciciosList.add(new excerciseClase(R.drawable.predicador, "Curl predicador",
                    "Consejos\n1. Mantén los codos fijos en todo momento.\n2. Baja el peso lentamente para maximizar la contracción.\n3. Evita balancear el torso para ayudar al movimiento."));
            ejerciciosList.add(new excerciseClase(R.drawable.martillo, "Curl martillo",
                    "Consejos\n1. Mantén las muñecas en posición neutral durante el movimiento.\n2. Realiza el movimiento de forma controlada, sin balancear.\n3. Evita que el peso sea tan alto que comprometa la técnica."));
            ejerciciosList.add(new excerciseClase(R.drawable.bayes, "Curl Bayes",
                    "Consejos\n1. Mantén los codos fijos durante todo el ejercicio.\n2. Evita hacer el movimiento con balanceo o impulso.\n3. Realiza el movimiento de forma controlada y sin prisas."));
            ejerciciosList.add(new excerciseClase(R.drawable.extenssiontriceps, "Extensión de tríceps en polea",
                    "Consejos\n1. Mantén los codos pegados al torso.\n2. Realiza el movimiento de forma controlada, sin que el peso te arrastre.\n3. Evita que las muñecas se doblen durante el ejercicio."));
            ejerciciosList.add(new excerciseClase(R.drawable.frances, "Press francés",
                    "Consejos\n1. Baja la barra hasta la frente de manera controlada.\n2. Mantén los codos fijos y no los muevas hacia adelante.\n3. No uses un peso tan elevado que comprometa la técnica."));
        }



        // Crea el adaptador y establece el ViewPager
        adapter = new adaptadorEjercicios(ejerciciosList);
        viewPager.setAdapter(adapter);
    }
}