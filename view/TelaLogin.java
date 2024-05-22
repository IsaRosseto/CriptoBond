package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import DAO.Database;
import DAO.UsuarioDAO;
import model.Usuario;

public class TelaLogin extends JFrame {

    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public TelaLogin() {
        initialize();
    }

    private void initialize() {
        setTitle("Login - CriptoBond");
        setSize(400, 400); // Ajustado para garantir que todos os elementos sejam visíveis
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Add border around the main panel
        getContentPane().setBackground(Color.BLACK); // Fundo da janela principal

        // Painel superior para a logo
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add border around the top panel

        JLabel titleLabel = new JLabel("CriptoBond");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 204, 0)); // Amarelo
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // Painel central para os campos de entrada
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the center panel

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        cpfLabel.setForeground(new Color(255, 204, 0)); // Amarelo
        centerPanel.add(cpfLabel);

        cpfField = new JTextField(15);
        styleTextField(cpfField);
        centerPanel.add(cpfField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        passwordLabel.setForeground(new Color(255, 204, 0)); // Amarelo
        centerPanel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        styleTextField(passwordField);
        centerPanel.add(passwordField);

        add(centerPanel, BorderLayout.CENTER);

        // Painel inferior para o botão de login
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add border around the bottom panel

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginButton.setPreferredSize(new Dimension(200, 50)); // Define a fixed size for the button
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        bottomPanel.add(loginButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        textField.setMargin(new Insets(5, 10, 5, 10)); // Add padding inside the text field
    }

    private void handleLogin() {
        String cpf = cpfField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = Database.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            Usuario usuario = usuarioDAO.authenticate(cpf, password);

            if (usuario != null) {
                // Fechar a tela de login e abrir a tela principal
                dispose();
                new CriptoBondApp(cpf).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "CPF ou senha incorretos", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
