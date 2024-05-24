package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckBalanceView extends JFrame {
    private JTextField cpfField;
    private JButton checkButton;

    public CheckBalanceView() {
        setTitle("Consultar Saldo");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("CPF do Usuário:"));
        cpfField = new JTextField(15);
        panel.add(cpfField);

        checkButton = new JButton("Consultar");
        panel.add(checkButton);
    }

    public String getCpf() {
        return cpfField.getText();
    }

    public void addCheckListener(ActionListener listener) {
        checkButton.addActionListener(listener);
    }
}
