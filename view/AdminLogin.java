package view;

import DAO.AdministradorDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame {
    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AdministradorDAO administradorDAO;

    public AdminLogin() {
        this.administradorDAO = new AdministradorDAO();
        initialize();
    }

    private void initialize() {
        setTitle("Login - Administrador");
        setSize(400, 300);
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

        // Painel central para campos de entrada
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(centerPanel, BorderLayout.CENTER);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setForeground(Color.WHITE);
        centerPanel.add(cpfLabel);

        cpfField = new JTextField(15);
        centerPanel.add(cpfField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setForeground(Color.WHITE);
        centerPanel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        centerPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        centerPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAdminLogin();
            }
        });
    }

    private void handleAdminLogin() {
        String cpf = cpfField.getText();
        String senha = new String(passwordField.getPassword());

        if (administradorDAO.verificarCredenciais(cpf, senha)) {
            JOptionPane.showMessageDialog(this, "Login de administrador bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new AdminMenu().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "CPF ou Senha incorretos. Por favor, tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
