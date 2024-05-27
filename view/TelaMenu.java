package view;

import DAO.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;

public class TelaMenu extends JFrame {

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private JLabel balanceLabel;
    private Timer timer;
    private static final double TAXA_CAMBIO_USD_BRL = 5.17; // Taxa de câmbio fixa para USD/BRL

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

        // Painel para cotações
        JPanel cotacoesPanel = new JPanel();
        cotacoesPanel.setBackground(Color.BLACK);
        cotacoesPanel.setLayout(new BoxLayout(cotacoesPanel, BoxLayout.X_AXIS));
        cotacoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Ajuste para reduzir espaço inferior
        JPanel cotacoesWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Wrapper para centralizar
        cotacoesWrapper.setBackground(Color.BLACK);
        cotacoesWrapper.add(cotacoesPanel);
        add(cotacoesWrapper, BorderLayout.CENTER);

        // Obter cotações e adicionar ao painel
        Map<String, Double> precos = getPrecosCriptomoedas();
        adicionarCotacoesAoPainel(cotacoesPanel, precos);

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

        JButton saldoButton = createButton("Saldo");
        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaSaldo(usuario).setVisible(true);
            }
        });

        JButton cryptoButton = createLargeButton("CRIPTOMOEDAS");
        cryptoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaTransacaoCripto(usuario).setVisible(true);
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

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(saldoButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupar o espaço de duas colunas
        centerPanel.add(cryptoButton, gbc);

        add(centerPanel, BorderLayout.SOUTH);

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

    private JButton createLargeButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 24));
        button.setPreferredSize(new Dimension(410, 100)); // Tamanho especial
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

    private Map<String, Double> getPrecosCriptomoedas() {
        Map<String, Double> precos = usuarioDAO.getPrecosCriptomoedas();

        // Adicionar cotações das moedas padrão
        Map<String, Double> apiPrecos = getPrecosCriptomoedasAPI();
        precos.putAll(apiPrecos);

        return precos;
    }

    private Map<String, Double> getPrecosCriptomoedasAPI() {
        Map<String, Double> apiPrecos = new HashMap<>();

        try {
            String[] moedas = {"bitcoin", "ethereum", "ripple"};
            for (String moeda : moedas) {
                double precoUSD = fetchCryptoPrice(moeda);
                double precoBRL = precoUSD * TAXA_CAMBIO_USD_BRL;
                apiPrecos.put(moeda.substring(0, 1).toUpperCase() + moeda.substring(1), precoBRL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return apiPrecos;
    }

    private double fetchCryptoPrice(String crypto) throws Exception {
        String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + crypto + "&vs_currencies=usd";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        JSONObject json = new JSONObject(content.toString());
        return json.getJSONObject(crypto).getDouble("usd");
    }

    private void adicionarCotacoesAoPainel(JPanel panel, Map<String, Double> precos) {
        for (Map.Entry<String, Double> entry : precos.entrySet()) {
            String nome = entry.getKey();
            double preco = entry.getValue();

            JPanel criptoPanel = new JPanel();
            criptoPanel.setLayout(new BoxLayout(criptoPanel, BoxLayout.Y_AXIS));
            criptoPanel.setBackground(Color.DARK_GRAY);
            criptoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel nomeLabel = new JLabel(nome.toUpperCase());
            nomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            nomeLabel.setForeground(Color.WHITE);
            nomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            criptoPanel.add(nomeLabel);

            JLabel precoLabel = new JLabel(formatCurrency(preco));
            precoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            precoLabel.setForeground(preco > 0 ? Color.GREEN : Color.RED);
            precoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            criptoPanel.add(precoLabel);

            panel.add(criptoPanel);
            panel.add(Box.createRigidArea(new Dimension(10, 0))); // Espaçamento horizontal
        }
    }


    }

