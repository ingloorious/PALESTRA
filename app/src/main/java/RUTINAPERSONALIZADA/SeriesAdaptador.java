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

public class SeriesAdaptador extends RecyclerView.Adapter<SeriesAdaptador.SeriesViewHolder> {

    private Context context;
    private List<series> listaSeries;

    public SeriesAdaptador(Context context, List<series> listaSeries) {
        this.context = context;
        this.listaSeries = listaSeries;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_serie_visualizar, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        series serieActual = listaSeries.get(position);
        holder.tvNumeroSerie.setText( serieActual.getPeso());
        holder.tvPeso.setText("Peso: " + serieActual.getRepeticiones());;
        holder.tvRepeticiones.setText("Repeticiones: " + serieActual.getNumeroSerie());
    }

    @Override
    public int getItemCount() {
        return listaSeries.size();
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumeroSerie, tvPeso, tvRepeticiones;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumeroSerie = itemView.findViewById(R.id.tvNumeroSerie);
            tvPeso = itemView.findViewById(R.id.tvPeso);
            tvRepeticiones = itemView.findViewById(R.id.tvRepeticiones);
        }
    }
}
