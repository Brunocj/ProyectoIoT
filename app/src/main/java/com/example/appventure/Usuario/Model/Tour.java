package com.example.appventure.Usuario.Model;

public class Tour {
    public final int imageRes;
    public final String titulo;
    public final String ubicacion;
    public final String precio;   // ej: "S/. 150"
    public final String rating;   // ej: "4.7"

    public Tour(int imageRes, String titulo, String ubicacion, String precio, String rating) {
        this.imageRes = imageRes;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.rating = rating;
    }
}
