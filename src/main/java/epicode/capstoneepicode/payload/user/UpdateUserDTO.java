package epicode.capstoneepicode.payload.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(

        @Size(min = 3, max = 30, message = "Name must contains between 3 to 30 characters")
        String firstName,

        @Size(min = 3, max = 30, message = "Last name must contains between 3 to 30 characters")
        String lastName,

        @Size(min = 3, max = 30, message = "Username must contains between 3 to 30 characters")
        String username,

        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email isn't valid")
        String email,

        String birthDay,
        @Size(min = 3, max = 36, message = "Password must contains between 12 to 36 characters")
        String password,

        String passwordConfirm
) {
}
