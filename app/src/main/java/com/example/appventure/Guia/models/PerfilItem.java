package com.example.appventure.Guia.models;

public class PerfilItem {
    private int iconResId;
    private String titulo;

    public PerfilItem(int iconResId, String titulo) {
        this.iconResId = iconResId;
        this.titulo = titulo;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitulo() {
        return titulo;
    }
}
