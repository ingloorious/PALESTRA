package ADAPTADOR;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.R;

import java.util.List;

import EJERCIOS_SCREEN.ejercicios;
import EJERCIOS_SCREEN.excerciseClase;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<Item> items;
    private final Context context;


    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView textView1;
        public final TextView textView2;
        public final TextView textView3;
        public final Button myButton;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_image);
            textView1 = view.findViewById(R.id.text_view_1);
            textView2 = view.findViewById(R.id.text_view_2);
            textView3 = view.findViewById(R.id.text_view_3);
            myButton = view.findViewById(R.id.my_button);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.textView1.setText(item.getText1());
        holder.textView2.setText(item.getText2());
        holder.textView3.setText(item.getText3());

        holder.myButton.setOnClickListener(v -> {

            Intent intent = new Intent(context, ejercicios.class);
            intent.putExtra("item_id", item.getText1());
            intent.putExtra("ejercicioTipo", item.getText2());
            context.startActivity(intent); // Usa el contexto aquí
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Item {
        private final int imageResId;
        private final String text1;
        private final String text2;
        private final String text3;

        public Item(int imageResId, String text1, String text2, String text3) {
            this.imageResId = imageResId;
            this.text1 = text1;
            this.text2 = text2;
            this.text3 = text3;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getText1() {
            return text1;
        }

        public String getText2() {
            return text2;
        }

        public String getText3() {
            return text3;
        }
    }
}
