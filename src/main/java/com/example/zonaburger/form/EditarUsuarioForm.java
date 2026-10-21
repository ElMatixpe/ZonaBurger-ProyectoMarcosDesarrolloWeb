package com.example.zonaburger.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/* Datos para editar un cliente o administrador desde el panel administracion */
public record EditarUsuarioForm(

        @NotBlank(message = "El nombre completo es obligatorio.")
        @Size(max = 120, message = "El nombre completo no puede superar los 120 caracteres.")
        String nombreCompleto,

        @NotBlank(message = "El correo electrónico es obligatorio.")
        @Email(message = "Ingresa un correo electrónico válido.")
        @Size(max = 120, message = "El correo no puede superar los 120 caracteres.")
        String correo,

        @NotBlank(message = "El usuario es obligatorio.")
        @Size(max = 50, message = "El usuario no puede superar los 50 caracteres.")
        String usuario,

        @ContrasenaSegura(opcional = true)
        String contrasena) {
}