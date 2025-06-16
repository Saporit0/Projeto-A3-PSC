package com.bd;

public class Produto {

    private String nomeProduto;
    private int idProduto;
    private String preco;
    private int saldo;

    public Produto(String nomeProduto, String preco, int saldo) {

        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.saldo = saldo;
    }

    public Produto(int idProduto, String nomeProduto, String preco, int saldo) {

        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.saldo = saldo;
        this.idProduto = idProduto;
    }

    public Produto(int idProduto, String nomeProduto, String preco){
        
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;

    }

    public Produto(String nomeProduto, String preco) {

        this.nomeProduto = nomeProduto;
        this.preco = preco;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
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

    public int getSaldo() {
        return saldo;
    }
}