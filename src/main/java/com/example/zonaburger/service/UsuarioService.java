package com.example.zonaburger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.zonaburger.model.Usuario;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        usuarios.add(new Usuario("elMati", "123456", "CLIENTE", "Matías Cliente", "elMati@zonaburger.com"));
        usuarios.add(new Usuario("Tavo488", "987654", "CLIENTE", "Gustavo Cliente", "tavo488@zonaburger.com"));
        usuarios.add(new Usuario("admin", "admin123", "ADMINISTRADOR", "Administrador Zona Burger", "admin@zonaburger.com"));
    }

    public Usuario validarUsuario(String usuario, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equalsIgnoreCase(usuario) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    public boolean existeUsuario(String usuario) {
        return usuarios.stream().anyMatch(u -> u.getUsuario().equalsIgnoreCase(usuario));
    }

    public boolean existeCorreo(String correo) {
        return usuarios.stream().anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));
    }

    public boolean registrarUsuario(String nombreCompleto, String correo, String usuario, String contrasena) {
        if (existeUsuario(usuario) || existeCorreo(correo)) {
            return false;
        }
        usuarios.add(new Usuario(usuario, contrasena, "CLIENTE", nombreCompleto, correo));
        return true;
    }

    public List<Usuario> listarUsuarios() { return usuarios; }

    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol().equalsIgnoreCase(rol)) resultado.add(u);
        }
        return resultado;
    }

    public List<Usuario> listarClientes() { return listarPorRol("CLIENTE"); }
    public List<Usuario> listarAdministradores() { return listarPorRol("ADMINISTRADOR"); }
}
