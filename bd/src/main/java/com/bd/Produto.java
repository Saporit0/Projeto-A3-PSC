package com.bd;

public class Produto {

    private String nomeProduto;
    private int idProduto;
    private String preco;
    private int entrada;
    private int saida;

    public Produto(int idProduto, String nomeProduto, String preco, int entrada, int saida) {

        this.nomeProduto = nomeProduto;
        this.idProduto = idProduto;
        this.preco = preco;
        this.entrada = entrada;
        this.saida = saida;
    }

    public Produto(String nomeProduto, String preco, int entrada, int saida) {

        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.entrada = entrada;
        this.saida = saida;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public int getSaida() {
        return saida;
    }

    public void setSaida(int saida) {
        this.saida = saida;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String produto) {
        this.nomeProduto = produto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

}
