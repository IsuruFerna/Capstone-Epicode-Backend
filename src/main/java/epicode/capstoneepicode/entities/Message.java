package epicode.capstoneepicode.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String from;
    private String text;
}
