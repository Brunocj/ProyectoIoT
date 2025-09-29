package com.example.appventure.Guia.models;

public class Mensaje {
    private String texto;
    private String hora;
    private boolean esSaliente;

    public Mensaje(String texto, String hora, boolean esSaliente) {
        this.texto = texto;
        this.hora = hora;
        this.esSaliente = esSaliente;
    }

    public String getTexto() { return texto; }
    public String getHora() { return hora; }
    public boolean isEsSaliente() { return esSaliente; }
}
