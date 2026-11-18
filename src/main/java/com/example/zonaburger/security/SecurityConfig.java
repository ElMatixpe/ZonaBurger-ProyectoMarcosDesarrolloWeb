package com.example.zonaburger.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

import com.example.zonaburger.repository.UsuarioRepository;

/** Configuracion de Spring Security para la parte web, que se evalua
 * despues de la cadena de la API (ApiSecurityConfig):
 * restricción de rutas por rol, inicio y cierre de sesión y cifrado BCrypt */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /* Todas las contraseñas se guardan cifradas con BCrypt. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filtroSeguridad(HttpSecurity http,
            UsuarioRepository usuarioRepository) throws Exception {

        http
            .authorizeHttpRequests(rutas -> rutas

                // Recursos estaticos y pagina de error: siempre accesibles
                .requestMatchers("/css/**", "/js/**", "/img/**", "/video/**",
                        "/images/**", "/webjars/**", "/favicon.ico", "/error").permitAll()

                // Paginas publicas
                .requestMatchers("/", "/productos", "/detalle", "/contacto",
                        "/login", "/registro").permitAll()

                // Confirmar un pedido exige haber iniciado sesion
                .requestMatchers("/carrito/confirmar").hasAnyRole("CLIENTE", "ADMINISTRADOR")

                // El resto del carrito puede usarlo cualquier visitante
                .requestMatchers("/carrito", "/carrito/**").permitAll()

                // Panel de administración: solo ADMINISTRADOR
                .requestMatchers("/vista-admin", "/vista-admin/**").hasRole("ADMINISTRADOR")

                // Cualquier otra ruta requiere sesion iniciada
                .anyRequest().authenticated())

            // Si la cuenta del usuario fue eliminada, se cierra su sesion
            .addFilterAfter(new UsuarioVigenteFilter(usuarioRepository),
                    SecurityContextHolderFilter.class)

            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("usuario")
                .passwordParameter("contrasena")
                .failureUrl("/login?error")
                .permitAll())

            .logout(salida -> salida
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());

        return http.build();
    }
}
