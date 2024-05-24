package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DeleteUserView extends JFrame {
    private JTextField cpfField;
    private JButton deleteButton;

    public DeleteUserView() {
        setTitle("Excluir Usuário");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("CPF do Usuário:"));
        cpfField = new JTextField(15);
        panel.add(cpfField);

        deleteButton = new JButton("Excluir");
        panel.add(deleteButton);
    }

    public String getCpf() {
        return cpfField.getText();
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
