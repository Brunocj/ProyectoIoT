package com.example.appventure.AdminEmpresa;

public class VentaTour {
    private String nombreTour;
    private String nombreGuia;
    private String ubicacion;
    private int numeroPersonas;
    private double precio;
    private String estado; // "Activo", "Pendiente", "Finalizado"
    private String inicialesGuia;

    public VentaTour(String nombreTour, String nombreGuia, String ubicacion,
                     int numeroPersonas, double precio, String estado, String inicialesGuia) {
        this.nombreTour = nombreTour;
        this.nombreGuia = nombreGuia;
        this.ubicacion = ubicacion;
        this.numeroPersonas = numeroPersonas;
        this.precio = precio;
        this.estado = estado;
        this.inicialesGuia = inicialesGuia;
    }

    // Getters
    public String getNombreTour() { return nombreTour; }
    public String getNombreGuia() { return nombreGuia; }
    public String getUbicacion() { return ubicacion; }
    public int getNumeroPersonas() { return numeroPersonas; }
    public double getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public String getInicialesGuia() { return inicialesGuia; }
}