package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private JButton registerUserButton;
    private JButton deleteUserButton;
    private JButton registerCarteiraButton;
    private JButton deleteCarteiraButton;
    private JButton checkBalanceButton;
    private JButton checkExtratoButton;
    private JButton updateCotacoesButton;
    private JLabel titleLabel;

    public MainMenuView() {
        setTitle("Menu Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        titleLabel = new JLabel("Menu Principal");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 204, 0));
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension buttonSize = new Dimension(250, 50);

        registerUserButton = createButton("Cadastrar Novo Usuário", buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(registerUserButton, gbc);

        deleteUserButton = createButton("Excluir Usuário", buttonSize);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(deleteUserButton, gbc);

        registerCarteiraButton = createButton("Cadastrar Nova Criptomoeda", buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(registerCarteiraButton, gbc);

        deleteCarteiraButton = createButton("Excluir Criptomoeda", buttonSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(deleteCarteiraButton, gbc);

        checkBalanceButton = createButton("Consultar Saldo", buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(checkBalanceButton, gbc);
        
        checkExtratoButton = createButton("Consultar Extrato", buttonSize);
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(checkExtratoButton, gbc);

        updateCotacoesButton = createButton("Atualizar Cotações", buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across 2 columns
        centerPanel.add(updateCotacoesButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        return button;
    }

    public void addRegisterUserListener(ActionListener listener) {
        registerUserButton.addActionListener(listener);
    }

    public void addDeleteUserListener(ActionListener listener) {
        deleteUserButton.addActionListener(listener);
    }

    public void addRegisterCarteiraListener(ActionListener listener) {
        registerCarteiraButton.addActionListener(listener);
    }

    public void addDeleteCarteiraListener(ActionListener listener) {
        deleteCarteiraButton.addActionListener(listener);
    }

    public void addCheckBalanceListener(ActionListener listener) {
        checkBalanceButton.addActionListener(listener);
    }
    
    public void addExtratoListener(ActionListener listener) {
        checkExtratoButton.addActionListener(listener);
    }

    public void addUpdateCotacoesListener(ActionListener listener) {
        updateCotacoesButton.addActionListener(listener);
    }
}
