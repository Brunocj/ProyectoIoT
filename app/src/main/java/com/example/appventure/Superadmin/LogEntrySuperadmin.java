package com.example.appventure.Superadmin;

import java.io.Serializable;

public class LogEntrySuperadmin implements Serializable {
    private String id;
    private String titulo;        // Ej: "Usuario autenticado"
    private String descripcion;   // Texto largo o detalle
    private String usuario;       // Ej: "f.godoy"
    private String rol;           // Usuario | Guía | Administrador
    private String fecha;         // Ej: "16-08-2025"
    private String hora;          // Ej: "09:41"
    private String ipDispositivo; // Ej: "192.168.35.89 / Android"
    private String entidad;       // Ej: "ID 2345 – Tour Cusco Mágico"
    private String avatarUrl;     // URL del avatar (opcional)

    public LogEntrySuperadmin() {
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getIpDispositivo() { return ipDispositivo; }
    public void setIpDispositivo(String ipDispositivo) { this.ipDispositivo = ipDispositivo; }

    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
