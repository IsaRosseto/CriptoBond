
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuUsuario extends JFrame {
    private JButton btnConsultarSaldo, btnConsultarExtrato, btnDepositar, btnSacar, btnComprarCrypto, btnVenderCrypto, btnAtualizarCotacao, btnSair;

    public MenuUsuario() {
        // Configuração inicial do JFrame
        setTitle("Menu da Exchange de Criptomoedas");
        setLayout(new GridLayout(8, 1)); // 8 opções, 1 coluna
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centraliza a janela

        // Criação e adição dos botões
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        btnConsultarSaldo = createButton("Consultar Saldo");
        btnConsultarExtrato = createButton("Consultar Extrato");
        btnDepositar = createButton("Depositar");
        btnSacar = createButton("Sacar");
        btnComprarCrypto = createButton("Comprar Criptomoedas");
        btnVenderCrypto = createButton("Vender Criptomoedas");
        btnAtualizarCotacao = createButton("Atualizar Cotação");
        btnSair = createButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));  // Ação para fechar a aplicação
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this::buttonAction);
        add(button);
        return button;
    }
    
    

    private void buttonAction(ActionEvent e) {
    JButton source = (JButton) e.getSource();
    if (source.getText().equals("Consultar Saldo")) {
        new ConsultarSaldo();
    } else {
        JOptionPane.showMessageDialog(this, "Ação realizada: " + source.getText());
    }
}

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuUsuario::new);
    }
}

    

