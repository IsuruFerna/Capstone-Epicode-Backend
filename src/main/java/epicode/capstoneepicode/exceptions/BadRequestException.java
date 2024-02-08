package epicode.capstoneepicode.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class BadRequestException extends RuntimeException{
    private List<ObjectError> errorList;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<ObjectError> errorList) {
        super("Errors in body");
        this.errorList = errorList;
    }
}
