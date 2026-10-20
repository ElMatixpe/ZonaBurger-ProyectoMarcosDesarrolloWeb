package com.example.zonaburger.service;

import org.springframework.stereotype.Service;
import com.example.zonaburger.form.ContactoForm;
import com.example.zonaburger.model.Contacto;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.repository.ContactoRepository;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final UsuarioService usuarioService;

    public ContactoService(ContactoRepository contactoRepository, UsuarioService usuarioService) {
        this.contactoRepository = contactoRepository;
        this.usuarioService = usuarioService;
    }

    /**
     * Guarda el mensaje. Si quien escribe tiene sesión iniciada se asocia a su usuario;
     * si es un visitante, usuario_id queda en null. */
    public Contacto registrar(ContactoForm form, Usuario usuarioSesion) {

        Usuario autor = null;
        if (usuarioSesion != null && usuarioSesion.getId() != null) {
            autor = usuarioService.buscarPorId(usuarioSesion.getId());
        }

        return contactoRepository.save(new Contacto(
                autor,
                form.getNombre().trim(),
                form.getApellido().trim(),
                form.getCorreo().trim(),
                form.getTelefono().trim(),
                form.getMotivo(),
                form.getMensaje().trim()));
    }
}