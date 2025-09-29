package com.example.appventure.Guia.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.Guia.models.Mensaje;
import com.example.appventure.R;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder> {

    private List<Mensaje> listaMensajes;
    private Context context;

    public MensajeAdapter(List<Mensaje> listaMensajes, Context context) {
        this.listaMensajes = listaMensajes;
        this.context = context;
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new MensajeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        Mensaje mensaje = listaMensajes.get(position);

        holder.tvMensaje.setText(mensaje.getTexto());
        holder.tvHora.setText(mensaje.getHora());

        if (mensaje.isEsSaliente()) {
            holder.tvMensaje.setBackgroundResource(R.drawable.bg_bubble_outgoing);
            holder.tvMensaje.setTextColor(context.getColor(android.R.color.white));
            ((LinearLayout) holder.tvMensaje.getParent()).setGravity(Gravity.END);
            holder.tvHora.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        } else {
            holder.tvMensaje.setBackgroundResource(R.drawable.bg_bubble_incoming);
            holder.tvMensaje.setTextColor(context.getColor(android.R.color.black));
            ((LinearLayout) holder.tvMensaje.getParent()).setGravity(Gravity.START);
            holder.tvHora.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    static class MensajeViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensaje, tvHora;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            tvHora = itemView.findViewById(R.id.tvHora);
        }
    }
}
