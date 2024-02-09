package epicode.capstoneepicode.payload.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.net.URL;
import java.util.UUID;

public record NewPostDTO(
        @Size( max = 500, message = "Content can't be longer than 500")
        String content,
        URL media,
        @NotEmpty(message = "user Id required!")
        UUID userid
) {
}
