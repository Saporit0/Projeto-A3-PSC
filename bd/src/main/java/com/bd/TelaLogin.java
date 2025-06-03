package com.bd;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoCadastro;

    public TelaLogin() {
        setTitle("Login do Cliente");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Entrar");

        botaoCadastro = new JButton("Cadastre-se");

        add(labelEmail);
        add(campoEmail);
        add(labelSenha);
        add(campoSenha);
        add(new JLabel()); // espaço vazio
        add(botaoLogin);
        add(botaoCadastro);

        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaCadastro();
            }
        });
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());

                Usuario usuario = new Usuario(email, senha);
                UsuarioDAO dao = new UsuarioDAO();

                if (dao.autenticar(usuario)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");

                    dispose();
                    
                    new TelaProduto();
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha inválidos.");
                }
            }

        });

        setVisible(true);
    }

}
