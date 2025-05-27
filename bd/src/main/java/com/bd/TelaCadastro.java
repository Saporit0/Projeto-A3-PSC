package com.bd;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastro extends JFrame {

    private JTextField campoEmail;
    private JTextField campoNome;
    private JTextField campoCpf;
    private JPasswordField campoSenha;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoNumero;
    private JTextField campoCep;
    private JButton botaoCadastrar;

    public TelaCadastro() {

        setTitle("Cadastro do Cliente");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel labelCampoEmail = new JLabel("Email:");
        campoEmail = new JTextField();

        JLabel labelCampoNome = new JLabel("Nome:");
        campoNome = new JTextField();

        JLabel labelCampoCpf = new JLabel("Cpf:");
        campoCpf = new JTextField();

        JLabel labelCampoSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        JLabel labelCampoRua = new JLabel("Rua:");
        campoRua = new JTextField();

        JLabel labelCampoBairro = new JLabel("Bairro:");
        campoBairro = new JTextField();

        JLabel labelCampoNumero = new JLabel("Numero:");
        campoNumero = new JTextField();

        JLabel labelCampoCep = new JLabel("Cep:");
        campoCep = new JTextField();

        botaoCadastrar = new JButton("Cadastrar");

        add(labelCampoEmail);
        add(campoEmail);
        add(labelCampoNome);
        add(campoNome);
        add(labelCampoCpf);
        add(campoCpf);
        add(labelCampoSenha);
        add(campoSenha);
        add(labelCampoRua);
        add(campoRua);
        add(labelCampoBairro);
        add(campoBairro);
        add(labelCampoNumero);
        add(campoNumero);
        add(labelCampoCep);
        add(campoCep);
        add(botaoCadastrar);

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String nome = campoNome.getText();
                String cpf = campoCpf.getText();
                String senha = new String(campoSenha.getPassword());
                String rua = campoRua.getText();
                String bairro = campoBairro.getText();
                String numero = campoNumero.getText();
                String cep = campoCep.getText();

                Usuario usuario = new Usuario(nome, email, cpf, senha);
                UsuarioDAO dao = new UsuarioDAO();
                int idUsuario = dao.cadastroUsuario(usuario);
                
                if (idUsuario != -1) {
                    Endereco endereco = new Endereco(rua, bairro, numero, cep, idUsuario);
                    if (dao.cadastroEndereco(endereco))

                        JOptionPane.showMessageDialog(null, "Cadastro bem-sucedido!");

                    dispose();

                    new TelaLogin();

                } else {
                    JOptionPane.showMessageDialog(null, "errou");
                }
            }

        });

        setVisible(true);
    }
}
