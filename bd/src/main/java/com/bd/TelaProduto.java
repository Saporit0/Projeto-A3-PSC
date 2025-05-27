package com.bd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaProduto extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private EstoqueDAO dao;

    public TelaProduto() {
        setTitle("Lista de Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dao = new EstoqueDAO();

        String[] colunas = { "ID", "Produto", "Preço", "Quantidade", "ID_ESTOQUE" };
        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 1, 5, 5));

        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoAtualizar = new JButton("Atualizar");

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoAtualizar);

        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.EAST);

        atualizarTabela();

        botaoAdicionar.addActionListener(e -> abrirDialogoProduto(null));

        botaoEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
                return;
            }
            Produto produtoSelecionado = getProdutoDaLinha(linhaSelecionada);
            abrirDialogoProduto(produtoSelecionado);
        });

        botaoExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Produto produtoSelecionado = getProdutoDaLinha(linhaSelecionada);
                if (dao.excluir(produtoSelecionado.getIdProduto())) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso.");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir Produto.");
                }
            }
        });

        botaoAtualizar.addActionListener(e -> atualizarTabela());

        setVisible(true);
    }

    private void atualizarTabela() {
        List<Produto> produtos = dao.listarProduto();
        modeloTabela.setRowCount(0);

        for (Produto produto : produtos) {
            Object[] linha = {
                    produto.getIdProduto(),
                    produto.getNomeProduto(),
                    produto.getPreco()
            };
            modeloTabela.addRow(linha);
        }

        List<Estoque> estoques = dao.listarEstoque();

        for (Estoque estoque : estoques) {
            Object[] linha = {
                    estoque.getIdEstoque(),
                    estoque.getQuantidade()
            };

            modeloTabela.addRow(linha);
        }
    }

    private Produto getProdutoDaLinha(int linha) {
    int idProduto = (int) modeloTabela.getValueAt(linha, 0);
    String nomeProduto = (String) modeloTabela.getValueAt(linha, 1);
    String preco = (String) modeloTabela.getValueAt(linha, 2);
    return new Produto(idProduto, nomeProduto, preco);

    }
    private Estoque getEstoqueDaLinha(int linha){
        int idEstoque = (int) modeloTabela.getValueAt(linha, 4);
        String quantidade = (String) modeloTabela.getValueAt(linha, 5);
        return new Estoque(quantidade, idEstoque);
    }

    private void abrirDialogoAluno(Produto produto) {
        boolean editar = produto != null; // Se tem aluno → é edição

        // Campos de entrada
        JTextField campoNomeProduto = new JTextField();
        JTextField campoPreco = new JTextField();
        JTextField campoQuantidade = new JTextField();

        if (editar) {
            campoNomeProduto.setText(produto.getNomeProduto());
            campoPreco.setText(produto.getPreco());
            campoQuantidade.setText(produto.getQuantidade());
        }

        Object[] campos = {
                "Nome do Produto:", campoNomeProduto,
                "RA:", campoPreco,
                "Nota:", campoQuantidade,
                "Telefone:", campoTelefone,
                "Senha:", campoSenha
        };

        int opcao = JOptionPane.showConfirmDialog(this, campos, editar ? "Editar Aluno" : "Adicionar Aluno", JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                String nomeProduto  = campoNomeProduto.getText();
                String ra = campoPreco.getText();
                double nota = Double.parseDouble(campoQuantidade.getText());
                String telefone = campoTelefone.getText();
                String senha = campoSenha.getText();

                // Validação básica
                if (nome.isEmpty() || ra.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
                    return;
                }

                if (editar) {
                    Aluno alunoEditado = new Aluno(aluno.getIdAluno(), nome, ra, nota, telefone, senha);
                    if (dao.editar(alunoEditado)) {
                        JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso.");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar aluno.");
                    }
                } else {
                    Aluno novoAluno = new Aluno(nome, ra, nota, telefone, senha);
                    if (dao.adicionar(novoAluno)) {
                        JOptionPane.showMessageDialog(this, "Aluno adicionado com sucesso.");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao adicionar aluno.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nota deve ser um número válido.");
            }
        }
    }

}

