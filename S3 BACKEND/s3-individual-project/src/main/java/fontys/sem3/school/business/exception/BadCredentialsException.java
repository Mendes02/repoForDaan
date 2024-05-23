package fontys.sem3.school.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadCredentialsException extends ResponseStatusException{
    public BadCredentialsException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_CREDENTIALS");
    }

    public BadCredentialsException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
