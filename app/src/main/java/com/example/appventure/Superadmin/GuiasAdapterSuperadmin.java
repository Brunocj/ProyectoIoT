package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class GuiasAdapterSuperadmin extends RecyclerView.Adapter<GuiasAdapterSuperadmin.VH> {

    public interface OnGuiaClickListener {
        void onDetalleClick(GuiaDatosSuperadmin guia);
        void onAccionClick(GuiaDatosSuperadmin guia);
    }

    private List<GuiaDatosSuperadmin> data;
    private final OnGuiaClickListener listener;

    public GuiasAdapterSuperadmin(List<GuiaDatosSuperadmin> data, OnGuiaClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_guia_superadmin, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        GuiaDatosSuperadmin g = data.get(position);

        h.tvNombre.setText(nz(g.getNombre(), "—"));
        h.tvSkills.setText(nz(g.getIdiomas(), "—")); // usa el mismo id del layout

        String estado = nz(g.getEstado(), "Pendiente");
        switch (estado.toLowerCase()) {
            case "aprobado":
                h.tvEstado.setBackgroundResource(R.drawable.bg_chip_aprobado);
                h.tvEstado.setText("Aprobado");
                h.btnAccion.setText("Rechazar");
                break;
            case "rechazado":
                h.tvEstado.setBackgroundResource(R.drawable.bg_chip_rechazado);
                h.tvEstado.setText("Rechazado");
                h.btnAccion.setText("Aprobar");
                break;
            default:
                h.tvEstado.setBackgroundResource(R.drawable.bg_chip_pendiente);
                h.tvEstado.setText("Pendiente");
                h.btnAccion.setText("Aprobar");
        }

        h.btnVerDetalle.setOnClickListener(v -> {
            if (listener != null) listener.onDetalleClick(g);
        });

        h.btnAccion.setOnClickListener(v -> {
            if (listener != null) listener.onAccionClick(g);
        });
    }

    @Override
    public int getItemCount() { return data != null ? data.size() : 0; }

    public void setItems(List<GuiaDatosSuperadmin> lista) {
        this.data = lista;
        notifyDataSetChanged();
    }

    public void actualizarEstado(String idGuia, String nuevoEstado) {
        if (data == null || idGuia == null) return;
        for (int i = 0; i < data.size(); i++) {
            if (idGuia.equals(data.get(i).getId())) {
                data.get(i).setEstado(nz(nuevoEstado, "Pendiente"));
                notifyItemChanged(i);
                break;
            }
        }
    }

    private static String nz(String s, String fb) {
        return (s == null || s.trim().isEmpty()) ? fb : s;
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgGuia;
        TextView tvNombre, tvSkills, tvEstado;
        Button btnVerDetalle, btnAccion;

        VH(@NonNull View v) {
            super(v);
            imgGuia = v.findViewById(R.id.imgGuia);
            tvNombre = v.findViewById(R.id.tvNombreGuia);
            tvSkills = v.findViewById(R.id.tvSkillsGuia);
            tvEstado = v.findViewById(R.id.tvEstadoGuia);
            btnVerDetalle = v.findViewById(R.id.btnVerDetalle);
            btnAccion = v.findViewById(R.id.btnAccion);
        }
    }
}
