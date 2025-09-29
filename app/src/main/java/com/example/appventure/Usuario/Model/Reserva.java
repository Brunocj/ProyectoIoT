package com.example.appventure.Usuario.Model;


import androidx.annotation.DrawableRes;

public class Reserva {
    public final String id;
    public final String titulo;
    public final String ubicacion;
    public final String fecha;
    public final String hora;
    public final float rating;
    @DrawableRes public final int imagenRes;

    public Reserva(String id, String titulo, String ubicacion,
                     String fecha, String hora, float rating,
                     @DrawableRes int imagenRes) {
        this.id = id;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.hora = hora;
        this.rating = rating;
        this.imagenRes = imagenRes;
    }
}
