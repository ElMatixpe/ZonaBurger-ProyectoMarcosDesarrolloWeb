package com.example.zonaburger.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.repository.UsuarioRepository;
import com.example.zonaburger.form.ContrasenaSegura;
import com.example.zonaburger.form.ContrasenaSeguraValidator;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario validarUsuario(String usuario, String contrasena) {
        Optional<Usuario> encontrado = usuarioRepository.findByUsuarioIgnoreCase(usuario);

        if (encontrado.isPresent() && encontrado.get().getContrasena().equals(contrasena)) {
            return encontrado.get();
        }
        return null;
    }

    public boolean existeUsuario(String usuario) {
        return usuarioRepository.existsByUsuarioIgnoreCase(usuario);
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreoIgnoreCase(correo);
    }

    public boolean registrarUsuario(String nombreCompleto, String correo, String usuario, String contrasena) {
        return registrar(nombreCompleto, correo, usuario, contrasena, "CLIENTE");
    }

    public boolean registrarAdministrador(String nombreCompleto, String correo, String usuario, String contrasena) {
        return registrar(nombreCompleto, correo, usuario, contrasena, "ADMINISTRADOR");
    }

    private boolean registrar(String nombreCompleto, String correo, String usuario, String contrasena, String rol) {
        if (existeUsuario(usuario) || existeCorreo(correo)) {
            return false;
        }
        usuarioRepository.save(new Usuario(usuario, contrasena, rol, nombreCompleto, correo));
        return true;
    }

    public List<Usuario> listarPorRol(String rol) {
        return usuarioRepository.findByRolIgnoreCase(rol);
    }

    public List<Usuario> listarClientes() {
        return listarPorRol("CLIENTE");
    }

    public List<Usuario> listarAdministradores() {
        return listarPorRol("ADMINISTRADOR");
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /* Actualiza nombre, correo, usuario y contraseña. */
    public String actualizarUsuario(int id, String nombreCompleto, String correo,
            String usuario, String contrasena) {

        Usuario actual = buscarPorId(id);
        if (actual == null) {
            return "El usuario no existe.";
        }

        String nombre = nombreCompleto == null ? "" : nombreCompleto.trim();
        String mail = correo == null ? "" : correo.trim();
        String user = usuario == null ? "" : usuario.trim();

        if (nombre.isEmpty() || mail.isEmpty() || user.isEmpty()) {
            return "Nombre, correo y usuario son obligatorios.";
        }

        Optional<Usuario> conMismoUsuario = usuarioRepository.findByUsuarioIgnoreCase(user);
        if (conMismoUsuario.isPresent() && !conMismoUsuario.get().getId().equals(id)) {
            return "El usuario ya está registrado.";
        }

        Optional<Usuario> conMismoCorreo = usuarioRepository.findByCorreoIgnoreCase(mail);
        if (conMismoCorreo.isPresent() && !conMismoCorreo.get().getId().equals(id)) {
            return "El correo electrónico ya está registrado.";
        }

        boolean cambiaContrasena = contrasena != null && !contrasena.isEmpty();
        if (cambiaContrasena && !ContrasenaSeguraValidator.esValida(contrasena)) {
            return ContrasenaSegura.MENSAJE;
        }

        actual.setNombreCompleto(nombre);
        actual.setCorreo(mail);
        actual.setUsuario(user);
        if (cambiaContrasena) {
            actual.setContrasena(contrasena);
        }
        usuarioRepository.save(actual);
        return null;
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }
}