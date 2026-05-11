package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Usuario;
import com.exemplo.crudmongo.config.JwtUtil;
import com.exemplo.crudmongo.repository.UsuarioRepository;
import com.exemplo.crudmongo.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UsuarioRepository usuarioRepository,
            UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());
        return Map.of("token", token, "role", usuario.getRole());
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody RegisterRequest request) {
        return usuarioService.salvarUsuario(request.username(), request.password(), request.role());
    }

    public record LoginRequest(String username, String password) {
    }

    public record RegisterRequest(String username, String password, String role) {
    }
}
