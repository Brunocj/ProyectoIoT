package com.example.appventure.Usuario.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Usuario.Model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservasListaAdapter extends RecyclerView.Adapter<ReservasListaAdapter.ViewHolder> {

    public interface OnReservaClickListener {
        void onClick(Reserva reserva);
    }

    private List<Reserva> reservas;
    private OnReservaClickListener listener;

    public ReservasListaAdapter(List<Reserva> reservas, OnReservaClickListener listener) {
        this.reservas = reservas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reserva, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reserva r = reservas.get(position);

        holder.tvNombre.setText(r.getNombreLugar());
        holder.tvUbicacion.setText(r.getUbicacion());
        holder.tvFecha.setText(r.getFecha());
        holder.tvRating.setText(r.getRating());

        holder.itemView.setOnClickListener(v -> listener.onClick(r));
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvUbicacion, tvFecha, tvRating;
        ImageView imgLugar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvRating = itemView.findViewById(R.id.tvRating);
            imgLugar = itemView.findViewById(R.id.imgLugar);
        }
    }
}