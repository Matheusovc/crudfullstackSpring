package com.exemplo.cursoservice.dto;

/**
 * DTO de entrada para criar ou atualizar um Curso.
 */
public class CursoRequestDTO {

    private String nome;
    private int cargaHoraria;
    private boolean ativo;

    public CursoRequestDTO() {}

    public CursoRequestDTO(String nome, int cargaHoraria, boolean ativo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ativo = ativo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
