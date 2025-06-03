package com.bd;

public class Movimento {

    private int idMovimento;
    private int idProduto;
    private double preco;
    private int quantidade;
    private boolean eEntrada;

    public Movimento(int idMovimento, int idProduto, double preco, int quantidade, boolean eEntrada) {

        this.idMovimento = idMovimento;
        this.idProduto = idProduto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.eEntrada = eEntrada;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isEEntrada() {
        return eEntrada;
    }

    public void setEEntrada(boolean eEntrada) {
        this.eEntrada = eEntrada;
    }

}
