package epicode.capstoneepicode.payload.post;

import java.util.UUID;

public record NewMediaResponse(
        String imageUrl,
        UUID id
) {
}
