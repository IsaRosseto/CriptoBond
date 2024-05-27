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
import java.util.Locale;

public class TelaSaque extends JFrame {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private JLabel saldoLabel;
    private JTextField amountField;
    private Timer timer;

    public TelaSaque(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        initialize();
        startBalanceUpdater();
    }

    private void initialize() {
        setTitle("Saque");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Saque", SwingConstants.CENTER);
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

        saldoLabel = new JLabel();
        saldoLabel.setForeground(Color.WHITE);
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saldoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        centerPanel.add(saldoLabel);
        atualizarSaldo();

        JLabel amountLabel = new JLabel("Digite o valor do saque:");
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(amountLabel);

        amountField = new JTextField(15);
        amountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(amountField);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento vertical

        JButton withdrawButton = new JButton("Sacar");
        withdrawButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        withdrawButton.setBackground(Color.BLACK);
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFocusPainted(false);
        withdrawButton.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(withdrawButton);

        // Adicionar ação para o botão de saque
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = amountField.getText().trim();
                if (!amountStr.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        if (amount > 0) {
                            if (usuario.getSaldo() >= amount) {
                                usuario.setSaldo(usuario.getSaldo() - amount);
                                usuarioDAO.atualizarUsuario(usuario);
                                registrarTransacao("Saque", amount, 0, "");

                                JOptionPane.showMessageDialog(TelaSaque.this, "Saque realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                atualizarSaldo();
                                dispose();
                            } else {
                                showError("Saldo insuficiente para realizar o saque.");
                            }
                        } else {
                            showError("O valor do saque deve ser positivo.");
                        }
                    } catch (NumberFormatException ex) {
                        showError("Valor inválido. Por favor, digite um valor numérico.");
                    }
                }
            }
        });
    }

    private void startBalanceUpdater() {
        int delay = 5000; // Atualizar a cada 5 segundos
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario atualizado = usuarioDAO.getUsuarioByCpf(usuario.getCpf());
                if (atualizado != null) {
                    usuario.setSaldo(atualizado.getSaldo());
                    atualizarSaldo();
                }
            }
        });
        timer.start();
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

    private void atualizarSaldo() {
        saldoLabel.setText(String.format(Locale.getDefault(), "Saldo: R$ %.2f", usuario.getSaldo()));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
