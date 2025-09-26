package com.example.appventure.Guia.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.fragments.DetalleOfertaGuiaFragment;
import com.example.appventure.Guia.models.Oferta;
import com.example.appventure.R;

import java.util.List;

public class OfertaAdapterGuia extends RecyclerView.Adapter<OfertaAdapterGuia.OfertaViewHolder> {

    private final List<Oferta> ofertaList;
    private final FragmentActivity activity;

    public OfertaAdapterGuia(List<Oferta> ofertaList, FragmentActivity activity) {
        this.ofertaList = ofertaList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_guia_oferta, parent, false);
        return new OfertaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfertaViewHolder holder, int position) {
        Oferta oferta = ofertaList.get(position);

        holder.txtTitulo.setText(oferta.getTitulo());
        holder.txtFecha.setText(oferta.getFecha());
        holder.txtPago.setText("Pago: " + oferta.getPago());
        holder.imgTour.setImageResource(oferta.getImagen());

        String ratingFormateado = String.format("%.1f", oferta.getRating());
        holder.txtRatingBottom.setText(ratingFormateado);

        holder.itemView.setOnClickListener(v -> {
            DetalleOfertaGuiaFragment detalle = new DetalleOfertaGuiaFragment();
            Bundle args = new Bundle();
            args.putString("titulo", oferta.getTitulo());
            args.putString("fecha", oferta.getFecha());
            args.putString("pago", oferta.getPago());
            args.putDouble("rating", oferta.getRating());
            args.putInt("imagenRes", oferta.getImagen());
            args.putString("descripcion", oferta.getDescripcion());
            detalle.setArguments(args);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container_guia, detalle)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return ofertaList.size();
    }

    static class OfertaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTour;
        TextView txtTitulo, txtFecha, txtPago, txtRatingBottom;

        public OfertaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTour = itemView.findViewById(R.id.imgTour);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtPago = itemView.findViewById(R.id.txtPago);
            txtRatingBottom = itemView.findViewById(R.id.txtRatingBottom);
        }
    }
}
