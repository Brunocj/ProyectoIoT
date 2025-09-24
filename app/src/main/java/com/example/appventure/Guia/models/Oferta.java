package com.example.appventure.Guia.models;

public class Oferta {
    private String titulo;
    private String fecha;
    private String pago;
    private int imagen;
    private float rating;
    private String descripcion;

    public Oferta(String titulo, String fecha, String pago, int imagen, float rating, String descripcion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.pago = pago;
        this.imagen = imagen;
        this.rating = rating;
        this.descripcion = descripcion;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public String getPago() { return pago; }
    public int getImagen() { return imagen; }
    public float getRating() { return rating; }
    public String getDescripcion() { return descripcion; }
}

