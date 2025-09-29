package com.example.appventure.Guia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.models.Tour;
import com.example.appventure.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProximoTourAdapter extends RecyclerView.Adapter<ProximoTourAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onVerMapa(Tour tour);
        void onIrChat(Tour tour);
    }

    private List<Tour> lista;
    private OnItemClickListener listener;

    public ProximoTourAdapter(List<Tour> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_proximo_tour_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tour t = lista.get(position);

        holder.txtNombreTour.setText(t.getTitulo());
        holder.txtFechaTour.setText(t.getFecha());
        holder.txtUbicacionTour.setText(t.getUbicacion());

        holder.btnVerMapa.setOnClickListener(v -> listener.onVerMapa(t));
        holder.btnIrChat.setOnClickListener(v -> listener.onIrChat(t));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTituloProximo, txtNombreTour, txtFechaTour, txtUbicacionTour;
        MaterialButton btnVerMapa, btnIrChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTituloProximo = itemView.findViewById(R.id.txtTituloProximo);
            txtNombreTour = itemView.findViewById(R.id.txtNombreTour);
            txtFechaTour = itemView.findViewById(R.id.txtFechaTour);
            txtUbicacionTour = itemView.findViewById(R.id.txtUbicacionTour);
            btnVerMapa = itemView.findViewById(R.id.btnVerMapa);
            btnIrChat = itemView.findViewById(R.id.btnIrChat);
        }
    }
}
