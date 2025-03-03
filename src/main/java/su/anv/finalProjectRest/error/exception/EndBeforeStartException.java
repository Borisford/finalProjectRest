package su.anv.finalProjectRest.error.exception;

import lombok.Getter;

@Getter
public class EndBeforeStartException extends RuntimeException{
    private final String start;
    private final String end;

    public EndBeforeStartException(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
