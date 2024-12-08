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

    private final List<Object> items;
    private final OnAjusteClickListener listener;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    // Constructor
    public ajustesAdapter(List<Object> items, OnAjusteClickListener listener) {
        this.items = items;
        this.listener = listener;
    }


    public interface OnAjusteClickListener {
        void onAjusteClick(ajustesClass item);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof String) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
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
            String headerTitle = (String) items.get(position);
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTitle.setText(headerTitle);
        } else if (holder instanceof AjusteViewHolder) {
            // Configura el elemento de ajuste
            ajustesClass ajuste = (ajustesClass) items.get(position);
            AjusteViewHolder ajusteHolder = (AjusteViewHolder) holder;

            ajusteHolder.icono.setImageResource(ajuste.getIcono());
            ajusteHolder.titulo.setText(ajuste.getTitulo());


            ajusteHolder.titulo.setOnClickListener(v -> {
                Toast.makeText(holder.itemView.getContext(),
                        "Título pulsado: " + ajuste.getTitulo(),
                        Toast.LENGTH_SHORT).show();
                listener.onAjusteClick(ajuste);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.headerTitle);
        }
    }


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
