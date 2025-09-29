package com.example.appventure.Guia.models;

public class Tour {
    private String titulo;
    private String fecha;
    private String estado;
    private int imagenResId;
    private String ubicacion;

    public Tour(String titulo, String fecha, String estado, int imagenResId, String ubicacion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.estado = estado;
        this.imagenResId = imagenResId;
        this.ubicacion = ubicacion;
    }

    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public int getImagenResId() { return imagenResId; }
    public String getUbicacion() { return ubicacion; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setImagenResId(int imagenResId) { this.imagenResId = imagenResId; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}
