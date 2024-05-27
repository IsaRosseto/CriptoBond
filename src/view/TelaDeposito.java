package view;

import DAO.UsuarioDAO;
import model.Usuario;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;

public class TelaDeposito extends JFrame {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    public TelaDeposito(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        initialize();
    }

    private void initialize() {
        setTitle("Depósito");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Depósito", SwingConstants.CENTER);
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

        JLabel amountLabel = new JLabel("Digite o valor do depósito:");
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(amountLabel);

        JTextField amountField = new JTextField(15);
        amountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(amountField);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento vertical

        JButton depositButton = new JButton("Depositar");
        depositButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        depositButton.setBackground(Color.BLACK);
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(depositButton);

        // Adicionar ação para o botão de depósito
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = amountField.getText().trim();
                if (!amountStr.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        if (amount > 0) {
                            usuario.setSaldo(usuario.getSaldo() + amount);
                            usuarioDAO.atualizarUsuario(usuario);
                            registrarTransacao("Depósito", amount, 0, "");

                            JOptionPane.showMessageDialog(TelaDeposito.this, "Depósito realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        } else {
                            showError("O valor do depósito deve ser positivo.");
                        }
                    } catch (NumberFormatException ex) {
                        showError("Valor inválido. Por favor, digite um valor numérico.");
                    }
                }
            }
        });
    }

    private void registrarTransacao(String tipo, double valor, double taxa, String cripto) {
        Timestamp data = new Timestamp(new Date().getTime());

        Transacao transacao = new Transacao(
                0, // ID será gerado automaticamente pelo banco de dados
                usuario.getCpf(),
                data,
                tipo,
                cripto,
                valor,
                taxa
        );

        usuarioDAO.registrarTransacao(transacao);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
