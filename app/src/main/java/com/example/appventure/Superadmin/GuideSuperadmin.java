package com.example.appventure.Superadmin;

import java.util.List;

public class GuideSuperadmin {
    private String nombre;
    private String descripcion;
    private String edad;
    private String dni;
    private String correo;
    private String telefono;
    private String idiomas;
    private String ubicacion;
    private List<String> fotos; // URLs o nombres de archivos de fotos
    private String estado; // "Pendiente", "Aprobado", "Rechazado"

    // Constructor
    public GuideSuperadmin(String nombre, String descripcion, String edad, String dni, 
                String correo, String telefono, String idiomas, String ubicacion, 
                List<String> fotos, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.edad = edad;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.idiomas = idiomas;
        this.ubicacion = ubicacion;
        this.fotos = fotos;
        this.estado = estado;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEdad() {
        return edad;
    }

    public String getDni() {
        return dni;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Métodos de utilidad para verificar estado
    public boolean isPendiente() {
        return "Pendiente".equalsIgnoreCase(estado);
    }

    public boolean isAprobado() {
        return "Aprobado".equalsIgnoreCase(estado);
    }

    public boolean isRechazado() {
        return "Rechazado".equalsIgnoreCase(estado);
    }

    // Método para verificar si se puede cambiar de estado
    public boolean canChangeState() {
        return true; // Ahora se puede cambiar desde cualquier estado
    }

    // Métodos para cambiar estado
    public boolean aprobar() {
        this.estado = "Aprobado";
        return true;
    }

    public boolean rechazar() {
        this.estado = "Rechazado";
        return true;
    }

    public boolean marcarPendiente() {
        this.estado = "Pendiente";
        return true;
    }
}