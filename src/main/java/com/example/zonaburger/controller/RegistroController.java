package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.zonaburger.form.RegistroForm;
import com.example.zonaburger.service.UsuarioService;
import com.example.zonaburger.form.ErroresFormulario;
import jakarta.validation.Valid;

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
            @Valid RegistroForm form,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            model.addAttribute("errorRegistro", ErroresFormulario.primerMensaje(
                    resultado, "nombreCompleto", "correo", "usuario", "contrasena"));
            return "paginas-internas/pg_registro";
        }

        String nombre = form.nombreCompleto().trim();
        String correo = form.correo().trim();
        String usuario = form.usuario().trim();

        if (usuarioService.existeUsuario(usuario)) {
            model.addAttribute("errorRegistro", "El usuario ya está registrado.");
            return "paginas-internas/pg_registro";
        }

        if (usuarioService.existeCorreo(correo)) {
            model.addAttribute("errorRegistro", "El correo electrónico ya está registrado.");
            return "paginas-internas/pg_registro";
        }

        usuarioService.registrarUsuario(nombre, correo, usuario, form.contrasena());
        model.addAttribute("registroExitoso", "Cuenta creada correctamente. Ahora puedes iniciar sesión.");
        return "paginas-internas/pg_login";
    }
}