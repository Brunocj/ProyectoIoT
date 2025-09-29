package com.example.appventure.Superadmin.Model;

public class User {
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String rol; // "Usuario", "Guía" o "Admin"
    private boolean activo;
    private String fechaRegistro;
    private String avatar; // URL o drawable resource

    // Constructor
    public User(String id, String nombre, String email, String telefono, String rol, boolean activo, String fechaRegistro, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    // Método auxiliar para obtener el color del estado
    public String getEstadoTexto() {
        return activo ? "Activo" : "Inactivo";
    }

    // Método auxiliar para determinar si es guía
    public boolean isGuia() {
        return "Guía".equals(rol);
    }

    // Método auxiliar para determinar si es usuario final
    public boolean isUsuarioFinal() {
        return "Usuario".equals(rol);
    }

    // Método auxiliar para determinar si es administrador
    public boolean isAdmin() {
        return "Admin".equals(rol);
    }
}