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

public class ReservasListaAdapter extends RecyclerView.Adapter<ReservasListaAdapter.VH> {

    public interface OnReservaClick {
        void onClick(@NonNull Reserva r);
        void onLongClick(@NonNull Reserva r);
    }

    private final List<Reserva> data = new ArrayList<>();
    private final OnReservaClick listener;

    public ReservasListaAdapter(OnReservaClick listener) { this.listener = listener; }

    public void setItems(@NonNull List<Reserva> items) {
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reserva, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Reserva r = data.get(position);

        ((TextView) h.itemView.findViewById(R.id.tvNombre)).setText(r.titulo);
        ((TextView) h.itemView.findViewById(R.id.tvUbicacion)).setText(r.ubicacion);
        ((TextView) h.itemView.findViewById(R.id.tvFecha)).setText(r.fecha);
        ((TextView) h.itemView.findViewById(R.id.tvRating)).setText(String.valueOf(r.rating));

        TextView tvHora = h.itemView.findViewById(R.id.tvHora);
        if (r.hora == null || r.hora.isEmpty()) {
            tvHora.setVisibility(View.GONE);
        } else {
            tvHora.setText(r.hora);
            tvHora.setVisibility(View.VISIBLE);
        }

        ImageView img = h.itemView.findViewById(R.id.imgLugar);
        img.setImageResource(r.imagenRes);

        h.itemView.setOnClickListener(v -> { if (listener != null) listener.onClick(r); });
        h.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onLongClick(r);
            return true;
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder { VH(@NonNull View itemView) { super(itemView); } }
}
