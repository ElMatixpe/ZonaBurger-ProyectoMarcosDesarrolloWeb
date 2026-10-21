package com.example.zonaburger.form;

import java.util.regex.Pattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/* Logica de @ContrasenaSegura. */
public class ContrasenaSeguraValidator implements ConstraintValidator<ContrasenaSegura, String> {

    private static final Pattern PATRON = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9\\s])\\S{8,}$");

    private boolean opcional;

    @Override
    public void initialize(ContrasenaSegura anotacion) {
        this.opcional = anotacion.opcional();
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null) {
            return true; 
        }
        if (valor.isEmpty()) {
            return opcional;
        }
        return esValida(valor);
    }

    public static boolean esValida(String contrasena) {
        return contrasena != null && PATRON.matcher(contrasena).matches();
    }
}
