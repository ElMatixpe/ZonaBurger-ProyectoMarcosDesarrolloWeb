package com.example.zonaburger.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Datos del formulario de contacto */
public class ContactoForm {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(min = 2, max = 60, message = "El apellido debe tener entre 2 y 60 caracteres.")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Ingresa un correo electrónico válido.")
    @Size(max = 120, message = "El correo no puede superar los 120 caracteres.")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "^\\+?[0-9 ]{7,19}$", message = "Ingresa un teléfono válido (solo números, de 7 a 15 dígitos; puede empezar con +).")
    private String telefono;

    @NotBlank(message = "Selecciona un motivo de contacto.")
    @Pattern(regexp = "Consulta sobre productos|Pedido|Reserva|Reclamo|Sugerencia|Otro", message = "Selecciona un motivo de contacto válido.")
    private String motivo;

    @NotBlank(message = "El mensaje es obligatorio.")
    @Size(min = 10, max = 1000, message = "El mensaje debe tener entre 10 y 1000 caracteres.")
    private String mensaje;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}