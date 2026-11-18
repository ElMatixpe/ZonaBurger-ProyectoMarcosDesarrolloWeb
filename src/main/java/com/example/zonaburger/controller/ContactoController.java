package com.example.zonaburger.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.zonaburger.form.ContactoForm;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.security.UsuarioPrincipal;
import com.example.zonaburger.service.ContactoService;
import com.example.zonaburger.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class ContactoController {

    private static final String VISTA = "paginas-internas/pg_contacto";
    private final ContactoService contactoService;
    private final UsuarioService usuarioService;

    public ContactoController(ContactoService contactoService, UsuarioService usuarioService) {
        this.contactoService = contactoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        if (!model.containsAttribute("contactoForm")) {
            model.addAttribute("contactoForm", new ContactoForm());
        }
        return VISTA;
    }

    @PostMapping("/contacto")
    public String enviar(
            @Valid @ModelAttribute("contactoForm") ContactoForm form,
            BindingResult resultado,
            @AuthenticationPrincipal UsuarioPrincipal principal,
            RedirectAttributes redirectAttributes) {
        // Con errores se vuelve a mostrar el formulario, cada error junto a su campo
        if (resultado.hasErrors()) {
            return VISTA;
        }

        // Visitante: principal es null y el mensaje se guarda sin usuario
        Usuario usuario = principal == null ? null : usuarioService.buscarPorId(principal.getId());
        contactoService.registrar(form, usuario);

        // Despues de guardar se redirige 
        redirectAttributes.addFlashAttribute("contactoEnviado", true);
        redirectAttributes.addFlashAttribute("contactoMotivo", form.getMotivo());
        redirectAttributes.addFlashAttribute("contactoCorreo", form.getCorreo().trim());
        return "redirect:/contacto";
    }
}