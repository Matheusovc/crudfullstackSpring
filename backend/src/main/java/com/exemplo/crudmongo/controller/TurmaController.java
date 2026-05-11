package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Turma;
import com.exemplo.crudmongo.service.TurmaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping({"/api/turmas", "/api/turma"}) 
@CrossOrigin(origins = "*") 

public class TurmaController {
     private final TurmaService service; 

    public TurmaController(TurmaService service) {
        this.service = service;
    }
  
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','PROFESSOR')")
    public List<Turma> listar() {
        return service.listarTodas();
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public Turma criar(@RequestBody Turma turma) {
        return service.salvar(turma);
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public Turma atualizar(@PathVariable Long id, 
    @RequestBody Turma turma) {
        return service.atualizar(id, turma);
    }
   
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
