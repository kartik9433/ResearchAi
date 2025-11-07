package chatbot.example.Chatbot.Controller;

import chatbot.example.Chatbot.Model.Message;
import chatbot.example.Chatbot.Service.ChatbotService;
import chatbot.example.Chatbot.Service.DeepSeekService;
import chatbot.example.Chatbot.Service.OpenAiService;
import chatbot.example.Chatbot.Service.WikipediaService;
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
      @Autowired
      private WikipediaService wikipediaService;
      @Autowired
      private DeepSeekService deepSeekService;

      @Autowired
      private OpenAiService openAiService;
         //gemini response
      @PostMapping("/ask")
      public String askChatbot(@RequestBody Map<String, String> payload) {
            String userMessage = payload.get("userMessage");
            return chatbotService.Response(userMessage);
      }

      @GetMapping("/getHistory")
    public List<Message> getAll(){
            return  chatbotService.getAll();
      }

      @GetMapping("/getByID/{id}")
    public Message getMessageById(@PathVariable Long id){
          return chatbotService.getMessageById(id);
      }

      @DeleteMapping("/Delete/History")
      public void DeleteHistory(){
            chatbotService.DeleteHistory();
      }

      @DeleteMapping("/Delete")
    public  void DeleteById(@RequestParam long Id){
             chatbotService.DeleteById(Id);
      }

      //wikipedia response
      @GetMapping("/wiki")
      public String wikipedia(@RequestParam  String query){
          return  wikipediaService.response(query);
      }

     //deepSeek response
      @PostMapping("/deepSeek")
    public  String deepSeek(@RequestParam String userMessage){
          return deepSeekService.deepSeek(userMessage);
      }

      //open ai response
      @PostMapping("/openai")
    public String openai (@RequestParam String userMessage){
          return  openAiService.response(userMessage);
      }
}
