package epicode.capstoneepicode.payload.post;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponsePostDTO(
        UUID id,
        String content,
        String media,
        LocalDateTime timeStamp,
        Boolean edited,
        String username,
        String firstName,
        String lastName,
        Boolean isLiked
) {
}
