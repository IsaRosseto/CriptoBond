package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminVerificarSaldoView extends JFrame {
    private JTextField cpfField;
    private JButton checkButton;

    public AdminVerificarSaldoView() {
        setTitle("Consultar Saldo");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel cpfLabel = new JLabel("CPF do Usuário:");
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

        checkButton = new JButton("Consultar");
        checkButton.setBackground(Color.BLACK);
        checkButton.setForeground(Color.WHITE);
        checkButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        checkButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        panel.add(checkButton);
    }

    public String getCpf() {
        return cpfField.getText();
    }

    public void addCheckListener(ActionListener listener) {
        checkButton.addActionListener(listener);
    }
}
