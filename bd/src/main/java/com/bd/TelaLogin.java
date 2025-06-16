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
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel painelEmail = new JPanel(new BorderLayout(5, 5));
        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField(20);
        painelEmail.add(labelEmail, BorderLayout.WEST);
        painelEmail.add(campoEmail, BorderLayout.CENTER);

        JPanel painelSenha = new JPanel(new BorderLayout(5, 5));
        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(20);
        painelSenha.add(labelSenha, BorderLayout.WEST);
        painelSenha.add(campoSenha, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botaoLogin = new JButton("Entrar");
        botaoCadastro = new JButton("Cadastre-se");
        painelBotoes.add(botaoLogin);
        painelBotoes.add(botaoCadastro);

        painelPrincipal.add(painelEmail);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        painelPrincipal.add(painelSenha);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        painelPrincipal.add(painelBotoes);

        add(painelPrincipal);

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
                boolean adm = true;

                Usuario usuario = new Usuario(email, senha, adm);

                if (usuario.getEmail().equals("admin") && usuario.getSenha().equals("admin") && usuario.isAdm() == true) {
                    dispose();
                    new TelaProduto();

                } else {
                    dispose();
                    new TelaProdutoUsuario();
                }

            }

        });

        setVisible(true);
    }
}