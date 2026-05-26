package com.exemplo.matriculaservice.dto;

/** DTO de entrada para criar ou atualizar uma Matricula. */
public class MatriculaRequestDTO {

    private Long pessoaId;
    private Long cursoId;
    private String dataMatricula;
    private boolean ativo;

    public MatriculaRequestDTO() {}

    public Long getPessoaId() { return pessoaId; }
    public void setPessoaId(Long pessoaId) { this.pessoaId = pessoaId; }

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public String getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(String dataMatricula) { this.dataMatricula = dataMatricula; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
