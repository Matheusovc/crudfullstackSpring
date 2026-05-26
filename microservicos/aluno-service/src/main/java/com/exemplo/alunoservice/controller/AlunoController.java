package com.exemplo.alunoservice.controller;

import com.exemplo.alunoservice.dto.AlunoRequestDTO;
import com.exemplo.alunoservice.dto.AlunoResponseDTO;
import com.exemplo.alunoservice.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
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
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        try {
            return ResponseEntity.ok(service.atualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** PATCH /alunos/{id}/desativar → soft delete */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        try {
            service.desativar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /alunos/{id} → exclui aluno */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
