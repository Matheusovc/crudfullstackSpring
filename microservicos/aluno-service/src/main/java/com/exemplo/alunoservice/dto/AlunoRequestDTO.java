package com.exemplo.alunoservice.dto;

/** DTO de entrada para criar ou atualizar um Aluno. */
public class AlunoRequestDTO {

    private String nome;
    private int idade;
    private String email;
    private String matricula;
    private boolean ativo;

    public AlunoRequestDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
