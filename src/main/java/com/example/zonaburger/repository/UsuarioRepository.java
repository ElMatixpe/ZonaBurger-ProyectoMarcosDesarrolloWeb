package com.example.zonaburger.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zonaburger.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsuarioIgnoreCase(String usuario);

    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    boolean existsByUsuarioIgnoreCase(String usuario);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Usuario> findByRolIgnoreCase(String rol);
}