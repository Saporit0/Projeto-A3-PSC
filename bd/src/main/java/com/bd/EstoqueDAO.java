package com.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public int adicionarProduto(Produto produto) {
        String sql = "INSERT INTO Produto (nomeProduto, preco) VALUES (?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getPreco());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }

        return idGerado;
    }

    public boolean adicionarEstoque(Estoque estoque) {
        String sql = "INSERT INTO Estoque (idProduto, quantidade) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estoque.getIdProduto());
            stmt.setString(2, estoque.getQuantidade());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar estoque: " + e.getMessage());
            return false;
        }

    }

    public List<Produto> listarProduto() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT  idProduto, nomeProduto, preco FROM Produto";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("idProduto"),
                        rs.getString("nomeProduto"),
                        rs.getString("preco"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public List<Estoque> listarEstoque() {
        List<Estoque> estoques = new ArrayList<>();
        String sql = "SELECT  quantidade FROM Produto";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estoque estoque = new Estoque(
                        rs.getString("Quantidade"));
                estoques.add(estoque);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar quantidade: " + e.getMessage());
        }
        return estoques;
    }

    public boolean editarProduto(Produto produto) {
        String sql = "UPDATE Produto SET nomeProduto = ?, preco = ? WHERE idProduto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getPreco());
            stmt.setInt(3, produto.getIdProduto());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao editar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean editarEstoque(Estoque estoque) {
        String sql = "UPDATE Estoque SET quantidade = ? WHERE idProduto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estoque.getQuantidade());
            stmt.setInt(2, estoque.getIdProduto());
            

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao editar quantidade: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirProduto(int idProduto) {
        String sql = "DELETE FROM Produto WHERE idProduto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirEstoque(int idProduto) {
        String sql = "DELETE FROM Estoque WHERE idProduto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir estoque: " + e.getMessage());
            return false;
        }
    }
}
