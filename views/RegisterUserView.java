package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterUserView extends JFrame {
    private JTextField nameField;
    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterUserView() {
        setTitle("Registrar Novo Usuário");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Nome:"));
        nameField = new JTextField(15);
        panel.add(nameField);

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField(15);
        panel.add(cpfField);

        panel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField(15);
        panel.add(passwordField);

        registerButton = new JButton("Cadastrar");
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
