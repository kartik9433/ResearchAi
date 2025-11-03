package chatbot.example.Chatbot.Repository;

import chatbot.example.Chatbot.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotRepository extends JpaRepository<Message,Long> {
}
