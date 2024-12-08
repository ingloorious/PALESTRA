package EJERCIOS_SCREEN;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import pl.droidsonroids.gif.GifImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gimnasio.R;
import java.util.List;

public class adaptadorEjercicios extends RecyclerView.Adapter<adaptadorEjercicios.ViewHolder> {

    private final List<excerciseClase> ejerciciosList;

    // Constructor
    public adaptadorEjercicios(List<excerciseClase> ejerciciosList) {
        this.ejerciciosList = ejerciciosList;
    }

    // Clase ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public GifImageView gifImageView;
        public TextView descripcionTextView , consejosTextView;

        public ViewHolder(View view) {
            super(view);
            // Usando los mismos IDs que en el XML
            gifImageView = view.findViewById(R.id.gifImageView);
            descripcionTextView = view.findViewById(R.id.descripcionTextView);
            consejosTextView = view.findViewById(R.id.consejos);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout para cada página en el ViewPager2
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        excerciseClase ejercicio = ejerciciosList.get(position);
        holder.gifImageView.setImageResource(ejercicio.getGifId());
        holder.descripcionTextView.setText(ejercicio.getDescripcion());
        holder.consejosTextView.setText(ejercicio.getConsejo());
    }

    @Override
    public int getItemCount() {
        return ejerciciosList.size();
    }
}
