package RUTINAPERSONALIZADA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.R;

import java.util.List;

public class RutinasAdaptador extends RecyclerView.Adapter<RutinasAdaptador.RutinaViewHolder> {
    private List<Rutina> mRutinas;
    private Context context;
    private OnRutinaClickListener onRutinaClickListener;

    public interface OnRutinaClickListener {
        void onRutinaClick(int position);
    }

    public RutinasAdaptador(Context context, List<Rutina> rutinas , OnRutinaClickListener listener) {
        this.context = context;
        this.mRutinas = rutinas;
        this.onRutinaClickListener = listener;
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
        holder.textviewFecha.setText(rutina.getFecha());
        holder.textViewNombreRutina.setText(rutina.getNombre());

        holder.itemView.setOnClickListener(v -> {
            if (onRutinaClickListener != null) {
                onRutinaClickListener.onRutinaClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRutinas.size();
    }

    public static class RutinaViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombreRutina;
        public TextView textviewFecha;

        public RutinaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreRutina = itemView.findViewById(R.id.textViewNombreRutina);
            textviewFecha = itemView.findViewById(R.id.textViewFecha);
        }
    }
}
