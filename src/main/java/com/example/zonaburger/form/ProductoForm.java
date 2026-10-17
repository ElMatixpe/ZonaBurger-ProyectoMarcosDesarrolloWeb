package com.example.zonaburger.form;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos para agregar o editar un producto desde el panel de administración. */
public record ProductoForm(

        @NotNull(message = "Selecciona una categoría.")
        Integer categoriaId,

        @NotBlank(message = "El nombre del producto es obligatorio.")
        @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres.")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria.")
        @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres.")
        String descripcion,

        @NotNull(message = "El precio es obligatorio.")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.")
        @DecimalMax(value = "999.99", message = "El precio no puede superar S/ 999.99.")
        @Digits(integer = 3, fraction = 2, message = "El precio admite como máximo 2 decimales.")
        BigDecimal precio,

        @NotBlank(message = "La imagen es obligatoria.")
        @Size(max = 100, message = "El nombre de la imagen no puede superar los 100 caracteres.")
        @Pattern(regexp = "(?i)^[\\w\\-. ]+\\.(png|jpg|jpeg|webp|gif)$", message = "La imagen debe ser solo el nombre del archivo, por ejemplo: clasica.png")
        String imagen) {
}