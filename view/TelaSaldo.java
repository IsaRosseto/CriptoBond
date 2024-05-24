package view;

import DAO.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class TelaSaldo extends JFrame {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    public TelaSaldo(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        initialize();
    }

    private void initialize() {
        setTitle("Saldo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Saldo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 204, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Painel central para campos de entrada
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(centerPanel, BorderLayout.CENTER);

        JLabel senhaLabel = new JLabel("Digite sua senha:");
        senhaLabel.setForeground(Color.WHITE);
        senhaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(senhaLabel);

        JPasswordField senhaField = new JPasswordField(15);
        senhaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(senhaField);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento vertical

        JButton checkBalanceButton = new JButton("Verificar Saldo");
        checkBalanceButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkBalanceButton.setBackground(Color.BLACK);
        checkBalanceButton.setForeground(Color.WHITE);
        checkBalanceButton.setFocusPainted(false);
        checkBalanceButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        checkBalanceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(checkBalanceButton);

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senha = new String(senhaField.getPassword());
                if (usuarioDAO.verificarSenha(usuario.getCpf(), senha)) {
                    showSaldo();
                } else {
                    showError("Senha incorreta. Por favor, tente novamente.");
                }
            }
        });
    }

    private void showSaldo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(usuario.getNome()).append("\n");
        sb.append("CPF: ").append(usuario.getCpf()).append("\n");
        sb.append("Saldo: ").append(formatCurrency(usuario.getSaldo())).append("\n\n");

        for (Map.Entry<String, Double> entry : usuario.getCriptomoedas().entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Saldo", JOptionPane.INFORMATION_MESSAGE);
    }

    private String formatCurrency(double value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormat.format(value);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
