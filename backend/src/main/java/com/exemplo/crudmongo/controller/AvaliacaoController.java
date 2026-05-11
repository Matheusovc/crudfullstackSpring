package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Avaliacao;
import com.exemplo.crudmongo.service.AvaliacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping({"/api/avaliacoes", "/api/avaliacao"}) 
@CrossOrigin(origins = "*") 

public class AvaliacaoController {
     private final AvaliacaoService service; 

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Avaliacao> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public Avaliacao criar(@RequestBody Avaliacao avaliacao) {
        return service.salvar(avaliacao);
    }
 
    @PutMapping("/{id}")
    public Avaliacao atualizar(@PathVariable Long id, 
    @RequestBody Avaliacao avaliacao) {
        return service.atualizar(id, avaliacao);
    }
   
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
