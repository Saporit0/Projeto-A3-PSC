package com.bd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaProduto extends JFrame {

    private JTable tabelaProduto;
    private DefaultTableModel modeloTabelaProduto;
    private ProdutoDAO dao;

    public TelaProduto() {

        setTitle("Lista de Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dao = new ProdutoDAO();

        String[] colunasProduto = { "ID_Produto", "Nome", "Preço", "Entrada", "Saida" };
        modeloTabelaProduto = new DefaultTableModel(null, colunasProduto) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProduto = new JTable(modeloTabelaProduto);
        JScrollPane scroll1 = new JScrollPane(tabelaProduto);

        JPanel painelBotoesProduto = new JPanel();
        painelBotoesProduto.setLayout(new GridLayout(4, 1, 5, 5));

        JButton botaoAdicionarProduto = new JButton("Adicionar");
        JButton botaoEditarProduto = new JButton("Editar");
        JButton botaoExcluirProduto = new JButton("Excluir");
        JButton botaoAtualizarProduto = new JButton("Atualizar");
        JButton botaoMovimento = new JButton("Controle de Movimento");

        painelBotoesProduto.add(botaoAdicionarProduto);
        painelBotoesProduto.add(botaoEditarProduto);
        painelBotoesProduto.add(botaoExcluirProduto);
        painelBotoesProduto.add(botaoAtualizarProduto);
        painelBotoesProduto.add(botaoMovimento);

        setLayout(new BorderLayout(10, 10));
        add(scroll1, BorderLayout.CENTER);
        add(painelBotoesProduto, BorderLayout.EAST);

        atualizarTabelaProduto();

        botaoAdicionarProduto.addActionListener(e -> abrirDialogoProduto(null));

        botaoEditarProduto.addActionListener(e -> {
            int linhaSelecionada = tabelaProduto.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
                return;
            }
            Produto produtoSelecionado = getProdutoDaLinha(linhaSelecionada);
            abrirDialogoProduto(produtoSelecionado);
        });

        botaoExcluirProduto.addActionListener(e -> {
            int linhaSelecionada = tabelaProduto.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Excluir",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Produto produtoSelecionado = getProdutoDaLinha(linhaSelecionada);
                if (dao.excluirProduto(produtoSelecionado.getIdProduto())) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso.");
                    atualizarTabelaProduto();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir produto.");
                }
            }
        });

        botaoAtualizarProduto.addActionListener(e -> atualizarTabelaProduto());

        setVisible(true);

        botaoMovimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaMovimento();
            }
        });
    }
    

    private void atualizarTabelaProduto() {
        List<Produto> produtos = dao.listarProduto();
        modeloTabelaProduto.setRowCount(0);

        for (Produto produto : produtos) {
            Object[] linha = {
                    produto.getIdProduto(),
                    produto.getNomeProduto(),
                    produto.getPreco(),
                    produto.getEntrada(),
                    produto.getSaida()
            };
            modeloTabelaProduto.addRow(linha);
        }
    }

    private Produto getProdutoDaLinha(int linha) {
        int idProduto = (int) modeloTabelaProduto.getValueAt(linha, 0);
        String nomeProduto = (String) modeloTabelaProduto.getValueAt(linha, 1);
        String preco = (String) modeloTabelaProduto.getValueAt(linha, 2);
        int entrada = (int) modeloTabelaProduto.getValueAt(linha, 3);
        int saida = (int) modeloTabelaProduto.getValueAt(linha, 4);

        return new Produto(idProduto, nomeProduto, preco, entrada, saida);
    }

    private void abrirDialogoProduto(Produto produto) {
        boolean editar = produto != null;

        JTextField campoNomeProduto = new JTextField();
        JTextField campoPreco = new JTextField();
        JTextField campoEntrada = new JTextField();
        JTextField campoSaida = new JTextField();

        if (editar) {
            campoNomeProduto.setText(produto.getNomeProduto());
            campoPreco.setText(produto.getPreco());
            campoEntrada.setText(String.valueOf(produto.getEntrada()));
            campoSaida.setText(String.valueOf(produto.getSaida()));
        }

        Object[] campos = {
                "Nome:", campoNomeProduto,
                "Preço:", campoPreco,
                "Entrada:", campoEntrada,
                "Saida:", campoSaida
        };

        int opcao = JOptionPane.showConfirmDialog(this, campos, editar ? "Editar Produto" : "Adicionar Produto",
                JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                String nomeProduto = campoNomeProduto.getText();
                String preco = campoPreco.getText();
                String entradaTexto = campoEntrada.getText();
                String saidaTexto = campoSaida.getText();

                if (nomeProduto.isEmpty() || preco.isEmpty() || entradaTexto.isEmpty() || saidaTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
                    return;
                }
                int entrada = Integer.parseInt(entradaTexto);
                int saida = Integer.parseInt(saidaTexto);
                if (editar) {
                    Produto produtoEditado = new Produto(produto.getIdProduto(), nomeProduto, preco, entrada, saida);
                    if (dao.editarProduto(produtoEditado)) {
                        JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso.");
                        atualizarTabelaProduto();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar produto.");
                    }
                } else {
                    Produto novoProduto = new Produto(nomeProduto, preco, entrada, saida);
                    if (dao.adicionarProduto(novoProduto) != -1) {
                        JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso.");
                        atualizarTabelaProduto();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao adicionar produto.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar/editar produto.");

            }
        }
    }
}
