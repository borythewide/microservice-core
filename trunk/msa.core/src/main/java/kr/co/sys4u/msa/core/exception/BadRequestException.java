package kr.co.sys4u.msa.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends IllegalArgumentException {

    private static final long serialVersionUID = 8598796377689608365L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(BindingResult bindingResult) {
        this(bindingResult.getAllErrors().get(0).getDefaultMessage());

    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
