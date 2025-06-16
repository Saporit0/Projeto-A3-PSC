package com.bd;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastro extends JFrame {

    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JTextField campoCpf;
    private JTextField campoRua;
    private JTextField campoBairro;
    private JTextField campoNumero;
    private JTextField campoCep;
    private JButton botaoCadastrar;

    public TelaCadastro() {
        setTitle("Cadastro de Cliente");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Função utilitária para criar painel de campo
        painelPrincipal.add(criarPainelCampo("Nome:", campoNome = new JTextField(20)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("Email:", campoEmail = new JTextField(20)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("Senha:", campoSenha = new JPasswordField(20)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("CPF:", campoCpf = new JTextField(15)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("Rua:", campoRua = new JTextField(20)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("Bairro:", campoBairro = new JTextField(20)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("Número:", campoNumero = new JTextField(6)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));

        painelPrincipal.add(criarPainelCampo("CEP:", campoCep = new JTextField(10)));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 16)));

        // Botão
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoCadastrar = new JButton("Cadastrar");
        painelBotao.add(botaoCadastrar);

        painelPrincipal.add(painelBotao);

        add(painelPrincipal);
        setVisible(true);

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

    // Método utilitário para criar painéis de campo com label + campo
    private JPanel criarPainelCampo(String textoLabel, JComponent campo) {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(textoLabel);
        label.setPreferredSize(new Dimension(70, 25));
        painel.add(label, BorderLayout.WEST);
        painel.add(campo, BorderLayout.CENTER);
        return painel;
    }

}