package view;

import DAO.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private UsuarioDAO usuarioDAO;

    public TelaLogin() {
        this.usuarioDAO = new UsuarioDAO();
        initialize();
    }

    private void initialize() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para a logo e título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("CriptoBond - Login", SwingConstants.CENTER);
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

        // Adicionar ação para o botão de login
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String cpf = cpfField.getText().trim();
        String senha = new String(passwordField.getPassword());

        if (!isValidCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Certifique-se de que está sem pontos e traços.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPassword(senha)) {
            JOptionPane.showMessageDialog(this, "Senha inválida. A senha deve conter 6 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioDAO.getUsuarioByCpfESenha(cpf, senha);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            // Abrir a tela principal ou redirecionar para outra funcionalidade
            TelaMenu telaMenu = new TelaMenu(usuario);
            telaMenu.setVisible(true);
            dispose(); // Fecha a tela de login
        } else {
            JOptionPane.showMessageDialog(this, "CPF ou senha incorretos. Por favor, tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{11}"); // Verifica se o CPF contém exatamente 11 dígitos
    }

    private boolean isValidPassword(String senha) {
        return senha.matches("\\d{6}"); // Verifica se a senha contém exatamente 6 dígitos numéricos
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        });
    }
}
