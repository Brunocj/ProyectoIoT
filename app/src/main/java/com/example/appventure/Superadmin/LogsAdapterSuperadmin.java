package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class LogsAdapterSuperadmin extends RecyclerView.Adapter<LogsAdapterSuperadmin.VH> {

    public interface OnLogClickSuperadmin {
        void onClick(LogEntrySuperadmin log);
    }

    private final List<LogEntrySuperadmin> data;
    private final OnLogClickSuperadmin listener;

    public LogsAdapterSuperadmin(List<LogEntrySuperadmin> data, OnLogClickSuperadmin listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log_superadmin, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        LogEntrySuperadmin e = data.get(pos);
        h.tvTitulo.setText(nz(e.getTitulo()));
        h.tvSub1.setText("Usuario: " + nz(e.getUsuario()) + "   Rol: " + nz(e.getRol()));
        h.tvSub2.setText(nz(e.getFecha()));
        h.tvHora.setText(nz(e.getHora()));

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(e);
        });
    }

    @Override
    public int getItemCount() { return data != null ? data.size() : 0; }

    private String nz(String s) { return s == null ? "—" : s; }

    static class VH extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView tvTitulo, tvSub1, tvSub2, tvHora;

        VH(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgAvatar);
            tvTitulo = v.findViewById(R.id.tvTitulo);
            tvSub1 = v.findViewById(R.id.tvSub1);
            tvSub2 = v.findViewById(R.id.tvSub2);
            tvHora = v.findViewById(R.id.tvHora);
        }
    }
}
