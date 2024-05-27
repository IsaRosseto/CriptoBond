package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaBoasVindas extends JFrame {

    public TelaBoasVindas() {
        initialize();
    }

    private void initialize() {
        setTitle("Bem-vindo ao CriptoBond");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        JLabel welcomeLabel = new JLabel("Bem-vindo ao CriptoBond, sua Exchange de Criptomoedas", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(255, 204, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout());

        JButton investorButton = new JButton("Login como Investidor");
        investorButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        investorButton.setBackground(Color.BLACK);
        investorButton.setForeground(Color.WHITE);
        investorButton.setFocusPainted(false);
        investorButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        investorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });

        JButton adminButton = new JButton("Login como Administrador");
        adminButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        adminButton.setBackground(Color.BLACK);
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        adminButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLogin().setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(investorButton);
        buttonPanel.add(adminButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
