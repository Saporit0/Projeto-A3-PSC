package com.bd;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private boolean adm;

    public Usuario(int idUsuario, String nome, String email, String cpf, String senha) {

        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String cpf, String senha) {

        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Usuario(String email, String senha) {

        this.email = email;
        this.senha = senha;
        this.adm = false;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}