package epicode.capstoneepicode.payload.user;

import epicode.capstoneepicode.entities.user.Role;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String profilePicture,
        String role,
        String username,
        Integer postAmount,
        Integer following,
        Integer followers,
        Boolean isFollowing
) {
}
