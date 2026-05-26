package com.exemplo.cursoservice.dto;

/**
 * DTO de saída com os dados de um Curso para as respostas da API.
 */
public class CursoResponseDTO {

    private Long id;
    private String nome;
    private int cargaHoraria;
    private boolean ativo;

    public CursoResponseDTO() {}

    public CursoResponseDTO(Long id, String nome, int cargaHoraria, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
