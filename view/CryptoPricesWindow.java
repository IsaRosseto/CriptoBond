package view;

import resources.CryptoAPI;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CryptoPricesWindow extends JFrame {

    private JPanel bitcoinPanel;
    private JPanel ethereumPanel;
    private JPanel ripplePanel;

    private JLabel bitcoinLabel;
    private JLabel ethereumLabel;
    private JLabel rippleLabel;

    private JLabel bitcoinChangeIcon;
    private JLabel ethereumChangeIcon;
    private JLabel rippleChangeIcon;

    private Map<String, Double> previousPrices = new HashMap<>();

    public CryptoPricesWindow() {
        initialize();
        fetchAndUpdatePrices();
    }

    private void initialize() {
        setTitle("Cotações de Criptomoedas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.DARK_GRAY);

        // Painel superior para o título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Cotações de Criptomoedas");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // Painel central para as cotações
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel para Bitcoin
        bitcoinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bitcoinPanel.setBackground(Color.DARK_GRAY);
        bitcoinLabel = createCryptoLabel("Bitcoin: Carregando...", "https://cryptologos.cc/logos/bitcoin-btc-logo.png");
        bitcoinChangeIcon = new JLabel();
        bitcoinPanel.add(bitcoinLabel);
        bitcoinPanel.add(bitcoinChangeIcon);
        centerPanel.add(bitcoinPanel);

        // Painel para Ethereum
        ethereumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ethereumPanel.setBackground(Color.DARK_GRAY);
        ethereumLabel = createCryptoLabel("Ethereum: Carregando...", "https://cryptologos.cc/logos/ethereum-eth-logo.png");
        ethereumChangeIcon = new JLabel();
        ethereumPanel.add(ethereumLabel);
        ethereumPanel.add(ethereumChangeIcon);
        centerPanel.add(ethereumPanel);

        // Painel para Ripple
        ripplePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ripplePanel.setBackground(Color.DARK_GRAY);
        rippleLabel = createCryptoLabel("Ripple: Carregando...", "https://cryptologos.cc/logos/xrp-xrp-logo.png");
        rippleChangeIcon = new JLabel();
        ripplePanel.add(rippleLabel);
        ripplePanel.add(rippleChangeIcon);
        centerPanel.add(ripplePanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JLabel createCryptoLabel(String text, String iconUrl) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        label.setForeground(Color.WHITE);

        try {
            ImageIcon icon = new ImageIcon(new URL(iconUrl));
            Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }

        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    private void fetchAndUpdatePrices() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    JSONObject prices = CryptoAPI.getCryptoPrices();
                    SwingUtilities.invokeLater(() -> updatePrices(prices));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60000); // Atualiza a cada 60 segundos
    }

    private void updatePrices(JSONObject prices) {
        updatePrice("bitcoin", prices.getJSONObject("bitcoin").getDouble("usd"), bitcoinLabel, bitcoinChangeIcon);
        updatePrice("ethereum", prices.getJSONObject("ethereum").getDouble("usd"), ethereumLabel, ethereumChangeIcon);
        updatePrice("ripple", prices.getJSONObject("ripple").getDouble("usd"), rippleLabel, rippleChangeIcon);
    }

    private void updatePrice(String cryptoName, double newPrice, JLabel label, JLabel changeIconLabel) {
        String text = String.format("%s: $%.2f", capitalize(cryptoName), newPrice);
        label.setText(text);

        String iconUrl = "";
        if (previousPrices.containsKey(cryptoName)) {
            double oldPrice = previousPrices.get(cryptoName);
            if (newPrice > oldPrice) {
                iconUrl = "https://upload.wikimedia.org/wikipedia/commons/5/50/Green-Up-Arrow.png"; // URL do ícone de alta
            } else {
                iconUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Red-Down-Arrow.png"; // URL do ícone de baixa
            }
        }
        previousPrices.put(cryptoName, newPrice);

        if (!iconUrl.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(new URL(iconUrl));
                Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                changeIconLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}