package epicode.capstoneepicode.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String from;
    private String text;
    private String senderName;
    private String receiverName;
    private Status status;
}
