package com.exemplo.professorservice.dto;

/**
 * DTO de entrada para criar ou atualizar um Professor.
 */
public class ProfessorRequestDTO {

    private String nome;
    private int idade;
    private String email;
    private String area;
    private boolean ativo;

    public ProfessorRequestDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
