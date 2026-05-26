package com.exemplo.alunoservice.dto;

/** DTO de saída com os dados de um Aluno. */
public class AlunoResponseDTO {

    private Long id;
    private String nome;
    private int idade;
    private String email;
    private String matricula;
    private boolean ativo;

    public AlunoResponseDTO() {}

    public AlunoResponseDTO(Long id, String nome, int idade, String email, String matricula, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.matricula = matricula;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
