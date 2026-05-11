package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Disciplina;
import com.exemplo.crudmongo.service.DisciplinaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping({"/api/disciplinas", "/api/Disciplinas"}) 
@CrossOrigin(origins = "*") 

public class DisciplinaController {
     private final DisciplinaService service; 

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','PROFESSOR')")
    public List<Disciplina> listar() {
        return service.listarTodas();
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public Disciplina criar(@RequestBody Disciplina disciplina) {
        return service.salvar(disciplina);
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public Disciplina atualizar(@PathVariable Long id, 
    @RequestBody Disciplina disciplina) {
        return service.atualizar(id, disciplina);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
