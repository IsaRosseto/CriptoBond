package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDeletarCriptoView extends JFrame {
    private JTextField nameField;
    private JButton deleteButton;

    public AdminDeletarCriptoView() {
        setTitle("Excluir Criptomoeda");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel nameLabel = new JLabel("Nome da Criptomoeda:");
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

        deleteButton = new JButton("Excluir");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        panel.add(deleteButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
