package resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
}
