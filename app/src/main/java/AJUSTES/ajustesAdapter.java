package AJUSTES;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gimnasio.R;
import java.util.List;

public class ajustesAdapter extends RecyclerView.Adapter<ajustesAdapter.ViewHolder> {

    private final List<ajustesClass> ajustesList;
    private final OnAjusteClickListener listener;

    // Constructor adecuado
    public ajustesAdapter(List<ajustesClass> ajustesList, OnAjusteClickListener listener) {
        this.ajustesList = ajustesList;
        this.listener = listener;
    }

    public interface OnAjusteClickListener {
        void onAjusteClick(ajustesClass item);
    }

    @NonNull
    @Override
    public ajustesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ajuste, parent, false);
        return new ajustesAdapter.ViewHolder(view); // Deberías retornar el ViewHolder aquí
    }

    @Override
    public void onBindViewHolder(@NonNull ajustesAdapter.ViewHolder holder, int position) {
        ajustesClass item = ajustesList.get(position);
        holder.icono.setImageResource(item.getIcono());
        holder.titulo.setText(item.getTitulo());
        holder.itemView.setOnClickListener(v -> listener.onAjusteClick(item));
    }

    @Override
    public int getItemCount() {
        return ajustesList.size();
    }

    // Clase ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView titulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icono = itemView.findViewById(R.id.iconImage);
            titulo = itemView.findViewById(R.id.titulo);
        }
    }
}
