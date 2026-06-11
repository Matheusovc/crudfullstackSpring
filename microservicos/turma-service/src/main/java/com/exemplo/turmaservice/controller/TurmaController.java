package com.exemplo.turmaservice.controller;

import com.exemplo.turmaservice.model.Turma;
import com.exemplo.turmaservice.service.TurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@CrossOrigin(origins = "*")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Turma> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Turma buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Turma nao encontrada: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma criar(@RequestBody Turma turma) {
        return service.salvar(turma);
    }

    @PutMapping("/{id}")
    public Turma atualizar(@PathVariable Long id, @RequestBody Turma turma) {
        return service.atualizar(id, turma);
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
