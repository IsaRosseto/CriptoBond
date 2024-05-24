package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DeleteCryptoView extends JFrame {
    private JTextField nameField;
    private JButton deleteButton;

    public DeleteCryptoView() {
        setTitle("Excluir Criptomoeda");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Nome da Criptomoeda:"));
        nameField = new JTextField(15);
        panel.add(nameField);

        deleteButton = new JButton("Excluir");
        panel.add(deleteButton);
    }

    public String getName() {
        return nameField.getText();
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
