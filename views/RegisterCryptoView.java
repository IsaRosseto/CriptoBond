package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterCryptoView extends JFrame {
    private JTextField nameField;
    private JTextField quotationField;
    private JButton registerButton;

    public RegisterCryptoView() {
        setTitle("Registrar Nova Criptomoeda");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Nome:"));
        nameField = new JTextField(15);
        panel.add(nameField);

        panel.add(new JLabel("Cotação:"));
        quotationField = new JTextField(15);
        panel.add(quotationField);

        registerButton = new JButton("Cadastrar");
        panel.add(registerButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public double getQuotation() {
        return Double.parseDouble(quotationField.getText());
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}
