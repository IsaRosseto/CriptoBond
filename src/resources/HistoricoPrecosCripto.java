package resources;

import java.util.HashMap;
import java.util.Map;

public class HistoricoPrecosCripto {

    private static Map<String, Double> currentPrices = new HashMap<>();
    private static Map<String, Double> previousPrices = new HashMap<>();

    public static void savePriceHistory(Map<String, Double> prices) {
        previousPrices = new HashMap<>(currentPrices);
        currentPrices = new HashMap<>(prices);
    }

    public static double getCurrentPrice(String cryptoName) {
        return currentPrices.getOrDefault(cryptoName, 0.0);
    }

    public static double getPreviousPrice(String cryptoName) {
        return previousPrices.getOrDefault(cryptoName, 0.0);
    }

    public static Map<String, Double> getCurrentPrices() {
        return currentPrices;
    }

    public static Map<String, Double> getPreviousPrices() {
        return previousPrices;
    }
}
