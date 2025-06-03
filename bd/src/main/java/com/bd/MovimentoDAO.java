package com.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimentoDAO {
    
    public boolean adicionarMovimento(Movimento movimento) {
        String sql = "INSERT INTO Movimento (idProduto, preco, quantidade, eEntrada) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, movimento.getIdProduto());
            stmt.setDouble(2, movimento.getPreco());
            stmt.setInt(3, movimento.getQuantidade());
            stmt.setBoolean(4, movimento.isEEntrada());
            
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar Movimento: " + e.getMessage());
            return false;
        }

    }

    public boolean excluirMovimento(int idMovimento) {
        String sql = "DELETE FROM Movimento WHERE idMovimento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMovimento); 

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir Movimento: " + e.getMessage());
            return false;
        }
    }

     public List<Movimento> listarMovimento() {
        List<Movimento> movimentos = new ArrayList<>(); 
        String sql = "SELECT idMovimento, idProduto, preco, quantidade, eEntrada FROM Movimento";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movimento movimento = new Movimento(
                        rs.getInt("idMovimento"),
                        rs.getInt("idProduto"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("eEntrada")
                );
                movimentos.add(movimento); 
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar movimentos: " + e.getMessage());
        }
        return movimentos; 
    }
}
