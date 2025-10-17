package com.example.appventure.models;

public class Usuario {

    private String uid;
    private String nombre;
    private String correo;
    private String rol;
    private boolean activo;

    // 🔹 Constructor vacío (obligatorio para Firestore)
    public Usuario() {}

    // 🔹 Constructor opcional
    public Usuario(String uid, String nombre, String correo, String rol, boolean activo) {
        this.uid = uid;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.activo = activo;
    }

    // 🔹 Getters y setters
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
