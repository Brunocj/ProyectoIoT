package com.example.appventure.Superadmin;

import java.io.Serializable;
import java.util.ArrayList;

public class GuiaDatosSuperadmin implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private Integer edad;
    private String dni;
    private String correo;
    private String telefono;
    private String idiomas;
    private String ubicacion;
    private String estado;       // Pendiente | Aprobado | Rechazado
    private ArrayList<String> fotos;

    public GuiaDatosSuperadmin() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getIdiomas() { return idiomas; }
    public void setIdiomas(String idiomas) { this.idiomas = idiomas; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ArrayList<String> getFotos() { return fotos; }
    public void setFotos(ArrayList<String> fotos) { this.fotos = fotos; }
}
