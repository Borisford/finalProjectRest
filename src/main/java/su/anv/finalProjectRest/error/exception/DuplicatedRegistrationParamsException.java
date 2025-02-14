package su.anv.finalProjectRest.error.exception;

import lombok.Getter;

@Getter
public class DuplicatedRegistrationParamsException extends RuntimeException{
    private final String param;

    public DuplicatedRegistrationParamsException(String param) {
        this.param = param;
    }


    public DuplicatedRegistrationParamsException(String message, String param) {
        super(message);
        this.param = param;
    }
}
