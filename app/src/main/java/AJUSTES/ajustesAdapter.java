package AJUSTES;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.R;

import java.util.List;

public class ajustesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> items; // Lista mixta de ajustes y encabezados
    private final OnAjusteClickListener listener;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    // Constructor
    public ajustesAdapter(List<Object> items, OnAjusteClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    // Interfaz para manejar clics en los elementos de ajuste
    public interface OnAjusteClickListener {
        void onAjusteClick(ajustesClass item);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof String) {
            return VIEW_TYPE_HEADER; // Encabezado
        } else {
            return VIEW_TYPE_ITEM; // Elemento de ajuste
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ajuste, parent, false);
            return new AjusteViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            // Configura el encabezado
            String headerTitle = (String) items.get(position);
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTitle.setText(headerTitle); // Establece el texto del encabezado
        } else if (holder instanceof AjusteViewHolder) {
            // Configura el elemento de ajuste
            ajustesClass ajuste = (ajustesClass) items.get(position);
            AjusteViewHolder ajusteHolder = (AjusteViewHolder) holder;

            ajusteHolder.icono.setImageResource(ajuste.getIcono());
            ajusteHolder.titulo.setText(ajuste.getTitulo());

            // Configura el evento onClick del elemento de ajuste
            ajusteHolder.titulo.setOnClickListener(v -> {
                Toast.makeText(holder.itemView.getContext(),
                        "Título pulsado: " + ajuste.getTitulo(),
                        Toast.LENGTH_SHORT).show();
                listener.onAjusteClick(ajuste); // Llama al listener
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder para encabezados
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.headerTitle);
        }
    }

    // ViewHolder para elementos de ajuste
    static class AjusteViewHolder extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView titulo;

        public AjusteViewHolder(@NonNull View itemView) {
            super(itemView);
            icono = itemView.findViewById(R.id.iconImage);
            titulo = itemView.findViewById(R.id.titulo);
        }
    }
}
