package com.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean autenticar(Usuario usuario) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());

            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return false;
        }
    }

    public int cadastroUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, cpf, senha) VALUES (?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getSenha());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuario: " + e.getMessage());
        }

        return idGerado;
    }

    public boolean cadastroEndereco(Endereco endereco) {
        String sql = "INSERT INTO Endereco (rua, bairro, numero, cep, idUsuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getBairro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getCep());
            stmt.setInt(5, endereco.getIdUsuario());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar endereço: " + e.getMessage());
            return false;
        }

    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT idUsuario, nome, email, cpf, senha FROM Usuario";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            // Para cada linha, cria um objeto Aluno e adiciona à lista
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("senha"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar dados do usuário: " + e.getMessage());
        }
        return usuarios;
    }

    public List<Endereco> listarEndereco() {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT  idUsuario, rua, bairro, numero, cep FROM Endereco";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            // Para cada linha, cria um objeto Aluno e adiciona à lista
            while (rs.next()) {
                Endereco endereco = new Endereco(

                        rs.getString("rua"),
                        rs.getString("bairro"),
                        rs.getString("numero"),
                        rs.getString("cep"),
                        rs.getInt("idUsuario"));
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar dados de endereço do usuário: " + e.getMessage());
        }
        return enderecos;
    }

    public boolean excluirUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirEndereco(int idUsuario) {
        String sql = "DELETE FROM Endereco WHERE idUsuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir endereço: " + e.getMessage());
            return false;
        }
    }
}
