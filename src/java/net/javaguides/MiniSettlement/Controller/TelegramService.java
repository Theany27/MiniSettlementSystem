
package net.javaguides.MiniSettlement.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TelegramService {

    private static final String BOT_TOKEN = "8633311545:AAEZJptEBCYFU5RUKji5NeOludOkjS2EFnY";

    public static void sendMessage(String chatId, String message) {
        try {
            System.out.println("telegram Service");
            SSLFix.disableSSLVerification();
            String encodedMessage = URLEncoder.encode(message, "UTF-8");

            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN
                    + "/sendMessage?chat_id=" + chatId
                    + "&text=" + encodedMessage
                    + "&parse_mode=Markdown";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Telegram Response: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
