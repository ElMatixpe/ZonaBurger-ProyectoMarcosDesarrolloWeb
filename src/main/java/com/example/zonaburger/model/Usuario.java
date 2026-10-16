package com.example.zonaburger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario", nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "nombre_completo", nullable = false, length = 120)
    private String nombreCompleto;

    @Column(name = "correo", nullable = false, unique = true, length = 120)
    private String correo;

    protected Usuario() {
    }

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

    public Integer getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}