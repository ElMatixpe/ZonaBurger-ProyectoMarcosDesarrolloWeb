package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Solo muestra el formulario de inicio de sesion.
 * El envio del formulario (POST /login) y el cierre de sesion (POST /logout)
 * los procesa Spring Security */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "paginas-internas/pg_login";
    }
}
