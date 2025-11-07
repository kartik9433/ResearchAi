package chatbot.example.Chatbot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatbotAggregatorService {
    @Autowired
    ChatbotService chatbotService;

    @Autowired
    DeepSeekService deepSeekService;

    @Autowired
    OpenAiService openAiService;

    @Autowired
    WikipediaService wikipediaService;

}
