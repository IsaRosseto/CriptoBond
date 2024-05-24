package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminRegistrarUsuario extends JFrame {
    private JTextField nameField;
    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public AdminRegistrarUsuario() {
        setTitle("Registrar Novo Usuário");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setForeground(new Color(255, 204, 0));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(nameLabel);

        nameField = new JTextField(15);
        nameField.setBackground(Color.BLACK);
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 1));
        panel.add(nameField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setForeground(new Color(255, 204, 0));
        cpfLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(cpfLabel);

        cpfField = new JTextField(15);
        cpfField.setBackground(Color.BLACK);
        cpfField.setForeground(Color.WHITE);
        cpfField.setCaretColor(Color.WHITE);
        cpfField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cpfField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 1));
        panel.add(cpfField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setForeground(new Color(255, 204, 0));
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 1));
        panel.add(passwordField);

        registerButton = new JButton("Cadastrar");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        panel.add(registerButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public String getCpf() {
        return cpfField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}
