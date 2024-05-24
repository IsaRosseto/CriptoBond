package view;

import DAO.UsuarioDAO;
import model.Usuario;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class TelaTransacoes extends JFrame {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private JComboBox<String> cryptoComboBox;
    private JTextField amountField;

    public TelaTransacoes(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        initialize();
    }

    private void initialize() {
        setTitle("Transações de Criptomoedas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Transações de Criptomoedas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 204, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Painel central para campos de entrada
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(centerPanel, BorderLayout.CENTER);

        JLabel cryptoLabel = new JLabel("Selecione a criptomoeda:");
        cryptoLabel.setForeground(Color.WHITE);
        cryptoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(cryptoLabel);

        cryptoComboBox = new JComboBox<>(usuarioDAO.getCryptoColumns().toArray(new String[0]));
        cryptoComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(cryptoComboBox);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento vertical

        JLabel amountLabel = new JLabel("Digite o valor:");
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(amountLabel);

        amountField = new JTextField(15);
        amountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(amountField);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento vertical

        JButton buyButton = new JButton("Comprar");
        buyButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        buyButton.setBackground(Color.BLACK);
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(buyButton);

        JButton sellButton = new JButton("Vender");
        sellButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sellButton.setBackground(Color.BLACK);
        sellButton.setForeground(Color.WHITE);
        sellButton.setFocusPainted(false);
        sellButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(sellButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTransaction(true);
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTransaction(false);
            }
        });
    }

    private void handleTransaction(boolean isBuy) {
        String cryptoName = (String) cryptoComboBox.getSelectedItem();
        String amountStr = amountField.getText().trim();
        if (!amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                double cryptoPrice = usuarioDAO.getPrecosAtuaisCriptomoedas().get(cryptoName);
                double cryptoAmount = amount / cryptoPrice;

                if (isBuy) {
                    if (usuario.getSaldo() >= amount) {
                        usuario.setSaldo(usuario.getSaldo() - amount);
                        usuario.getCriptomoedas().put(cryptoName, usuario.getCriptomoedas().getOrDefault(cryptoName, 0.0) + cryptoAmount);
                        usuarioDAO.atualizarSaldo(usuario.getCpf(), usuario.getSaldo());
                        usuarioDAO.atualizarCriptomoedas(usuario.getCpf(), usuario.getCriptomoedas());
                        adicionarTransacao("Compra", amount, 0);
                    } else {
                        showError("Saldo insuficiente.");
                    }
                } else {
                    double cryptoBalance = usuario.getCriptomoedas().getOrDefault(cryptoName, 0.0);
                    if (cryptoBalance >= cryptoAmount) {
                        usuario.setSaldo(usuario.getSaldo() + amount);
                        usuario.getCriptomoedas().put(cryptoName, cryptoBalance - cryptoAmount);
                        usuarioDAO.atualizarSaldo(usuario.getCpf(), usuario.getSaldo());
                        usuarioDAO.atualizarCriptomoedas(usuario.getCpf(), usuario.getCriptomoedas());
                        adicionarTransacao("Venda", amount, 0);
                    } else {
                        showError("Quantidade insuficiente de criptomoeda.");
                    }
                }
            } catch (NumberFormatException ex) {
                showError("Valor inválido. Por favor, digite um valor numérico.");
            }
        }
    }

    private void adicionarTransacao(String tipo, double valor, double taxa) {
        Timestamp data = new Timestamp(new Date().getTime());

        Map<String, Double> criptomoedas = usuario.getCriptomoedas();
        double saldoBitcoin = criptomoedas.getOrDefault("bitcoin", 0.0);
        double saldoEthereum = criptomoedas.getOrDefault("ethereum", 0.0);
        double saldoRipple = criptomoedas.getOrDefault("ripple", 0.0);

        Transacao transacao = new Transacao(
                usuario.getCpf(),
                data,
                tipo,
                valor,
                taxa,
                usuario.getSaldo(),
                saldoBitcoin,
                saldoEthereum,
                saldoRipple
        );

        usuarioDAO.adicionarTransacao(transacao);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
