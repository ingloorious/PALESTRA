package RUTINAPERSONALIZADA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.R;

import java.util.List;

public class EjercicioAdaptador extends RecyclerView.Adapter<EjercicioAdaptador.EjercicioViewHolder> {

    private Context context;
    private List<ejercicio> listaEjercicios;

    public EjercicioAdaptador(Context context, List<ejercicio> listaEjercicios) {
        this.context = context;
        this.listaEjercicios = listaEjercicios;
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_visualizar_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        ejercicio ejercicioActual = listaEjercicios.get(position);
        holder.tvNombreEjercicio.setText(ejercicioActual.getNombreEjercicio());

        SeriesAdaptador seriesAdaptador = new SeriesAdaptador(context, ejercicioActual.getSeriesList());
        holder.recyclerViewSeries.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewSeries.setAdapter(seriesAdaptador);
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public static class EjercicioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreEjercicio;
        RecyclerView recyclerViewSeries;

        public EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEjercicio = itemView.findViewById(R.id.tvNombreEjercicio);
            recyclerViewSeries = itemView.findViewById(R.id.recyclerViewSeries);
        }
    }
}
