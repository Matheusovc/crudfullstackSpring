package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {
    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pessoa> listar() {
        return service.listarTodas();
    }

    @GetMapping("/nome")
    public List<Pessoa> buscarPorNome(@RequestParam("valor") String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/idade")
    public List<Pessoa> buscarPorIdade(@RequestParam("valor") Integer idade) {
        return service.buscarPorIdade(idade);
    }

    @GetMapping("/pagina")
    public Page<Pessoa> listarPaginado(@RequestParam(defaultValue = "0") int numero, @RequestParam(defaultValue = "10") int tamanho) {
        return service.listarPaginado(numero, tamanho);
    }

    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return service.salvar(pessoa);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
