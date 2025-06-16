
package com.bd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaMovimento extends JFrame {

    private JTable tabelaMovimento;
    private DefaultTableModel modeloTabelaMovimento;
    private MovimentoDAO dao;

    public TelaMovimento() {

        setTitle("Lista de Movimentos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dao = new MovimentoDAO();

        String[] colunasMovimento = { "ID_Movimento", "ID_Produto", "Preço", "Quantidade", "É_Entrada" };
        modeloTabelaMovimento = new DefaultTableModel(null, colunasMovimento) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaMovimento = new JTable(modeloTabelaMovimento);
        JScrollPane scroll2 = new JScrollPane(tabelaMovimento);

        JPanel painelBotoesMovimento = new JPanel();
        painelBotoesMovimento.setLayout(new GridLayout(4, 1, 5, 5));

        JButton botaoAdicionarMovimento = new JButton("Adicionar");
        JButton botaoExcluirMovimento = new JButton("Excluir");
        JButton botaoAtualizarMovimento = new JButton("Atualizar");
        JButton botaoProduto = new JButton("Controle de Produtos");

        painelBotoesMovimento.add(botaoAdicionarMovimento);
        painelBotoesMovimento.add(botaoExcluirMovimento);
        painelBotoesMovimento.add(botaoAtualizarMovimento);
        painelBotoesMovimento.add(botaoProduto);

        setLayout(new BorderLayout(10, 10));
        add(scroll2, BorderLayout.CENTER);
        add(painelBotoesMovimento, BorderLayout.EAST);

        atualizarTabelaMovimento();

        botaoAdicionarMovimento.addActionListener(e -> abrirDialogoMovimento(null));

        botaoExcluirMovimento.addActionListener(e -> {
            int linhaSelecionada = tabelaMovimento.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um movimento para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Excluir",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Movimento movimentoSelecionado = getMovimentoDaLinha(linhaSelecionada);
                if (dao.excluirMovimento(movimentoSelecionado.getIdMovimento())) {
                    JOptionPane.showMessageDialog(this, "Movimento excluído com sucesso.");
                    atualizarTabelaMovimento();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir movimento.");
                }
            }
        });

        botaoAtualizarMovimento.addActionListener(e -> atualizarTabelaMovimento());

        setVisible(true);

        botaoProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaProduto();
            }
        });
    }

    private void atualizarTabelaMovimento() {
        List<Movimento> movimentos = dao.listarMovimento();
        modeloTabelaMovimento.setRowCount(0);

        for (Movimento movimento : movimentos) {
            Object[] linha = {
                    movimento.getIdMovimento(),
                    movimento.getIdProduto(),
                    movimento.getPreco(),
                    movimento.getQuantidade(),
                    movimento.isEEntrada()
            };
            modeloTabelaMovimento.addRow(linha);
        }
    }

    private Movimento getMovimentoDaLinha(int linha) {
        int idMovimento = (int) modeloTabelaMovimento.getValueAt(linha, 0);
        int idProduto = (int) modeloTabelaMovimento.getValueAt(linha, 1);
        double preco = (double) modeloTabelaMovimento.getValueAt(linha, 2);
        int quantidade = (int) modeloTabelaMovimento.getValueAt(linha, 3);
        boolean eEntrada = (boolean) modeloTabelaMovimento.getValueAt(linha, 4);

        return new Movimento(idMovimento, idProduto, preco, quantidade, eEntrada);
    }

    private void abrirDialogoMovimento(Movimento movimento) {
        JTextField campoIdProduto = new JTextField();
        JTextField campoPreco = new JTextField();
        JTextField campoQuantidade = new JTextField();
        JCheckBox checkEEntrada = new JCheckBox("É Entrada?");

        if (movimento != null) {
            campoIdProduto.setText(String.valueOf(movimento.getIdProduto()));
            campoPreco.setText(String.valueOf(movimento.getPreco()));
            campoQuantidade.setText(String.valueOf(movimento.getQuantidade()));
            checkEEntrada.setSelected(movimento.isEEntrada());
        }

        Object[] campos = {
                "ID do Produto:", campoIdProduto,
                "Preço:", campoPreco,
                "Quantidade:", campoQuantidade,
                "É Entrada:", checkEEntrada
        };

        int opcao = JOptionPane.showConfirmDialog(this, campos, "Adicionar Movimento",
                JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {

                String idProdutoTexto = campoIdProduto.getText();
                String precoTexto = campoPreco.getText();
                String quantidadeTexto = campoQuantidade.getText();
                boolean eEntrada = checkEEntrada.isSelected();

                if (precoTexto.isEmpty() || quantidadeTexto.isEmpty() || idProdutoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
                    return;
                }

                int idProduto = Integer.parseInt(idProdutoTexto);
                double preco = Double.parseDouble(precoTexto);
                int quantidade = Integer.parseInt(quantidadeTexto);

                Movimento novoMovimento = new Movimento(idProduto, preco, quantidade, eEntrada);
                if (dao.adicionarMovimento(novoMovimento)) {
                    JOptionPane.showMessageDialog(this, "Movimento adicionado com sucesso.");
                    atualizarTabelaMovimento();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar movimento.");
                }

                 try {
                    idProduto = Integer.parseInt(campoIdProduto.getText());
                    quantidade = Integer.parseInt(campoQuantidade.getText());
                    eEntrada = checkEEntrada.isSelected();
                    ProdutoDAO produtoDAO = new ProdutoDAO();

                    Produto produto = produtoDAO.buscarProdutoPorId(idProduto);
                    if (produto == null) {
                        JOptionPane.showMessageDialog(this, "Produto com ID " + idProduto + " não encontrado.");
                        return;
                    }

                     aplicarMovimento(produto, quantidade, eEntrada, preco);
                    JOptionPane.showMessageDialog(this, "Movimento aplicado com sucesso.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID e Quantidade devem ser números.");
                }
                ; 

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar/editar produto.");
            }
        }
    }

     private void aplicarMovimento(Produto produto, int quantidade, boolean eEntrada, double preco) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        if (eEntrada) {
            produto.setSaldo(produto.getSaldo() + quantidade);
            produto.setPreco(String.valueOf(preco));
        } else {
            produto.setSaldo(produto.getSaldo() - quantidade);
            produto.setPreco(String.valueOf(preco));
        }

        produtoDAO.editarProduto(produto);
    } 
   
}
