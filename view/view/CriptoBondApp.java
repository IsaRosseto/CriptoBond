package view;

import javax.swing.*;
import java.awt.*;
import DAO.UsuarioDAO;
import model.Usuario;

public class CriptoBondApp extends JFrame {

    private JLabel accountLabel;
    private JLabel balanceLabel;
    private String cpf;

    public CriptoBondApp(String cpf) {
        this.cpf = cpf;
        initialize();
        loadAccountInfo(cpf);
    }

    private void initialize() {
        setTitle("CriptoBond");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Add border around the main panel
        getContentPane().setBackground(Color.BLACK); // Fundo da janela principal

        // Painel superior para a logo e informações da conta
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add border around the top panel

        // Logo
        JLabel titleLabel = new JLabel("CriptoBond");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 204, 0)); // Amarelo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.BLACK);
        logoPanel.add(titleLabel);

        // Informações da conta
        JPanel accountPanel = new JPanel();
        accountPanel.setBackground(Color.BLACK);
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountLabel = new JLabel("Conta: Carregando...");
        balanceLabel = new JLabel("Saldo: Carregando...");

        Font accountFont = new Font("SansSerif", Font.BOLD, 20);
        Font balanceFont = new Font("SansSerif", Font.PLAIN, 18);
        Color textColor = new Color(255, 204, 0); // Amarelo

        accountLabel.setFont(accountFont);
        accountLabel.setForeground(textColor);
        balanceLabel.setFont(balanceFont);
        balanceLabel.setForeground(textColor);

        accountPanel.add(accountLabel);
        accountPanel.add(balanceLabel);

        // Adicionar logo e informações da conta ao painel superior
        topPanel.add(logoPanel, BorderLayout.WEST);
        topPanel.add(accountPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Painel de cotações
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PainelPrecosCripto pricesPanel = new PainelPrecosCripto(usuarioDAO.getCryptoColumns());
        add(pricesPanel, BorderLayout.CENTER);

        // Painel inferior para os botões
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add border around the bottom panel
        GridBagConstraints gbc = new GridBagConstraints();

        JButton depositButton = createButton("Depósito");
        JButton statementButton = createButton("Extrato");
        JButton withdrawalButton = createButton("Saque");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        bottomPanel.add(depositButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        bottomPanel.add(statementButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        bottomPanel.add(withdrawalButton, gbc);

        JButton cryptoButton = createButton("CRIPTOMOEDAS");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 0, 20, 0); // Adjust the insets to bring the button up
        bottomPanel.add(cryptoButton, gbc);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 24));
        button.setPreferredSize(new Dimension(200, 100)); // Define um tamanho fixo para os botões
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        return button;
    }

    private void loadAccountInfo(String cpf) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.getUsuarioByCpf(cpf);
        if (usuario != null) {
            accountLabel.setText("Conta: " + usuario.getNome());
            balanceLabel.setText("Saldo: R$ " + usuario.getSaldo());
        } else {
            accountLabel.setText("Conta: Não encontrada");
            balanceLabel.setText("Saldo: --");
        }
    }
}
