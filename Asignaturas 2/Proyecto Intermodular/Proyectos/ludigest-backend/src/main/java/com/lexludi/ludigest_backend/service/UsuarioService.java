package com.lexludi.ludigest_backend.service;

import com.lexludi.ludigest_backend.model.Usuario;
import com.lexludi.ludigest_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Metodo para comprobar si las credenciales son correctas
    public Usuario autenticar(String username, String passwordIngresada) {
        
        // 1. Buscamos si existe el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: Credenciales invalidas"));

        // 2. Comparamos la contrasena
        // NOTA IMPORTANTE DE SEGURIDAD: 
        // Actualmente comparamos texto plano. En el futuro, aqui aplicaremos BCrypt:
        // if (!passwordEncoder.matches(passwordIngresada, usuario.getPassword())) { ... }
        
        if (!usuario.getPassword().equals(passwordIngresada)) {
            throw new RuntimeException("Error: Credenciales invalidas");
        }

        // Si llegamos aqui, el login es correcto
        return usuario;
    }
}