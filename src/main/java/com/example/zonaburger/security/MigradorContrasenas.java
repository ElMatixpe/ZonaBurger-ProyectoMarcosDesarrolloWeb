package com.example.zonaburger.security;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.repository.UsuarioRepository;

/** Se ejecuta al iniciar la aplicacion y cifra con BCrypt las contraseñas que
 * todavia esten guardadas en texto plano */
@Component
public class MigradorContrasenas implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MigradorContrasenas.class);
    private static final Pattern HASH_BCRYPT = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$.{53}$");

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public MigradorContrasenas(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        int cifradas = 0;

        for (Usuario u : usuarioRepository.findAll()) {
            String actual = u.getContrasena();
            if (actual != null && !HASH_BCRYPT.matcher(actual).matches()) {
                u.setContrasena(passwordEncoder.encode(actual));
                usuarioRepository.save(u);
                cifradas++;
            }
        }

        if (cifradas > 0) {
            log.info("Contraseñas cifradas con BCrypt: {}", cifradas);
        }
    }
}
