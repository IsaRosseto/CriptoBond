package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterCarteiraView extends JFrame {
    private JTextField nomeField;
    private JTextField cotacaoField;
    private JTextField taxaCField;
    private JTextField taxaVField;
    private JButton registerButton;
    private JLabel titleLabel;

    public RegisterCarteiraView() {
        setTitle("Registrar Nova Criptomoeda");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        titleLabel = new JLabel("Registrar Nova Criptomoeda");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 204, 0));
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // Painel central para os campos de entrada e botão
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nomeLabel, gbc);

        nomeField = new JTextField(20);
        nomeField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(nomeField, gbc);

        JLabel cotacaoLabel = new JLabel("Cotação:");
        cotacaoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        cotacaoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(cotacaoLabel, gbc);

        cotacaoField = new JTextField(20);
        cotacaoField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(cotacaoField, gbc);

        JLabel taxaCLabel = new JLabel("Taxa de Compra:");
        taxaCLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        taxaCLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(taxaCLabel, gbc);

        taxaCField = new JTextField(20);
        taxaCField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(taxaCField, gbc);

        JLabel taxaVLabel = new JLabel("Taxa de Venda:");
        taxaVLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        taxaVLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(taxaVLabel, gbc);

        taxaVField = new JTextField(20);
        taxaVField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        centerPanel.add(taxaVField, gbc);

        registerButton = new JButton("Cadastrar");
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        gbc.gridx = 1;
        gbc.gridy = 4;
        centerPanel.add(registerButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    public String getNome() {
        return nomeField.getText();
    }

    public double getCotacao() {
        return Double.parseDouble(cotacaoField.getText());
    }

    public double getTaxaC() {
        return Double.parseDouble(taxaCField.getText());
    }

    public double getTaxaV() {
        return Double.parseDouble(taxaVField.getText());
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}
