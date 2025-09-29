package com.example.appventure.Superadmin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogSuperladmin {
    private String evento;
    private String usuario;
    private String descripcion;
    private String categoria;
    private String prioridad;
    private String timestamp;

    // Constructor
    public LogSuperladmin(String evento, String usuario, String descripcion, String categoria, String prioridad) {
        this.evento = evento;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.timestamp = generateTimestamp();
    }

    // Generar timestamp automático
    private String generateTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Getters
    public String getEvento() { return evento; }
    public String getUsuario() { return usuario; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
    public String getPrioridad() { return prioridad; }
    public String getTimestamp() { return timestamp; }

    // Setters
    public void setEvento(String evento) { this.evento = evento; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    // Métodos de utilidad para verificar prioridad
    public boolean isInfo() {
        return "INFO".equals(prioridad);
    }

    public boolean isWarning() {
        return "WARNING".equals(prioridad);
    }

    public boolean isError() {
        return "ERROR".equals(prioridad);
    }

    public boolean isCritical() {
        return "CRITICAL".equals(prioridad);
    }

    // Método para obtener ícono según el evento
    public int getEventIcon() {
        switch (evento) {
            case "EMPRESA_REGISTRADA":
            case "EMPRESA_DESACTIVADA":
            case "EMPRESA_SUSPENSION":
                return android.R.drawable.ic_menu_manage;
            case "GUIA_APROBADO":
            case "CHECKIN_REALIZADO":
                return android.R.drawable.ic_menu_compass;
            case "RESERVA_CREADA":
            case "TOUR_CANCELADO":
                return android.R.drawable.ic_menu_agenda;
            case "PAGO_PROCESADO":
            case "ERROR_PAGO":
                return android.R.drawable.ic_menu_send;
            case "LOGIN_FALLIDO":
            case "BRECHA_SEGURIDAD":
                return android.R.drawable.ic_menu_close_clear_cancel;
            case "ERROR_BASE_DATOS":
            case "SERVIDOR_CAIDO":
                return android.R.drawable.ic_menu_info_details;
            default:
                return android.R.drawable.ic_menu_gallery;
        }
    }
}