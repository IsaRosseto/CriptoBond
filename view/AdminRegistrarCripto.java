package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminRegistrarCripto extends JFrame {
    private JTextField nameField;
    private JTextField quotationField;
    private JButton registerButton;
    private JTextField taxaCField;
    private JTextField taxaVField;
    

    public AdminRegistrarCripto() {
        setTitle("Registrar Nova Criptomoeda");
        setSize(300, 300);
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

        JLabel quotationLabel = new JLabel("Cotação:");
        quotationLabel.setForeground(new Color(255, 204, 0));
        quotationLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(quotationLabel);

        quotationField = new JTextField(15);
        quotationField.setBackground(Color.BLACK);
        quotationField.setForeground(Color.WHITE);
        quotationField.setCaretColor(Color.WHITE);
        quotationField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        quotationField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 1));
        panel.add(quotationField);

        
        
        JLabel taxaCLabel = new JLabel("Taxa de Compra:");
        taxaCLabel.setForeground(new Color(255, 204, 0));
        taxaCLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(taxaCLabel);
        
        taxaCField = new JTextField(15);
        taxaCField.setBackground(Color.BLACK);
        taxaCField.setForeground(Color.WHITE);
        taxaCField.setCaretColor(Color.WHITE);
        taxaCField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taxaCField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 1));
        panel.add(taxaCField);
        
        
        JLabel taxaVLabel = new JLabel("Taxa de Venda:");
        taxaVLabel.setForeground(new Color(255, 204, 0));
        taxaVLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(taxaVLabel);
        
        taxaVField = new JTextField(15);
        taxaVField.setBackground(Color.BLACK);
        taxaVField.setForeground(Color.WHITE);
        taxaVField.setCaretColor(Color.WHITE);
        taxaVField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taxaVField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 1));
        panel.add(taxaVField);
        
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

    public double getQuotation() {
        return Double.parseDouble(quotationField.getText());
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}
