package epicode.capstoneepicode.payload;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsPayLoadWithListDTO(
        String message,
        LocalDateTime timestamp,
        List<String> errorList
) {
}
