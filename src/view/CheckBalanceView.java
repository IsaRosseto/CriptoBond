package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CheckBalanceView extends JFrame {
    private JTextField cpfField;
    private JButton checkButton;
    private JLabel titleLabel;
    private JLabel cpfLabel;

    public CheckBalanceView() {
        setTitle("Consultar Saldo");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        titleLabel = new JLabel("Consultar Saldo");
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

        cpfLabel = new JLabel("CPF do Usuário:");
        cpfLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        cpfLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(cpfLabel, gbc);

        cpfField = new JTextField(20); // Aumentado para 20 colunas
        cpfField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns for more space
        centerPanel.add(cpfField, gbc);

        checkButton = new JButton("Consultar");
        checkButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        checkButton.setBackground(Color.BLACK);
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);
        checkButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset span to 1 for button
        centerPanel.add(checkButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    public String getCpf() {
        return cpfField.getText();
    }

    public void addCheckListener(ActionListener listener) {
        checkButton.addActionListener(listener);
    }
}
