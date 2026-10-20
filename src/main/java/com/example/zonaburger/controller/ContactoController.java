package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.zonaburger.form.ContactoForm;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.service.ContactoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ContactoController {

    private static final String VISTA = "paginas-internas/pg_contacto";
    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
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
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        // Con errores se vuelve a mostrar el formulario, cada error junto a su campo
        if (resultado.hasErrors()) {
            return VISTA;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioActivo");
        contactoService.registrar(form, usuario);

        // Despues de guardar se redirige 
        redirectAttributes.addFlashAttribute("contactoEnviado", true);
        redirectAttributes.addFlashAttribute("contactoMotivo", form.getMotivo());
        redirectAttributes.addFlashAttribute("contactoCorreo", form.getCorreo().trim());
        return "redirect:/contacto";
    }
}