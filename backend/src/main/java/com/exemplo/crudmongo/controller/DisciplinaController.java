package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Disciplina;
import com.exemplo.crudmongo.service.DisciplinaService;
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
    public List<Disciplina> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public Disciplina criar(@RequestBody Disciplina disciplina) {
        return service.salvar(disciplina);
    }
 
    @PutMapping("/{id}")
    public Disciplina atualizar(@PathVariable Long id, 
    @RequestBody Disciplina disciplina) {
        return service.atualizar(id, disciplina);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
