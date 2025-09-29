package com.example.appventure.AdminEmpresa;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    private List<Tour> tourList;
    private OnTourClickListener listener;

    public interface OnTourClickListener {
        void onTourClick(Tour tour, int position);
    }

    public TourAdapter(List<Tour> tourList, OnTourClickListener listener) {
        this.tourList = tourList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour_admin, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);

        holder.tvNombre.setText(tour.getNombre());
        holder.tvUbicacion.setText(tour.getUbicacion());
        holder.tvPrecio.setText(String.format("S/.%.0f", tour.getPrecio()));
        holder.tvRating.setText(String.format("%.1f", tour.getRating()));
        holder.tvRatingDetalle.setText(String.format("%.1f", tour.getRating()));
        holder.ivTourImage.setImageResource(tour.getImagenResId());

        if (tour.getEstado() != null && !tour.getEstado().isEmpty()) {
            holder.layoutEstados.setVisibility(View.VISIBLE);
            holder.tvEstado.setText("● " + tour.getEstado());

            switch (tour.getEstado()) {
                case "Pendiente":
                    holder.cardEstado.setCardBackgroundColor(Color.parseColor("#FFF3E0"));
                    holder.tvEstado.setTextColor(Color.parseColor("#FF9800"));
                    break;
                case "Activo":
                    holder.cardEstado.setCardBackgroundColor(Color.parseColor("#E8F5E8"));
                    holder.tvEstado.setTextColor(Color.parseColor("#4CAF50"));
                    break;
                case "Finalizado":
                    holder.cardEstado.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
                    holder.tvEstado.setTextColor(Color.parseColor("#006970"));
                    break;
            }

            if (!tour.isTieneGuia()) {
                holder.cardEstadoGuia.setVisibility(View.VISIBLE);
                holder.tvEstadoGuia.setText("Sin guía");
            } else {
                holder.cardEstadoGuia.setVisibility(View.GONE);
            }
        } else {
            holder.layoutEstados.setVisibility(View.GONE);
        }

        holder.cardTour.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTourClick(tour, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    static class TourViewHolder extends RecyclerView.ViewHolder {
        CardView cardTour;
        ImageView ivTourImage;
        TextView tvNombre, tvUbicacion, tvPrecio, tvRating, tvRatingDetalle;
        TextView tvEstado, tvEstadoGuia;
        CardView cardEstado, cardEstadoGuia;
        ViewGroup layoutEstados;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTour = itemView.findViewById(R.id.card_tour);
            ivTourImage = itemView.findViewById(R.id.iv_tour_image);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvUbicacion = itemView.findViewById(R.id.tv_ubicacion);
            tvPrecio = itemView.findViewById(R.id.tv_precio);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvRatingDetalle = itemView.findViewById(R.id.tv_rating_detalle);
            tvEstado = itemView.findViewById(R.id.tv_estado);
            tvEstadoGuia = itemView.findViewById(R.id.tv_estado_guia);
            cardEstado = itemView.findViewById(R.id.card_estado);
            cardEstadoGuia = itemView.findViewById(R.id.card_estado_guia);
            layoutEstados = itemView.findViewById(R.id.layout_estados);
        }
    }
}