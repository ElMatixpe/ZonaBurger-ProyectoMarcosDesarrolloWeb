package com.example.zonaburger.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.zonaburger.Model.Usuario;

@Service 
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {

        usuarios.add(
            new Usuario("elMati", "123456", "CLIENTE")
        );

        usuarios.add(
            new Usuario("Tavo488", "987654", "CLIENTE")
        );

        usuarios.add(
            new Usuario("admin", "admin123", "ADMINISTRADOR")
        );
    }

    public Usuario validarUsuario(String usuario, String contrasena) {

        for (Usuario u : usuarios) {

            if (u.getUsuario().equals(usuario)
                    && u.getContrasena().equals(contrasena)) {

                return u;
            }
        }

        return null;
    }
}
