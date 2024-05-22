package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import DAO.Database;
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

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add border around the center panel
        GridBagConstraints gbc = new GridBagConstraints();

        // Assuming the image is named "deposito_criptoBond.png" and located in the project directory
        String iconPath = "src/resources/deposito_criptoBond.png";
        System.out.println("Icon Path: " + iconPath);

        JButton depositButton = createButton("Depósito", iconPath);
        JButton statementButton = createButton("Extrato", iconPath);
        JButton withdrawalButton = createButton("Saque", iconPath);

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

        add(centerPanel, BorderLayout.CENTER);

        // Painel inferior para criptomoedas
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add border around the bottom panel
        JButton cryptoButton = createCryptoButton("CRIPTOMOEDAS", null);
        cryptoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CryptoPricesWindow().setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Adjust the insets to bring the button up
        bottomPanel.add(cryptoButton, gbc);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 24));
        button.setPreferredSize(new Dimension(200, 200)); // Define um tamanho fixo para os botões
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));

        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            if (icon.getIconWidth() == -1) {
                System.out.println("Icon not found at " + iconPath);
            } else {
                button.setIcon(icon);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
        }

        return button;
    }

    private JButton createCryptoButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(400, 100)); // Define um tamanho fixo para o botão de criptomoedas
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));

        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            if (icon.getIconWidth() == -1) {
                System.out.println("Icon not found at " + iconPath);
            } else {
                button.setIcon(icon);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
        }

        return button;
    }

    private void loadAccountInfo(String cpf) {
        Connection conn = null;
        try {
            // Obtendo conexão com o banco de dados
            conn = Database.getConnection();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            Usuario usuario = usuarioDAO.getUsuarioByCPF(cpf);
            if (usuario != null) {
                accountLabel.setText("Conta: " + usuario.getNome());
                balanceLabel.setText("Saldo: R$ " + usuario.getSaldo());
            } else {
                accountLabel.setText("Conta: Não encontrada");
                balanceLabel.setText("Saldo: N/A");
            }
        } catch (SQLException e) {
            accountLabel.setText("Erro ao carregar conta");
            balanceLabel.setText("Erro ao carregar saldo");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
