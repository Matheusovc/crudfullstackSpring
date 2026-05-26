package com.exemplo.professorservice.dto;

/**
 * DTO de saída com os dados de um Professor para as respostas da API.
 */
public class ProfessorResponseDTO {

    private Long id;
    private String nome;
    private int idade;
    private String email;
    private String area;
    private boolean ativo;

    public ProfessorResponseDTO() {}

    public ProfessorResponseDTO(Long id, String nome, int idade, String email, String area, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.area = area;
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

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
