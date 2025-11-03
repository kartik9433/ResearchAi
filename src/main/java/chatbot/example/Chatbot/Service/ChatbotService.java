package chatbot.example.Chatbot.Service;

import chatbot.example.Chatbot.Model.Message;
import chatbot.example.Chatbot.Repository.ChatbotRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ChatbotService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Autowired
    private ChatbotRepository chatbotRepository;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com/v1beta/models")
            .build();

    public String Response(String userMessage) {
        try {
            JSONObject requestBody = new JSONObject()
                    .put("contents", new JSONArray()
                            .put(new JSONObject()
                                    .put("role", "user")
                                    .put("parts", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("text", userMessage)))));
                  System.out.print(requestBody);
            System.out.println("➡️ Gemini Request Body:\n" + requestBody.toString(2));

            String responseString = webClient.post()
                    .uri("/gemini-2.5-pro:generateContent")
                    .header("Content-Type", "application/json")
                    .header("x-goog-api-key", apiKey)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            System.out.println("✅ Raw Gemini API Response:\n" + responseString);

            if (responseString == null || responseString.isEmpty()) {
                return "Empty response from Gemini API.";
            }

            JSONObject response = new JSONObject(responseString);

            if (response.has("error")) {
                JSONObject error = response.getJSONObject("error");
                return "Gemini API Error: " + error.optString("message", "Unknown error");
            }

            String botResponse = response
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            Message message = new Message();
            message.setUserMessage(userMessage);
            message.setBotResponse(botResponse);
            chatbotRepository.save(message);

            return botResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public List<Message> getAll() {
         return chatbotRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return chatbotRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
    }

}
