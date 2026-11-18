package com.example.zonaburger.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.zonaburger.model.Usuario;

/** Adaptador entre la entidad Usuario y Spring Security
 * Guarda el id para poder volver a buscar al usuario en la base de datos
 * aunque cambie su nombre de usuario */
public class UsuarioPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final String usuario;
    private final String contrasena;
    private final String rol;

    public UsuarioPrincipal(Usuario u) {
        this.id = u.getId();
        this.usuario = u.getUsuario();
        this.contrasena = u.getContrasena();
        this.rol = u.getRol();
    }

    public Integer getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }

    /* CLIENTE => ROLE_CLIENTE, ADMINISTRADOR => ROLE_ADMINISTRADOR */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return usuario;
    }
}
