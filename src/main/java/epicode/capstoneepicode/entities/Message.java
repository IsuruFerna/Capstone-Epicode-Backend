package epicode.capstoneepicode.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String from;
    private String text;
    private String senderName;
    private String receiverName;
    private String message;
    private Status status;
}
