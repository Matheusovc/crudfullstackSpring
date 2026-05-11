package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Professor;
import com.exemplo.crudmongo.service.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping({"/api/professores", "/api/professor"}) 
@CrossOrigin(origins = "*") 

public class ProfessorController {
     private final ProfessorService service; 

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Professor> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public Professor criar(@RequestBody Professor professor) {
        return service.salvar(professor);
    }
 
    @PutMapping("/{id}")
    public Professor atualizar(@PathVariable Long id, 
    @RequestBody Professor professor) {
        return service.atualizar(id, professor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
