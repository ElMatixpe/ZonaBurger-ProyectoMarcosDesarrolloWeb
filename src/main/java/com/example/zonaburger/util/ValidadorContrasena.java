package com.example.zonaburger.util;

import java.util.regex.Pattern;

public final class ValidadorContrasena {

    public static final String REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9\\s])\\S{8,}$";

    public static final String MENSAJE =
            "La contraseña debe tener mínimo 8 caracteres, una mayúscula, "
            + "una minúscula, un número y un signo especial (sin espacios).";

    private static final Pattern PATRON = Pattern.compile(REGEX);

    private ValidadorContrasena() {
    }

    public static boolean esValida(String contrasena) {
        return contrasena != null && PATRON.matcher(contrasena).matches();
    }
}
