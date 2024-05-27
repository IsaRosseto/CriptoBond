package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DeleteCarteiraView extends JFrame {
    private JTextField nameField;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nameLabel;

    public DeleteCarteiraView() {
        setTitle("Excluir Criptomoeda");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        titleLabel = new JLabel("Excluir Criptomoeda");
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

        nameLabel = new JLabel("Nome da Criptomoeda:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nameLabel, gbc);

        nameField = new JTextField(20); // Aumentado para 20 colunas
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns for more space
        centerPanel.add(nameField, gbc);

        deleteButton = new JButton("Excluir");
        deleteButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset span to 1 for button
        centerPanel.add(deleteButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    public String getName() {
        return nameField.getText();
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
