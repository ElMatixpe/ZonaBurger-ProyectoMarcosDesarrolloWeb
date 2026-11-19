package com.example.zonaburger.security;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.zonaburger.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/* Cierra la sesion de un usuario cuya cuenta fue eliminada */
public class UsuarioVigenteFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;

    public UsuarioVigenteFilter(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /* Los recursos estaticos no necesitan esta comprobacion */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String ruta = request.getRequestURI();
        return ruta.startsWith("/css/") || ruta.startsWith("/js/")
                || ruta.startsWith("/img/") || ruta.startsWith("/video/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain cadena) throws ServletException, IOException {

        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacion != null
                && autenticacion.getPrincipal() instanceof UsuarioPrincipal principal
                && principal.getId() != null
                && !usuarioRepository.existsById(principal.getId())) {

            // La cuenta ya no existe: se anula la autenticacion y se cierra la sesion
            SecurityContextHolder.clearContext();
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
        }

        cadena.doFilter(request, response);
    }
}
