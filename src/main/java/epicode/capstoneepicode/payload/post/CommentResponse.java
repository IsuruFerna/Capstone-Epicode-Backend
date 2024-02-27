package epicode.capstoneepicode.payload.post;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        String comment,
        LocalDateTime timeStamp,
        String firstName,
        String lastName
) {
}
