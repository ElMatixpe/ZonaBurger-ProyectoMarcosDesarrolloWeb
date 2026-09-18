package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "paginas-internas/pg_login";
    }

    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena,
            HttpSession session,
            Model model) {

        Usuario usuarioEncontrado =usuarioService.validarUsuario(usuario, contrasena);

        if (usuarioEncontrado != null) {

            session.setAttribute("usuarioActivo", usuarioEncontrado);

            return "redirect:/";
        }

        model.addAttribute("errorLogin","Usuario o contraseña incorrectos.");

        return "paginas-internas/pg_login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}
