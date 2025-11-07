package chatbot.example.Chatbot.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DeepSeekService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.deepseek.com")
            .build();

    public String deepSeek(String userMessage) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("stream", false);

            JSONArray messages = new JSONArray();
            messages.put(new JSONObject()
                    .put("role", "system")
                    .put("content", "You are a helpful assistant."));
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", userMessage));
            requestBody.put("messages", messages);

            System.out.println("➡️ DeepSeek Request Body:\n" + requestBody.toString(2));

            String responseString = webClient.post()
                    .uri("/chat/completions")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("✅ Raw DeepSeek Response:\n" + responseString);

            JSONObject response = new JSONObject(responseString);
            JSONArray choices = response.getJSONArray("choices");

            if (choices.isEmpty()) {
                return "No response from DeepSeek.";
            }

            String botResponse = choices
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return botResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}
