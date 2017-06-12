package kr.co.sys4u.msa.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchResourceException extends RuntimeException {

    private static final long serialVersionUID = 8598796377689608365L;

    public NoSuchResourceException() {
        super();
    }

    public NoSuchResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchResourceException(String message) {
        super(message);
    }

    public NoSuchResourceException(Throwable cause) {
        super(cause);
    }
}
