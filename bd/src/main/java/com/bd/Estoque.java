package com.bd;

public class Estoque{

    private int idEstoque;
    private String quantidade;
    private int idProduto;

    public Estoque(int idProduto, String quantidade, int idEstoque) {

        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.idEstoque = idEstoque;
    }

    public Estoque(String quantidade, int idEstoque) {

        this.quantidade = quantidade;
        this.idEstoque = idEstoque;
    }

    public Estoque(String quantidade){
        
        this.quantidade = quantidade;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdEstoque() {
        return idEstoque;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

}
