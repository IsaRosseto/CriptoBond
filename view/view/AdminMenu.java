package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {
    private JButton AdminRegistrarUsuarioButton;
    private JButton AdminDeletarUsuarioButton;
    private JButton AdminRegistrarCriptoButton;
    private JButton AdminDeletarCriptoButton;
    private JButton AdminVerificarSaldoButton;
    private JButton AdminAtualizarCotasButton;

    public AdminMenu() {
        initialize();
    }

    private void initialize() {
        setTitle("Menu Principal - Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para a logo e título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("CriptoBond - Administrador", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 204, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(centerPanel, BorderLayout.CENTER);

        AdminRegistrarUsuarioButton = createButton("Cadastrar Novo Usuário");
        centerPanel.add(AdminRegistrarUsuarioButton);

        AdminDeletarUsuarioButton = createButton("Excluir Usuário");
        centerPanel.add(AdminDeletarUsuarioButton);

        AdminRegistrarCriptoButton = createButton("Cadastrar Nova Criptomoeda");
        centerPanel.add(AdminRegistrarCriptoButton);

        AdminDeletarCriptoButton = createButton("Excluir Criptomoeda");
        centerPanel.add(AdminDeletarCriptoButton);

        AdminVerificarSaldoButton = createButton("Consultar Saldo");
        centerPanel.add(AdminVerificarSaldoButton);

        AdminAtualizarCotasButton = createButton("Atualizar Cotações");
        centerPanel.add(AdminAtualizarCotasButton);

        // Adicionando ActionListeners aos botões para abrir as janelas correspondentes
        AdminRegistrarUsuarioButton.addActionListener(e -> new AdminRegistrarUsuario().setVisible(true));
        AdminDeletarUsuarioButton.addActionListener(e -> new AdminDeletarUsuarioView().setVisible(true));
        AdminRegistrarCriptoButton.addActionListener(e -> new AdminRegistrarCripto().setVisible(true));
        AdminDeletarCriptoButton.addActionListener(e -> new AdminDeletarCriptoView().setVisible(true));
        AdminVerificarSaldoButton.addActionListener(e -> new AdminVerificarSaldoView().setVisible(true));
        AdminAtualizarCotasButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Atualizar Cotações clicado! (Implementar classe correspondente)");
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMenu().setVisible(true));
    }
}
