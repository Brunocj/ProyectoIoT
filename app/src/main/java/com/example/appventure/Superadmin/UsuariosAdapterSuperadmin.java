package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsuariosAdapterSuperadmin extends RecyclerView.Adapter<UsuariosAdapterSuperadmin.VH> implements Filterable {

    public interface OnUsuarioClickListener {
        void onMenuClick(UsuarioDatosSuperadmin usuario, View anchor);
    }

    private final List<UsuarioDatosSuperadmin> data;     // lista visible
    private final List<UsuarioDatosSuperadmin> original; // copia completa
    private final OnUsuarioClickListener listener;

    public UsuariosAdapterSuperadmin(List<UsuarioDatosSuperadmin> data, OnUsuarioClickListener listener) {
        this.data = new ArrayList<>(data);
        this.original = new ArrayList<>(data);
        this.listener = listener;
    }

    public void setItems(List<UsuarioDatosSuperadmin> nuevos) {
        original.clear();
        original.addAll(nuevos);
        data.clear();
        data.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_usuario, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        UsuarioDatosSuperadmin u = data.get(position);

        h.tvNombre.setText(u.getNombre());
        h.chipRol.setText(u.getRol());

        // Estado
        String estado = u.getEstado() != null ? u.getEstado().trim() : "";
        boolean activo = "Activo".equalsIgnoreCase(estado);

        h.chipEstado.setText(activo ? "Activo" : "Inactivo");
        h.chipEstado.setBackgroundResource(
                activo ? R.drawable.chip_activo_background
                        : R.drawable.chip_inactivo_background
        );
        h.chipEstado.setTextColor(android.graphics.Color.WHITE);

        h.btnMenu.setOnClickListener(v -> {
            if (listener != null) listener.onMenuClick(u, h.btnMenu);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void notifyUsuarioCambiado(UsuarioDatosSuperadmin u) {
        int idx = data.indexOf(u);
        if (idx >= 0) notifyItemChanged(idx);
        else notifyDataSetChanged();
    }

    // --- Filtro ---
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String q = constraint == null ? "" : constraint.toString().toLowerCase(Locale.getDefault()).trim();
                List<UsuarioDatosSuperadmin> filtrada = new ArrayList<>();

                if (q.isEmpty()) {
                    filtrada.addAll(original);
                } else {
                    for (UsuarioDatosSuperadmin u : original) {
                        if (match(u, q)) {
                            filtrada.add(u);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtrada;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data.clear();
                //noinspection unchecked
                data.addAll((List<UsuarioDatosSuperadmin>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    private boolean match(UsuarioDatosSuperadmin u, String q) {
        return safe(u.getNombre()).toLowerCase(Locale.getDefault()).contains(q)
                || safe(u.getApellido()).toLowerCase(Locale.getDefault()).contains(q)
                || safe(u.getRol()).toLowerCase(Locale.getDefault()).contains(q)
                || safe(u.getTelefono()).toLowerCase(Locale.getDefault()).contains(q)
                || safe(u.getEmail()).toLowerCase(Locale.getDefault()).contains(q)
                || safe(u.getDni()).toLowerCase(Locale.getDefault()).contains(q);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre, chipRol, chipEstado;
        ImageView btnMenu;

        VH(@NonNull View itemView) {
            super(itemView);
            tvNombre   = itemView.findViewById(R.id.tv_nombre_usuario);
            chipRol    = itemView.findViewById(R.id.chip_rol);
            chipEstado = itemView.findViewById(R.id.chip_estado);
            btnMenu    = itemView.findViewById(R.id.btn_menu);
        }
    }
}
