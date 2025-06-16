package com.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public int adicionarProduto(Produto produto) {
        String sql = "INSERT INTO Produto (nomeProduto, preco, saldo) VALUES (?, ?, ?)";
        int idGerado = -1;
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getPreco());
            stmt.setInt(3, produto.getSaldo());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar Produto: " + e.getMessage());

        }
        return idGerado;
    }

     public boolean excluirProduto(int idProduto) {
        String sql = "DELETE FROM Produto WHERE idProduto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto); 

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir Produto: " + e.getMessage());
            return false;
        }
    }

    public boolean editarProduto(Produto produto) {
        String sql = "UPDATE produto SET nomeProduto = ?, preco = ?, saldo = ? WHERE idProduto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getPreco());
            stmt.setInt(3, produto.getSaldo());
            stmt.setInt(4, produto.getIdProduto());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao editar produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarProduto() {
        List<Produto> produtos = new ArrayList<>(); 
        String sql = "SELECT idProduto, nomeProduto, preco, saldo FROM Produto";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("idProduto"),
                        rs.getString("nomeProduto"),
                        rs.getString("preco"),
                        rs.getInt("saldo")
                );
                produtos.add(produto); 
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos; 
    }

    public Produto buscarProdutoPorId(int id) {
    String sql = "SELECT * FROM Produto WHERE idProduto = ?";
    try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Produto(
                rs.getInt("idProduto"),
                rs.getString("nomeProduto"),
                rs.getString("preco"),
                rs.getInt("saldo")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
   
}