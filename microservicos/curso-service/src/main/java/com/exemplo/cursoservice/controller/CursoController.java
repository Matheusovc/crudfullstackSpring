package com.exemplo.cursoservice.controller;

import com.exemplo.cursoservice.model.Curso;
import com.exemplo.cursoservice.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Curso> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrado: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso criar(@RequestBody Curso curso) {
        return service.salvar(curso);
    }

    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return service.atualizar(id, curso);
    }

    @PatchMapping("/{id}/desativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long id) {
        service.desativar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
