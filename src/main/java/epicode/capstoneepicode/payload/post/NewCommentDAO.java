package epicode.capstoneepicode.payload.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewCommentDAO(
        @NotEmpty(message = "comment can't be empty")
        @Size(max = 500)
        String comment
) {
}
