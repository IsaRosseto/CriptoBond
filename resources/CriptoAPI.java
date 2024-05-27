package resources;

import DAO.Database;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;


public class CriptoAPI {
    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,ripple&vs_currencies=usd";
    private static final int MAX_RETRIES = 10;
    private static final int INITIAL_WAIT_TIME = 1000;
    private static final int RANDOM_WAIT_TIME = 500;

    
    public static JSONObject getCryptoPrices() throws Exception {
        int attempt = 0;
        int waitTime = INITIAL_WAIT_TIME;
        Random random = new Random();

        while (attempt < MAX_RETRIES) {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 429) {
                    System.out.println("Rate limited. Retrying in " + waitTime + "ms");
                    Thread.sleep(waitTime + random.nextInt(RANDOM_WAIT_TIME));
                    attempt++;
                    waitTime *= 2;
                    continue;
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                connection.disconnect();

                JSONObject json = new JSONObject(content.toString());
                System.out.println("API Response: " + json.toString());
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                if (attempt >= MAX_RETRIES - 1) {
                    throw new Exception("Failed to fetch prices after " + MAX_RETRIES + " attempts", e);
                }
                Thread.sleep(waitTime + random.nextInt(RANDOM_WAIT_TIME));
                waitTime *= 2;
                attempt++;
            }
        }

        throw new Exception("Failed to fetch prices after " + MAX_RETRIES + " attempts");
    }
    
    private void adicionarCriptoPadrao(Map<String, Map<String, Double>> criptoInfo) throws Exception {
        JSONObject json = getCryptoPrices();
        
        Map<String, Double> bitcoinInfo = new HashMap<>();
        bitcoinInfo.put("cotacao", json.getJSONObject("bitcoin").getDouble("usd"));
        bitcoinInfo.put("taxa_compra", 0.02);
        bitcoinInfo.put("taxa_venda", 0.03);
        criptoInfo.put("Bitcoin", bitcoinInfo);

        Map<String, Double> ethereumInfo = new HashMap<>();
        ethereumInfo.put("cotacao", json.getJSONObject("ethereum").getDouble("usd"));
        ethereumInfo.put("taxa_compra", 0.01);
        ethereumInfo.put("taxa_venda", 0.02);
        criptoInfo.put("Ethereum", ethereumInfo);

        Map<String, Double> rippleInfo = new HashMap<>();
        rippleInfo.put("cotacao", json.getJSONObject("ripple").getDouble("usd"));
        rippleInfo.put("taxa_compra", 0.01);
        rippleInfo.put("taxa_venda", 0.01);
        criptoInfo.put("Ripple", rippleInfo);
    }
    
    public Map<String, Map<String, Double>> getCriptoInfo() {
        Map<String, Map<String, Double>> criptoInfo = new HashMap<>();

        // Adicionar cotações das criptomoedas padrão
        try {
            adicionarCriptoPadrao(criptoInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Adicionar outras criptomoedas do banco de dados
        String query = "SELECT nome_criptos, cotacao_atual, taxa_compra, taxa_venda FROM criptos";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nome = rs.getString("nome_criptos");
                double cotacao = rs.getDouble("cotacao_atual");
                double taxaCompra = rs.getDouble("taxa_compra");
                double taxaVenda = rs.getDouble("taxa_venda");

                if (!criptoInfo.containsKey(nome)) { // Evitar sobrescrever as criptomoedas padrão
                    Map<String, Double> info = new HashMap<>();
                    info.put("cotacao", cotacao);
                    info.put("taxa_compra", taxaCompra);
                    info.put("taxa_venda", taxaVenda);

                    criptoInfo.put(nome, info);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return criptoInfo;
    }
    
    public double getCotacao(String cripto) {
        Map<String, Map<String, Double>> criptoInfo = getCriptoInfo();
        if (criptoInfo.containsKey(cripto)) {
            return criptoInfo.get(cripto).get("cotacao");
        }
        return 0.0;
    }

    public double getTaxaCompra(String cripto) {
        Map<String, Map<String, Double>> criptoInfo = getCriptoInfo();
        if (criptoInfo.containsKey(cripto)) {
            return criptoInfo.get(cripto).get("taxa_compra");
        }
        return 0.0;
    }

    public double getTaxaVenda(String cripto) {
        Map<String, Map<String, Double>> criptoInfo = getCriptoInfo();
        if (criptoInfo.containsKey(cripto)) {
            return criptoInfo.get(cripto).get("taxa_venda");
        }
        return 0.0;
    }
}
