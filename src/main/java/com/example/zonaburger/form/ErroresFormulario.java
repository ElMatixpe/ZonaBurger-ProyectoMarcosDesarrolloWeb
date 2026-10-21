package com.example.zonaburger.form;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public final class ErroresFormulario {

    private ErroresFormulario() {
    }

    /* Devuelve el mensaje del primer error, revisando los campos 
    /* Devuelve null si no hay errores */
    public static String primerMensaje(BindingResult resultado, String... camposEnOrden) {
        for (String campo : camposEnOrden) {
            FieldError error = resultado.getFieldError(campo);
            if (error != null) {
                if (error.isBindingFailure()) {
                    return "El valor ingresado en el campo \"" + campo + "\" no es válido.";
                }
                return error.getDefaultMessage();
            }
        }
        return resultado.getAllErrors().isEmpty()
                ? null
                : resultado.getAllErrors().get(0).getDefaultMessage();
    }
}