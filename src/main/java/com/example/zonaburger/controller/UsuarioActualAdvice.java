package com.example.zonaburger.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.security.UsuarioPrincipal;
import com.example.zonaburger.service.UsuarioService;

/** Deja disponible en todas las vistas la variable "usuarioActual": el usuario
 * con sesion iniciada o null si es un visitante.*/
@ControllerAdvice
public class UsuarioActualAdvice {

    private final UsuarioService usuarioService;

    public UsuarioActualAdvice(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual(@AuthenticationPrincipal UsuarioPrincipal principal) {
        if (principal == null) {
            return null;
        }
        return usuarioService.buscarPorId(principal.getId());
    }
}
