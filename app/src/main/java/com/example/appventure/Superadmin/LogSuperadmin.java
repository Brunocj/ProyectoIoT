package com.example.appventure.Superadmin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogSuperadmin {
    private String id;
    private String timestamp;
    private String evento;
    private String usuario;
    private String descripcion;
    private String entidad;
    private String nivelPrioridad;
    private String ipAddress;
    private String detallesJson;

    // Constructor completo
    public LogSuperadmin(String id, String timestamp, String evento, String usuario, 
                        String descripcion, String entidad, String nivelPrioridad, 
                        String ipAddress, String detallesJson) {
        this.id = id;
        this.timestamp = timestamp;
        this.evento = evento;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.entidad = entidad;
        this.nivelPrioridad = nivelPrioridad;
        this.ipAddress = ipAddress;
        this.detallesJson = detallesJson;
    }

    // Constructor simplificado
    public LogSuperadmin(String evento, String usuario, String descripcion, 
                        String entidad, String nivelPrioridad) {
        this.id = generateId();
        this.timestamp = getCurrentTimestamp();
        this.evento = evento;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.entidad = entidad;
        this.nivelPrioridad = nivelPrioridad;
        this.ipAddress = "192.168.1.100"; // IP por defecto para demo
        this.detallesJson = "{}"; // JSON vacío por defecto
    }

    // Métodos helper
    private String generateId() {
        return "LOG_" + System.currentTimeMillis();
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Métodos para verificar nivel de prioridad
    public boolean isInfo() {
        return "INFO".equalsIgnoreCase(nivelPrioridad);
    }

    public boolean isWarning() {
        return "WARNING".equalsIgnoreCase(nivelPrioridad);
    }

    public boolean isError() {
        return "ERROR".equalsIgnoreCase(nivelPrioridad);
    }

    public boolean isCritical() {
        return "CRITICAL".equalsIgnoreCase(nivelPrioridad);
    }

    // Método para obtener color según prioridad
    public int getPriorityColor() {
        switch (nivelPrioridad.toUpperCase()) {
            case "INFO":
                return android.graphics.Color.parseColor("#2196F3"); // Azul
            case "WARNING":
                return android.graphics.Color.parseColor("#FF9800"); // Naranja
            case "ERROR":
                return android.graphics.Color.parseColor("#F44336"); // Rojo
            case "CRITICAL":
                return android.graphics.Color.parseColor("#000000"); // Negro
            default:
                return android.graphics.Color.parseColor("#9E9E9E"); // Gris
        }
    }

    // Método para obtener ícono según tipo de evento
    public int getEventIcon() {
        // Usar sólo íconos que definitivamente existen
        return com.example.appventure.R.drawable.ic_info;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getEvento() { return evento; }
    public void setEvento(String evento) { this.evento = evento; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }

    public String getNivelPrioridad() { return nivelPrioridad; }
    public void setNivelPrioridad(String nivelPrioridad) { this.nivelPrioridad = nivelPrioridad; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getDetallesJson() { return detallesJson; }
    public void setDetallesJson(String detallesJson) { this.detallesJson = detallesJson; }

    // Método toString para debugging
    @Override
    public String toString() {
        return "LogSuperadmin{" +
                "timestamp='" + timestamp + '\'' +
                ", evento='" + evento + '\'' +
                ", usuario='" + usuario + '\'' +
                ", nivelPrioridad='" + nivelPrioridad + '\'' +
                '}';
    }
}