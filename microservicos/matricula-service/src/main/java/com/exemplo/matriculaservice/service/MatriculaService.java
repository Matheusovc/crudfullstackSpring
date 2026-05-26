package com.exemplo.matriculaservice.service;

import com.exemplo.matriculaservice.dto.MatriculaDetalhadaDTO;
import com.exemplo.matriculaservice.dto.NomeResponseDTO;
import com.exemplo.matriculaservice.model.Matricula;
import com.exemplo.matriculaservice.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Camada de negócio do microserviço de Matrículas.
 * Toda a lógica de negócio fica aqui ? o controller só delega.
 */
@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final RestTemplate restTemplate;
    private final String pessoaServiceUrl;
    private final String cursoServiceUrl;

    public MatriculaService(MatriculaRepository repository,
                            RestTemplate restTemplate,
                            @Value("${pessoa.service.url}") String pessoaServiceUrl,
                            @Value("${curso.service.url}") String cursoServiceUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.pessoaServiceUrl = pessoaServiceUrl;
        this.cursoServiceUrl = cursoServiceUrl;
    }

    /** Lista todas as matrículas */
    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    /** Busca uma matrícula pelo ID */
    public Optional<Matricula> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public MatriculaDetalhadaDTO buscarDetalhadaPorId(Long id) {
        Matricula matricula = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matricula nao encontrada: " + id));

        return new MatriculaDetalhadaDTO(
                matricula.getId(),
                matricula.getPessoaId(),
                buscarNome(pessoaServiceUrl, matricula.getPessoaId()),
                matricula.getCursoId(),
                buscarNome(cursoServiceUrl, matricula.getCursoId()),
                matricula.getDataMatricula(),
                matricula.isAtivo()
        );
    }

    /** Lista matrículas de uma pessoa específica */
    public List<Matricula> listarPorPessoa(Long pessoaId) {
        return repository.findByPessoaId(pessoaId);
    }

    /** Lista matrículas de um curso específico */
    public List<Matricula> listarPorCurso(Long cursoId) {
        return repository.findByCursoId(cursoId);
    }

    /** Cria uma nova matrícula */
    public Matricula salvar(Matricula matricula) {
        return repository.save(matricula);
    }

    /** Atualiza uma matrícula existente */
    public Matricula atualizar(Long id, Matricula dados) {
        Matricula existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matricula nao encontrada: " + id));
        existente.setPessoaId(dados.getPessoaId());
        existente.setCursoId(dados.getCursoId());
        existente.setDataMatricula(dados.getDataMatricula());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    /** Desativa uma matrícula (soft delete) */
    public void desativar(Long id) {
        Matricula existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matricula nao encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    /** Remove permanentemente uma matrícula */
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private String buscarNome(String baseUrl, Long id) {
        try {
            NomeResponseDTO dto = restTemplate.getForObject(baseUrl + "/" + id, NomeResponseDTO.class);
            if (dto == null || dto.getNome() == null || dto.getNome().isBlank()) {
                return "indisponível";
            }
            return dto.getNome();
        } catch (RestClientException ex) {
            return "indisponível";
        }
    }
}
