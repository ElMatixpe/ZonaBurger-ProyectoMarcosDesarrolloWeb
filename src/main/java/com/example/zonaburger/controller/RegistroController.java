package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.zonaburger.service.UsuarioService;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String registro() {
        return "paginas-internas/pg_registro";
    }

    @PostMapping("/registro")
    public String registrar(
            @RequestParam("nombreCompleto") String nombreCompleto,
            @RequestParam("correo") String correo,
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena,
            Model model) {

        if (usuarioService.existeUsuario(usuario)) {
            model.addAttribute("errorRegistro", "El usuario ya está registrado.");
            return "paginas-internas/pg_registro";
        }

        if (usuarioService.existeCorreo(correo)) {
            model.addAttribute("errorRegistro", "El correo electrónico ya está registrado.");
            return "paginas-internas/pg_registro";
        }

        usuarioService.registrarUsuario(nombreCompleto, correo, usuario, contrasena);
        model.addAttribute("registroExitoso", "Cuenta creada correctamente. Ahora puedes iniciar sesión.");
        return "paginas-internas/pg_login";
    }
}
