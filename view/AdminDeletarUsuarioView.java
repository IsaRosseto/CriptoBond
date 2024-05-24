package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDeletarUsuarioView extends JFrame {
    private JTextField cpfField;
    private JButton deleteButton;

    public AdminDeletarUsuarioView() {
        setTitle("Excluir Usuário");
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

        deleteButton = new JButton("Excluir");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        panel.add(deleteButton);
    }

    public String getCpf() {
        return cpfField.getText();
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
