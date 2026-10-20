package com.example.zonaburger.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** Mensaje enviado desde el formulario de contacto. */
@Entity
@Table(name = "contactos")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Solo para los que tengan la sesion iniciada se asocia el usuario. si es un visitante, usuario_id queda en null.  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 60)
    private String apellido;

    @Column(name = "correo", nullable = false, length = 120)
    private String correo;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    // Requerido por JPA
    protected Contacto() {
    }

    public Contacto(Usuario usuario, String nombre, String apellido, String correo,
                    String telefono, String motivo, String mensaje) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.motivo = motivo;
        this.mensaje = mensaje;
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getMotivo() { return motivo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFecha() { return fecha; }
}