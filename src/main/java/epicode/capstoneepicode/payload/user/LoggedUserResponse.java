package epicode.capstoneepicode.payload.user;

import java.time.LocalDate;
import java.util.UUID;

public record LoggedUserResponse(
        UUID id,
        String firstName,
        String lastName,
        String profilePicture,
        String role,
        String username,
        Integer postAmount,
        Integer followings,
        Integer followers,
        LocalDate birthDay,
        String email
) {
}
