package com.example.appventure.Superadmin;

import java.io.Serializable;

public class UsuarioDatosSuperadmin implements Serializable {
    private String dni;
    private String nombre;
    private String apellido;
    private String rol;
    private String telefono;
    private String email;
    private String estado;

    // 🔹 Constructor corto (lo que ya usabas antes: nombre, rol, estado)
    public UsuarioDatosSuperadmin(String nombre, String rol, String estado) {
        this.nombre = nombre;
        this.rol = rol;
        this.estado = estado;
    }

    // 🔹 Constructor largo (para vista de detalles)
    public UsuarioDatosSuperadmin(String dni, String nombre, String apellido,
                                  String rol, String telefono, String email, String estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.telefono = telefono;
        this.email = email;
        this.estado = estado;
    }

    // --- Getters & Setters ---
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
