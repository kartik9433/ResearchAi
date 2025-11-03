package chatbot.example.Chatbot.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String userMessage;

    @Column(columnDefinition = "LONGTEXT")
    private String botResponse;

    public Message(Long id, String userMessage, String botResponse) {
        this.id = id;
        this.userMessage = userMessage;
        this.botResponse = botResponse;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getBotResponse() {
        return botResponse;
    }

    public void setBotResponse(String botResponse) {
        this.botResponse = botResponse;
    }
}
