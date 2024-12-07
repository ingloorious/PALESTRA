package RUTINAPERSONALIZADA;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.List;

public class RutinasAdaptador extends RecyclerView.Adapter<RutinasAdaptador.RutinaViewHolder> {
    private List<Rutina> mRutinas;

    // Constructor del adaptador
    public RutinasAdaptador(List<Rutina> rutinas) {
        this.mRutinas = rutinas;
    }

    @NonNull
    @Override
    public RutinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia, parent, false);
        return new RutinaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaViewHolder holder, int position) {
        Rutina rutina = mRutinas.get(position);
        holder.textViewNombreRutina.setText(rutina.getNombre());
    }

    @Override
    public int getItemCount() {
        return mRutinas.size();
    }

    public static class RutinaViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreRutina;

        public RutinaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreRutina = itemView.findViewById(R.id.textViewNombreRutina);
        }
    }
}