package com.example.appventure.Guia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.models.PerfilItem;
import com.example.appventure.R;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PerfilItem item, int position);
    }

    private final List<PerfilItem> lista;
    private final OnItemClickListener listener;

    public PerfilAdapter(List<PerfilItem> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_perfil, parent, false);
        return new PerfilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilViewHolder holder, int position) {
        PerfilItem item = lista.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class PerfilViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtTitulo;

        public PerfilViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
        }

        public void bind(PerfilItem item, OnItemClickListener listener) {
            imgIcon.setImageResource(item.getIconResId());
            txtTitulo.setText(item.getTitulo());

            itemView.setOnClickListener(v -> listener.onItemClick(item, getAdapterPosition()));
        }
    }
}
