package epicode.capstoneepicode.payload.errors;

import java.util.Date;
import java.util.List;

public record ErrorsPayLoadWithList(
        String message,
        Date timestamp,
        List<String> errorsList
) {
}
