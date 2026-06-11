package com.exemplo.authservice.controller;

import com.exemplo.authservice.config.JwtUtil;
import com.exemplo.authservice.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AuthController — unico ponto de entrada para autenticacao.
 *
 * POST /auth/login      → retorna JWT
 * POST /auth/registrar  → cria novo usuario (apenas para fins academicos)
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    /**
     * POST /auth/login
     * Body: { "username": "...", "password": "..." }
     * Retorna: { "token": "eyJ...", "role": "PROFESSOR", "username": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("ALUNO");

        String token = jwtUtil.gerarToken(username, role);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", role,
                "username", username,
                "tipo", "Bearer",
                "expiresIn", "3600 segundos"
        ));
    }

    /**
     * POST /auth/registrar
     * Body: { "username": "...", "password": "...", "role": "ALUNO" }
     * Roles validas: ALUNO, PROFESSOR
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String role = body.getOrDefault("role", "ALUNO");

        usuarioService.registrar(username, password, role);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem", "Usuario registrado com sucesso",
                        "username", username,
                        "role", role
                ));
    }
}
