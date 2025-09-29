package com.example.appventure.AdminEmpresa;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class VentaTourAdapter extends RecyclerView.Adapter<VentaTourAdapter.VentaTourViewHolder> {

    private List<VentaTour> ventasList;
    private OnVentaTourClickListener listener;

    public interface OnVentaTourClickListener {
        void onVentaTourClick(VentaTour venta, int position);
    }

    public VentaTourAdapter(List<VentaTour> ventasList, OnVentaTourClickListener listener) {
        this.ventasList = ventasList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VentaTourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venta_tour, parent, false);
        return new VentaTourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaTourViewHolder holder, int position) {
        VentaTour venta = ventasList.get(position);

        holder.tvNombreTour.setText(venta.getNombreTour());
        holder.tvNombreGuia.setText(venta.getNombreGuia());
        holder.tvUbicacion.setText(venta.getUbicacion());
        holder.tvNumeroPersonas.setText("👥 " + venta.getNumeroPersonas());
        holder.tvPrecio.setText(String.format("S/. %.0f", venta.getPrecio()));
        holder.tvInicialesGuia.setText(venta.getInicialesGuia());
        holder.tvEstado.setText("● " + venta.getEstado());

        // Cambiar colores según estado
        switch (venta.getEstado()) {
            case "Activo":
                holder.cardEstado.setCardBackgroundColor(Color.parseColor("#E8F5E8"));
                holder.tvEstado.setTextColor(Color.parseColor("#4CAF50"));
                break;
            case "Pendiente":
                holder.cardEstado.setCardBackgroundColor(Color.parseColor("#FFF3E0"));
                holder.tvEstado.setTextColor(Color.parseColor("#FF9800"));
                break;
            case "Finalizado":
                holder.cardEstado.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
                holder.tvEstado.setTextColor(Color.parseColor("#006970"));
                break;
        }

        holder.cardVentaTour.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVentaTourClick(venta, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ventasList.size();
    }

    static class VentaTourViewHolder extends RecyclerView.ViewHolder {
        CardView cardVentaTour, cardEstado;
        TextView tvNombreTour, tvNombreGuia, tvUbicacion, tvNumeroPersonas;
        TextView tvPrecio, tvEstado, tvInicialesGuia;

        public VentaTourViewHolder(@NonNull View itemView) {
            super(itemView);
            cardVentaTour = itemView.findViewById(R.id.card_venta_tour);
            tvNombreTour = itemView.findViewById(R.id.tv_nombre_tour);
            tvNombreGuia = itemView.findViewById(R.id.tv_nombre_guia);
            tvUbicacion = itemView.findViewById(R.id.tv_ubicacion);
            tvNumeroPersonas = itemView.findViewById(R.id.tv_numero_personas);
            tvPrecio = itemView.findViewById(R.id.tv_precio);
            tvEstado = itemView.findViewById(R.id.tv_estado);
            tvInicialesGuia = itemView.findViewById(R.id.tv_iniciales_guia);
            cardEstado = itemView.findViewById(R.id.card_estado);
        }
    }
}