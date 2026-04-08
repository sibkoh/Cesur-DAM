package com.lexludi.ludigest_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.Usuario;
import com.lexludi.ludigest_backend.repository.UsuarioRepository;
import com.lexludi.ludigest_backend.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

 // Metodo para registrar usuarios desde Postman o el Frontend
    // BLINDAJE: Usamos @Valid. Ya no hace falta el 'if' manual.
    @PostMapping("/registro")
    public Usuario registrarUsuario(@Valid @RequestBody Usuario nuevoUsuario) {
        
        return usuarioRepository.save(nuevoUsuario);
    }

    // Nuestro endpoint de login para validar el acceso
    @PostMapping("/login")
    public Usuario login(@RequestBody Map<String, String> credenciales) {
        // Extraemos los datos del JSON recibido
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        // Llamamos a nuestro servicio y devolvemos la informacion del usuario si hay exito
        return usuarioService.autenticar(username, password);
    }
    
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    
}