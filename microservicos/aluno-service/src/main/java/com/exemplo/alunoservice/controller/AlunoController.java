package com.exemplo.alunoservice.controller;

import com.exemplo.alunoservice.dto.AlunoRequestDTO;
import com.exemplo.alunoservice.dto.AlunoResponseDTO;
import com.exemplo.alunoservice.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    /** GET /alunos → lista todos os alunos */
    @GetMapping
    public List<AlunoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    /** GET /alunos/{id} → busca por ID */
    @GetMapping("/{id}")
    public AlunoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado: " + id));
    }

    /** POST /alunos → cria novo aluno */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponseDTO criar(@RequestBody AlunoRequestDTO dto) {
        return service.salvar(dto);
    }

    /** PUT /alunos/{id} → atualiza aluno completo */
    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    /** PATCH /alunos/{id}/desativar → soft delete */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    /** DELETE /alunos/{id} → exclui aluno */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
