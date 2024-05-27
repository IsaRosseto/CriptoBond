package view;

import resources.CriptoAPI;
import resources.HistoricoPrecosCripto;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class PainelPrecosCripto extends JPanel {

    private Map<String, JLabel> cryptoLabels;
    private Map<String, Double> cryptoPrices;

    public PainelPrecosCripto(List<String> cryptoColumns) {
        this.cryptoLabels = new HashMap<>();
        this.cryptoPrices = new HashMap<>();
        initialize(cryptoColumns);
        fetchAndUpdatePrices();
    }

    private void initialize(List<String> cryptoColumns) {
        setLayout(new GridLayout(cryptoColumns.size(), 1, 10, 10));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String crypto : cryptoColumns) {
            JLabel cryptoLabel = createCryptoLabel(crypto + ": Carregando...");
            cryptoLabels.put(crypto, cryptoLabel);
            add(cryptoLabel);
        }
    }

    private JLabel createCryptoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void fetchAndUpdatePrices() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    JSONObject prices = CriptoAPI.getCryptoPrices();
                    SwingUtilities.invokeLater(() -> updatePrices(prices));
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> showError("Falha ao buscar as cotações após várias tentativas. Por favor, tente novamente mais tarde."));
                }
            }
        }, 0, 300000); // Atualiza a cada 5 minutos
    }

    private void updatePrices(JSONObject prices) {
        try {
            for (String crypto : cryptoLabels.keySet()) {
                if (prices.has(crypto.toLowerCase())) {
                    double price = prices.getJSONObject(crypto.toLowerCase()).getDouble("usd");
                    cryptoPrices.put(crypto, price);
                    cryptoLabels.get(crypto).setText(String.format("%s: $%.2f", crypto, price));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HistoricoPrecosCripto.savePriceHistory(cryptoPrices);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
