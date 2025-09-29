package com.example.appventure.Guia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Guia.models.Tour;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    public interface OnItemClickListener {
        void onVerDetalles(Tour tour);
        void onVerMapa(Tour tour);
    }

    private final List<Tour> lista;
    private final OnItemClickListener listener;

    public TourAdapter(List<Tour> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour_card, parent, false);
        return new TourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour t = lista.get(position);

        // ✅ Usa getTitulo()
        holder.tvTitulo.setText(t.getTitulo());
        holder.tvFecha.setText(t.getFecha());
        holder.tvEstado.setText(t.getEstado());
        holder.imgTour.setImageResource(t.getImagenResId());

        // ✅ Botones
        holder.btnVerDetalles.setText("Detalles");
        holder.btnVerMapa.setText("Mapa");

        holder.btnVerDetalles.setOnClickListener(v -> listener.onVerDetalles(t));
        holder.btnVerMapa.setOnClickListener(v -> listener.onVerMapa(t));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class TourViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTour;
        TextView tvTitulo, tvFecha, tvEstado;
        MaterialButton btnVerDetalles, btnVerMapa;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTour = itemView.findViewById(R.id.imgTour);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
            btnVerMapa = itemView.findViewById(R.id.btnVerMapa);
        }
    }
}
