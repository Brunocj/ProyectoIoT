package com.example.appventure.Superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;

import java.util.List;

public class LogSuperadminAdapter extends RecyclerView.Adapter<LogSuperadminAdapter.LogViewHolder> {

    private List<LogSuperadmin> logs;
    private OnLogClickListener listener;

    public interface OnLogClickListener {
        void onLogClick(LogSuperadmin log);
    }

    public LogSuperadminAdapter(List<LogSuperadmin> logs, OnLogClickListener listener) {
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
        LogSuperadmin log = logs.get(position);
        holder.bind(log, listener);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public void updateLogList(List<LogSuperadmin> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();
    }

    static class LogViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTimestamp;
        private TextView textViewEvento;
        private TextView textViewUsuario;
        private TextView textViewDescripcion;
        private TextView chipPrioridad;
        private ImageView imageViewEventIcon;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewEvento = itemView.findViewById(R.id.textViewEvento);
            textViewUsuario = itemView.findViewById(R.id.textViewUsuario);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            chipPrioridad = itemView.findViewById(R.id.chipPrioridad);
            imageViewEventIcon = itemView.findViewById(R.id.imageViewEventIcon);
        }

        public void bind(LogSuperadmin log, OnLogClickListener listener) {
            // Establecer datos básicos
            textViewTimestamp.setText(formatTimestamp(log.getTimestamp()));
            textViewEvento.setText(log.getEvento());
            textViewUsuario.setText(log.getUsuario());
            textViewDescripcion.setText(log.getDescripcion());

            // Configurar chip de prioridad
            chipPrioridad.setText(log.getNivelPrioridad());
            updatePriorityChip(chipPrioridad, log.getNivelPrioridad());

            // Configurar ícono del evento
            imageViewEventIcon.setImageResource(log.getEventIcon());

            // Click en el item completo para ver detalles
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLogClick(log);
                }
            });
        }

        private String formatTimestamp(String timestamp) {
            // Formatear timestamp para mostrar solo hora si es de hoy
            // o fecha completa si es de otro día
            try {
                if (timestamp.contains(" ")) {
                    String[] parts = timestamp.split(" ");
                    if (parts.length >= 2) {
                        String date = parts[0];
                        String time = parts[1];
                        
                        // Si es de hoy, mostrar solo la hora
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        String today = sdf.format(new java.util.Date());
                        
                        if (date.equals(today)) {
                            return time.substring(0, 5); // HH:mm
                        } else {
                            return date + " " + time.substring(0, 5); // yyyy-MM-dd HH:mm
                        }
                    }
                }
                return timestamp;
            } catch (Exception e) {
                return timestamp;
            }
        }

        private void updatePriorityChip(TextView chip, String priority) {
            switch (priority.toUpperCase()) {
                case "INFO":
                    chip.setBackgroundResource(R.drawable.bg_chip_info);
                    chip.setTextColor(itemView.getContext().getColor(R.color.white));
                    break;
                case "WARNING":
                    chip.setBackgroundResource(R.drawable.bg_chip_warning);
                    chip.setTextColor(itemView.getContext().getColor(R.color.white));
                    break;
                case "ERROR":
                    chip.setBackgroundResource(R.drawable.bg_chip_error);
                    chip.setTextColor(itemView.getContext().getColor(R.color.white));
                    break;
                case "CRITICAL":
                    chip.setBackgroundResource(R.drawable.bg_chip_critical);
                    chip.setTextColor(itemView.getContext().getColor(R.color.white));
                    break;
                default:
                    chip.setBackgroundResource(R.drawable.bg_chip_default);
                    chip.setTextColor(itemView.getContext().getColor(R.color.text_secondary));
                    break;
            }
        }
    }
}