package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curso")
@CrossOrigin(origins = "*")
public class CursoController {
    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ALUNO')")
    public List<Curso> listar() {
        return service.listarTodas();
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public Curso criar(@RequestBody Curso curso) {
        return service.salvar(curso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}