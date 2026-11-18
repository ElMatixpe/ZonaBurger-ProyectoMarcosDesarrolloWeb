package com.example.zonaburger.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.zonaburger.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Se ejecuta en cada peticion a /api/**: lee la cabecera
 * "Authorization: Bearer <token>", valida el JWT y, si es correcto,
 * deja al usuario autenticado para esa peticion */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIJO = "Bearer ";

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain cadena) throws ServletException, IOException {

        String cabecera = request.getHeader("Authorization");

        if (cabecera != null && cabecera.startsWith(PREFIJO)) {
            String token = cabecera.substring(PREFIJO.length());

            try {
                Claims datos = jwtService.validar(token);
                Integer id = datos.get("id", Integer.class);

                // Se vuelve a leer al usuario: si fue eliminado, el token deja de servir
                // y el rol siempre es el que esta en la base de datos.
                if (id != null) {
                    usuarioRepository.findById(id).ifPresent(usuario -> {
                        UsuarioPrincipal principal = new UsuarioPrincipal(usuario);
                        UsernamePasswordAuthenticationToken autenticacion =
                                new UsernamePasswordAuthenticationToken(
                                        principal, null, principal.getAuthorities());
                        autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(autenticacion);
                    });
                }
            } catch (JwtException | IllegalArgumentException e) {
                // Token invalido o vencido: no se autentica y la cadena respondera 401
                SecurityContextHolder.clearContext();
            }
        }

        cadena.doFilter(request, response);
    }
}
