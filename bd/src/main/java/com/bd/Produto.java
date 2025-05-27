package com.bd;

public class Produto {

    private String nomeProduto;
    private int idProduto;
    private String preco;

    public Produto(int idProduto, String nomeProduto, String preco){

        this.nomeProduto = nomeProduto;
        this.idProduto = idProduto;
        this.preco = preco;
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
