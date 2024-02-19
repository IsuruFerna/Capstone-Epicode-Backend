package epicode.capstoneepicode.payload.post;

import java.time.LocalDateTime;
import java.util.UUID;

public record NewPostResponse(
        UUID id,
        LocalDateTime timeStamp,
        Boolean edited
) {
}
