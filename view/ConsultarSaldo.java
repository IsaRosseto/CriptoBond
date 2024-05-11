package view;

import javax.swing.*;
import java.awt.*;

public class ConsultarSaldo extends JFrame {
    private JLabel lblSaldo;
    private JButton btnVoltar;

    public ConsultarSaldo() {
        setTitle("Consultar Saldo");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        lblSaldo = new JLabel("Saldo Atual: $1,000.00", JLabel.CENTER);
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 24));

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> this.dispose());

        add(lblSaldo, BorderLayout.CENTER);
        add(btnVoltar, BorderLayout.SOUTH);
    }
}
