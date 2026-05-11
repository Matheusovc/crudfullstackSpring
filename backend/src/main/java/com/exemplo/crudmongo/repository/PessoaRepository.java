package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    List<Pessoa> findByIdade(Integer idade);
    Page<Pessoa> findAll(Pageable pageable);
}
