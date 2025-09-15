package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class LogsMiniAdapterSuperadmin extends RecyclerView.Adapter<LogsMiniAdapterSuperadmin.VH> {

    private final List<String> data;

    public LogsMiniAdapterSuperadmin(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log_mini_superadmin, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.titulo.setText("Log #" + (position + 1));
        holder.detalle.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView titulo, detalle;
        VH(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTituloLog);
            detalle = itemView.findViewById(R.id.txtDetalleLog);
        }
    }
}
