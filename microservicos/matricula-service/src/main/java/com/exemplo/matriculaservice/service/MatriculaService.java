package com.exemplo.matriculaservice.service;

import com.exemplo.matriculaservice.dto.MatriculaDetalhadaDTO;
import com.exemplo.matriculaservice.model.Matricula;
import com.exemplo.matriculaservice.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final RestTemplate restTemplate;

    public MatriculaService(MatriculaRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    public Optional<Matricula> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Matricula> listarPorPessoa(Long pessoaId) {
        return repository.findByPessoaId(pessoaId);
    }

    public List<Matricula> listarPorCurso(Long cursoId) {
        return repository.findByCursoId(cursoId);
    }

    public Matricula salvar(Matricula matricula) {
        return repository.save(matricula);
    }

    public Matricula atualizar(Long id, Matricula dados) {
        Matricula existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada: " + id));
        existente.setPessoaId(dados.getPessoaId());
        existente.setCursoId(dados.getCursoId());
        existente.setDataMatricula(dados.getDataMatricula());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    public void desativar(Long id) {
        Matricula existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public MatriculaDetalhadaDTO buscarDetalhada(Long id) {
        Matricula matricula = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada: " + id));

        String nomePessoa = "indisponível";
        try {
            Map<String, Object> pessoa = restTemplate.getForObject("http://localhost:8082/api/pessoas/" + matricula.getPessoaId(), Map.class);
            if (pessoa != null && pessoa.get("nome") != null) {
                nomePessoa = pessoa.get("nome").toString();
            }
        } catch (Exception e) {
            // Fallback mantido
        }

        String nomeCurso = "indisponível";
        try {
            Map<String, Object> curso = restTemplate.getForObject("http://localhost:8083/api/cursos/" + matricula.getCursoId(), Map.class);
            if (curso != null && curso.get("nome") != null) {
                nomeCurso = curso.get("nome").toString();
            }
        } catch (Exception e) {
            // Fallback mantido
        }

        MatriculaDetalhadaDTO dto = new MatriculaDetalhadaDTO();
        dto.setId(matricula.getId());
        dto.setPessoaId(matricula.getPessoaId());
        dto.setNomePessoa(nomePessoa);
        dto.setCursoId(matricula.getCursoId());
        dto.setNomeCurso(nomeCurso);
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setAtivo(matricula.isAtivo());

        return dto;
    }
}
