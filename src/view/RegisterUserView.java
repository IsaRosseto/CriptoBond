package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterUserView extends JFrame {
    private JTextField nameField;
    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel titleLabel;

    public RegisterUserView() {
        setTitle("Registrar Novo Usuário");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        titleLabel = new JLabel("Registrar Novo Usuário");
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

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nameLabel, gbc);

        nameField = new JTextField(30);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(nameField, gbc);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        cpfLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(cpfLabel, gbc);

        cpfField = new JTextField(30);
        cpfField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(cpfField, gbc);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(30);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(passwordField, gbc);

        registerButton = new JButton("Cadastrar");
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        gbc.gridx = 1;
        gbc.gridy = 3;
        centerPanel.add(registerButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = getCpf().trim();
                String password = getPassword().trim();

                // Verificar se o CPF tem 11 dígitos e contém apenas números
                if (!cpf.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(RegisterUserView.this, "CPF deve conter 11 dígitos numéricos, sem pontos ou traços.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar se a senha contém exatamente 6 dígitos numéricos
                if (!password.matches("\\d{6}")) {
                    JOptionPane.showMessageDialog(RegisterUserView.this, "A senha deve conter exatamente 6 dígitos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Se as validações passarem, invocar o listener de registro
                listener.actionPerformed(e);
            }
        });
    }
}
