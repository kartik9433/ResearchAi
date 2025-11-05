package chatbot.example.Chatbot.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WikipediaService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://en.wikipedia.org/w/api.php")
            .defaultHeader("User-Agent", "ChatbotResearchApp/1.0 (kartik@abes.ac.in)")
            .build();

    public String response(String query) {
        try {
            String result = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("action", "query")
                            .queryParam("prop", "extracts")
                            .queryParam("exintro", "true")
                            .queryParam("explaintext", "true")
                            .queryParam("format", "json")
                            .queryParam("titles", query)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);
            JsonNode pageNode = root.path("query").path("pages");
            JsonNode firstPage = pageNode.elements().next();

            return firstPage.path("extract").asText("No summary found");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching from Wikipedia: " + e.getMessage();
        }
    }
}
