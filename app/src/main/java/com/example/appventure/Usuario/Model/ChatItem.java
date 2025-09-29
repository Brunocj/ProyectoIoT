package com.example.appventure.Usuario.Model;

import androidx.annotation.DrawableRes;

public class ChatItem {
    public final String nombre;
    public final String tour;
    public final String preview;
    public final String hora;
    public final int unread;
    public final boolean activo;
    @DrawableRes
    public final int avatarResId;

    public ChatItem(String nombre, String tour, String preview, String hora,
                    int unread, boolean activo, @DrawableRes int avatarResId) {
        this.nombre = nombre;
        this.tour = tour;
        this.preview = preview;
        this.hora = hora;
        this.unread = unread;
        this.activo = activo;
        this.avatarResId = avatarResId;
    }

    public String getTituloToolbar() {
        return nombre; // lo que mostraremos en el toolbar del chat
    }

    public String getNombreTourLinea() {
        return nombre + "  (" + tour + ")";
    }
}
