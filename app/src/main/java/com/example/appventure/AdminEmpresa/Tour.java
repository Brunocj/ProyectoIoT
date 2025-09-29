package com.example.appventure.AdminEmpresa;

public class Tour {
    private String nombre;
    private String ubicacion;
    private double precio;
    private double rating;
    private int imagenResId;
    private String estado;
    private boolean tieneGuia;

    public Tour(String nombre, String ubicacion, double precio, double rating,
                int imagenResId, String estado, boolean tieneGuia) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.rating = rating;
        this.imagenResId = imagenResId;
        this.estado = estado;
        this.tieneGuia = tieneGuia;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public double getPrecio() { return precio; }
    public double getRating() { return rating; }
    public int getImagenResId() { return imagenResId; }
    public String getEstado() { return estado; }
    public boolean isTieneGuia() { return tieneGuia; }
}