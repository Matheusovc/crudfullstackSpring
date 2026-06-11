package com.exemplo.disciplinaservice.controller;

import com.exemplo.disciplinaservice.model.Disciplina;
import com.exemplo.disciplinaservice.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Disciplina> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Disciplina buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Disciplina nao encontrada: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina criar(@RequestBody Disciplina disciplina) {
        return service.salvar(disciplina);
    }

    @PutMapping("/{id}")
    public Disciplina atualizar(@PathVariable Long id, @RequestBody Disciplina disciplina) {
        return service.atualizar(id, disciplina);
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
