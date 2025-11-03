package chatbot.example.Chatbot.Controller;

import chatbot.example.Chatbot.Model.Message;
import chatbot.example.Chatbot.Service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatbotController {

      @Autowired
      private ChatbotService chatbotService;

      @PostMapping("/ask")
      public String askChatbot(@RequestBody Map<String, String> payload) {
            String userMessage = payload.get("userMessage");
            return chatbotService.Response(userMessage);
      }

      @GetMapping("/getHistory")
    public List<Message> getAll(){
            return  chatbotService.getAll();
      }

}
