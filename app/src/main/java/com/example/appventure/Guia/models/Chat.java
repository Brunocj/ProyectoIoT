package com.example.appventure.Guia.models;

public class Chat {
    private String nombre;       // Nombre del usuario/guía
    private String tour;         // Nombre del tour
    private String ultimoMensaje; // Preview del último mensaje
    private String hora;          // Hora del último mensaje
    private int avatar;           // Drawable del avatar
    private int unreadCount;      // Mensajes no leídos

    // ✅ Constructor con unreadCount
    public Chat(String nombre, String tour, String ultimoMensaje, String hora, int avatar, int unreadCount) {
        this.nombre = nombre;
        this.tour = tour;
        this.ultimoMensaje = ultimoMensaje;
        this.hora = hora;
        this.avatar = avatar;
        this.unreadCount = unreadCount;
    }

    // ✅ Constructor alternativo (sin unreadCount → por defecto 0)
    public Chat(String nombre, String tour, String ultimoMensaje, String hora, int avatar) {
        this(nombre, tour, ultimoMensaje, hora, avatar, 0);
    }

    // ===== Getters & Setters =====
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
