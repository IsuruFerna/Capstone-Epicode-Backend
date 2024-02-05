package epicode.capstoneepicode.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputMessage {
    private String from;
    private String text;
    private String time;
}
