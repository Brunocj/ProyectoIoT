package com.example.appventure.Usuario.Model;


import androidx.annotation.DrawableRes;

import java.io.Serializable;

public class Reserva implements Serializable {
    private String id;
    private String nombreLugar;
    private String ubicacion;
    private String fecha;
    private String rating;
    private String empresa;
    private int personas;

    // Constructor vacío (Firebase o serialización lo requiere)
    public Reserva() { }

    public Reserva(String id, String nombreLugar, String ubicacion, String fecha, String rating, String empresa, int personas) {
        this.id = id;
        this.nombreLugar = nombreLugar;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.rating = rating;
        this.empresa = empresa;
        this.personas = personas;
    }

    // Getters y setters
    public String getId() { return id; }
    public String getNombreLugar() { return nombreLugar; }
    public String getUbicacion() { return ubicacion; }
    public String getFecha() { return fecha; }
    public String getRating() { return rating; }
    public String getEmpresa() { return empresa; }
    public int getPersonas() { return personas; }

    public void setId(String id) { this.id = id; }
    public void setNombreLugar(String nombreLugar) { this.nombreLugar = nombreLugar; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setRating(String rating) { this.rating = rating; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    public void setPersonas(int personas) { this.personas = personas; }
}