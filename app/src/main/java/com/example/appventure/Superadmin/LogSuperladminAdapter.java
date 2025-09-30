package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appventure.R;
import com.google.android.material.chip.Chip;
import java.util.List;

public class LogSuperladminAdapter extends RecyclerView.Adapter<LogSuperladminAdapter.LogViewHolder> {

    private List<LogSuperladmin> logs;
    private OnLogClickListener listener;

    public interface OnLogClickListener {
        void onLogClick(LogSuperladmin log);
    }

    public LogSuperladminAdapter(List<LogSuperladmin> logs, OnLogClickListener listener) {
        this.logs = logs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log_superadmin, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogSuperladmin log = logs.get(position);
        holder.bind(log, listener);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public void updateLogList(List<LogSuperladmin> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();
    }

    static class LogViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconImageView;
        private TextView timestampTextView;
        private TextView priorityChip;
        private TextView eventTextView;
        private TextView descriptionTextView;
        private TextView userTextView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.imageViewEventIcon);
            timestampTextView = itemView.findViewById(R.id.textViewTimestamp);
            priorityChip = itemView.findViewById(R.id.chipPrioridad);
            eventTextView = itemView.findViewById(R.id.textViewEvento);
            descriptionTextView = itemView.findViewById(R.id.textViewDescripcion);
            userTextView = itemView.findViewById(R.id.textViewUsuario);
        }

        public void bind(LogSuperladmin log, OnLogClickListener listener) {
            // Configurar ícono
            iconImageView.setImageResource(log.getEventIcon());

            // Configurar timestamp
            timestampTextView.setText(log.getTimestamp());

            // Configurar chip de prioridad
            priorityChip.setText(log.getPrioridad());
            
            // Configurar background del chip según la prioridad
            int chipBackground = getChipBackground(log.getPrioridad());
            priorityChip.setBackgroundResource(chipBackground);
            
            // Configurar textos
            eventTextView.setText(log.getEvento());
            descriptionTextView.setText(log.getDescripcion());
            userTextView.setText(log.getUsuario());

            // Configurar click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLogClick(log);
                }
            });
        }

        private int getChipBackground(String priority) {
            switch (priority) {
                case "INFO":
                    return R.drawable.bg_chip_info;
                case "WARNING":
                    return R.drawable.bg_chip_warning;
                case "ERROR":
                    return R.drawable.bg_chip_error;
                case "CRITICAL":
                    return R.drawable.bg_chip_critical;
                default:
                    return R.drawable.bg_chip_info;
            }
        }


    }
}