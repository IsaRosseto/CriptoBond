package view;

import DAO.UsuarioDAO;
import model.Usuario;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaMenu extends JFrame {

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private JLabel balanceLabel;
    private Timer timer;

    public TelaMenu(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        initialize();
        startBalanceUpdater();
    }

    private void initialize() {
        setTitle("CriptoBond - Menu");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        // Painel superior para a logo e informações da conta
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("CriptoBond");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 204, 0));
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.BLACK);
        logoPanel.add(titleLabel);

        JPanel accountPanel = new JPanel();
        accountPanel.setBackground(Color.BLACK);
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        JLabel accountLabel = new JLabel("Conta: " + usuario.getNome());
        balanceLabel = new JLabel("Saldo: " + formatCurrency(usuario.getSaldo()));

        Font accountFont = new Font("SansSerif", Font.BOLD, 20);
        Font balanceFont = new Font("SansSerif", Font.PLAIN, 18);
        Color textColor = new Color(255, 204, 0);

        accountLabel.setFont(accountFont);
        accountLabel.setForeground(textColor);
        balanceLabel.setFont(balanceFont);
        balanceLabel.setForeground(textColor);

        accountPanel.add(accountLabel);
        accountPanel.add(balanceLabel);

        topPanel.add(logoPanel, BorderLayout.WEST);
        topPanel.add(accountPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        JButton depositButton = createButton("Depósito");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaDeposito(usuario).setVisible(true);
            }
        });

        JButton statementButton = createButton("Extrato");
        statementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaExtrato(usuario).setVisible(true);
            }
        });

        JButton withdrawalButton = createButton("Saque");
        withdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaSaque(usuario).setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(depositButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(statementButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        centerPanel.add(withdrawalButton, gbc);

        JButton cryptoButton = createButton("CRIPTOMOEDAS");
        cryptoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaTransacoes(usuario).setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 0, 20, 0);
        centerPanel.add(cryptoButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 24));
        button.setPreferredSize(new Dimension(200, 100));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        return button;
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

    private void atualizarSaldo() {
        balanceLabel.setText("Saldo: " + formatCurrency(usuario.getSaldo()));
    }

    private String formatCurrency(double value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormat.format(value);
    }
}
