package com.exemplo.matriculaservice.controller;

import com.exemplo.matriculaservice.dto.MatriculaDetalhadaDTO;
import com.exemplo.matriculaservice.model.Matricula;
import com.exemplo.matriculaservice.service.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin(origins = "*")
public class MatriculaController {

    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Matricula> listarTodas() { return service.listarTodas(); }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/detalhada")
    public ResponseEntity<MatriculaDetalhadaDTO> buscarDetalhada(@PathVariable Long id) {
        try { return ResponseEntity.ok(service.buscarDetalhada(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/pessoa/{pessoaId}")
    public List<Matricula> listarPorPessoa(@PathVariable Long pessoaId) { return service.listarPorPessoa(pessoaId); }

    @GetMapping("/curso/{cursoId}")
    public List<Matricula> listarPorCurso(@PathVariable Long cursoId) { return service.listarPorCurso(cursoId); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Matricula criar(@RequestBody Matricula matricula) { return service.salvar(matricula); }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> atualizar(@PathVariable Long id, @RequestBody Matricula matricula) {
        try { return ResponseEntity.ok(service.atualizar(id, matricula)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        try { service.desativar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { service.excluir(id); }
}
