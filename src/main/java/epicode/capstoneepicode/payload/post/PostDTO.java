package epicode.capstoneepicode.payload.post;

import jakarta.validation.constraints.Size;

import java.net.URL;

public record PostDTO(
        @Size( max = 500, message = "Content can't be longer than 500")
        String content,
        String media
) {
}
