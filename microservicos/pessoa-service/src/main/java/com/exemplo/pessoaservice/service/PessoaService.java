package com.exemplo.pessoaservice.service;

import com.exemplo.pessoaservice.model.Pessoa;
import com.exemplo.pessoaservice.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) { this.repository = repository; }

    public List<Pessoa> listarTodas() { return repository.findAll(); }
    public Optional<Pessoa> buscarPorId(Long id) { return repository.findById(id); }

    public Optional<Pessoa> buscarPorEmail(String email) { return repository.findByEmail(email); }
    public Optional<Pessoa> buscarPorCpf(String cpf) { return repository.findByCpf(cpf); }

    public Pessoa salvar(Pessoa entidade) { return repository.save(entidade); }

    public Pessoa atualizar(Long id, Pessoa dados) {
        Pessoa existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada: " + id));
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setCpf(dados.getCpf());
        existente.setDataNascimento(dados.getDataNascimento());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    public void desativar(Long id) {
        Pessoa existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    public void excluir(Long id) { repository.deleteById(id); }
}