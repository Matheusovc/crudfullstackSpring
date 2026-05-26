package com.exemplo.alunoservice.service;

import com.exemplo.alunoservice.dto.AlunoRequestDTO;
import com.exemplo.alunoservice.dto.AlunoResponseDTO;
import com.exemplo.alunoservice.model.Aluno;
import com.exemplo.alunoservice.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    // ── Conversores ─────────────────────────────────────────────────────────

    private AlunoResponseDTO toDTO(Aluno a) {
        return new AlunoResponseDTO(
                a.getId(), a.getNome(), a.getIdade(),
                a.getEmail(), a.getMatricula(), a.isAtivo()
        );
    }

    private Aluno toEntity(AlunoRequestDTO dto) {
        Aluno a = new Aluno();
        a.setNome(dto.getNome());
        a.setIdade(dto.getIdade());
        a.setEmail(dto.getEmail());
        a.setMatricula(dto.getMatricula());
        a.setAtivo(dto.isAtivo());
        return a;
    }

    // ── Operações CRUD ───────────────────────────────────────────────────────

    public List<AlunoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<AlunoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public AlunoResponseDTO salvar(AlunoRequestDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        Aluno existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + id));

        existente.setNome(dto.getNome());
        existente.setIdade(dto.getIdade());
        existente.setEmail(dto.getEmail());
        existente.setMatricula(dto.getMatricula());
        existente.setAtivo(dto.isAtivo());

        return toDTO(repository.save(existente));
    }

    public void desativar(Long id) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + id));
        aluno.setAtivo(false);
        repository.save(aluno);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
