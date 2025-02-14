package su.anv.finalProjectRest.error.exception;

import lombok.Getter;

@Getter
public class NoTickerInBaseException extends RuntimeException{
    private final String ticker;

    public NoTickerInBaseException(String ticker) {
        this.ticker = ticker;
    }
}
