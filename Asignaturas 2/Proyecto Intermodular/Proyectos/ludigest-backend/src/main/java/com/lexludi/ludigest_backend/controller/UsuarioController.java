package com.lexludi.ludigest_backend.controller;

import com.lexludi.ludigest_backend.model.Usuario;
import com.lexludi.ludigest_backend.repository.UsuarioRepository;
import com.lexludi.ludigest_backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

 // Metodo temporal para poder crear un administrador de prueba desde Postman
    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        
        // BLINDAJE: Si Postman o el frontend no nos envian un rol, forzamos el de ADMIN
        if (nuevoUsuario.getRol() == null || nuevoUsuario.getRol().isEmpty()) {
            nuevoUsuario.setRol("SOCIO");
        }
        
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