package com.bd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaProdutoUsuario extends JFrame {

    private JTable tabelaProduto;
    private DefaultTableModel modeloTabelaProduto;
    private ProdutoDAO dao;

    public TelaProdutoUsuario() {

        setTitle("Lista de Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dao = new ProdutoDAO();

        String[] colunasProduto = { "ID_Produto", "Nome", "Preço" };
        modeloTabelaProduto = new DefaultTableModel(null, colunasProduto) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProduto = new JTable(modeloTabelaProduto);
        JScrollPane scroll1 = new JScrollPane(tabelaProduto);

        setLayout(new BorderLayout(10, 10));
        add(scroll1, BorderLayout.CENTER);

        atualizarTabelaProduto();
    }

    private void atualizarTabelaProduto() {
        List<Produto> produtos = dao.listarProduto();
        modeloTabelaProduto.setRowCount(0);

        for (Produto produto : produtos) {
            Object[] linha = {
                    produto.getIdProduto(),
                    produto.getNomeProduto(),
                    produto.getPreco()
            };
            modeloTabelaProduto.addRow(linha);
        }
        
      setVisible(true);
    }
}