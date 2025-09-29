package com.example.appventure.Usuario.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Usuario.Model.Tour;

import java.util.List;


public class TourAdapter extends RecyclerView.Adapter<TourAdapter.VH> {

    private final List<Tour> data;
    private final OnItemClick listener;

    public interface OnItemClick {
        void onClick(Tour item);
    }

    public TourAdapter(List<Tour> data, OnItemClick listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour_inicio, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Tour t = data.get(position);
        h.imgThumb.setImageResource(t.imageRes);
        h.txtTitulo.setText(t.titulo);
        h.txtUbicacion.setText(t.ubicacion);
        h.txtPrecio.setText(t.precio);
        h.txtRating.setText(t.rating);
        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(t);
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView txtTitulo, txtUbicacion, txtPrecio, txtRating, txtPor;
        VH(@NonNull View v) {
            super(v);
            imgThumb = v.findViewById(R.id.imgThumb);
            txtTitulo = v.findViewById(R.id.txtTitulo);
            txtUbicacion = v.findViewById(R.id.txtUbicacion);
            txtPrecio = v.findViewById(R.id.txtPrecio);
            txtPor = v.findViewById(R.id.txtPor); // opcional, ya está en el layout
            txtRating = v.findViewById(R.id.txtRating);
        }
    }
}
