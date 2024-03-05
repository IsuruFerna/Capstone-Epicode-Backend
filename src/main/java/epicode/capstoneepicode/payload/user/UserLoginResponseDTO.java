package epicode.capstoneepicode.payload.user;

import java.util.UUID;

public record UserLoginResponseDTO(
        String token,
        UUID id
) {
}
