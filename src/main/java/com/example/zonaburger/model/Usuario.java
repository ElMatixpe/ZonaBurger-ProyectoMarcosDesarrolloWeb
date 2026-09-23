package com.example.zonaburger.model;

public class Usuario {

    private String usuario;
    private String contrasena;
    private String rol;
    private String nombreCompleto;
    private String correo;

    public Usuario(String usuario, String contrasena, String rol) {
        this(usuario, contrasena, rol, "", "");
    }

    public Usuario(String usuario, String contrasena, String rol, String nombreCompleto, String correo) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
    }

    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setCorreo(String correo) { this.correo = correo; }
}
