package chatbot.example.Chatbot.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private  String apikey;

     private final WebClient webClient = WebClient.builder()
              .baseUrl("https://api.openai.com/v1")
              .build();
       public  String response(String userMessage){
           try {
               JSONObject requestbody = new JSONObject();
               requestbody.put("model", "gpt-4.1");
               requestbody.put("input", userMessage);

               String botresponse = webClient.post()
                       .uri("/responses")
                       .header("Content-Type", "application/json")
                       .header("Authorization", "Bearer " + apikey)
                       .bodyValue(requestbody.toString())
                       .retrieve()
                       .bodyToMono(String.class)
                       .block();
               JSONObject botjson = new JSONObject(botresponse);
               return botjson.getJSONArray("output")
                       .getJSONObject(0)
                       .getJSONArray("content")
                       .getJSONObject(0)
                       .getString("text");
           }
           catch (Exception e){
               e.printStackTrace();
               return e.getMessage();
           }
       }
}
