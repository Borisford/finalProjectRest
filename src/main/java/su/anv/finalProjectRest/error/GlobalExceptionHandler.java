package su.anv.finalProjectRest.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import su.anv.finalProjectRest.error.dto.ErrorResponseDto;
import su.anv.finalProjectRest.error.exception.DuplicatedRegistrationParamsException;
import su.anv.finalProjectRest.error.exception.EndBeforeStartException;
import su.anv.finalProjectRest.error.exception.NoTickerInBaseException;

import java.time.DateTimeException;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EndBeforeStartException.class)
    public ResponseEntity<ErrorResponseDto> endBeforeStartExceptionHandler(EndBeforeStartException exception) {
        log.error("Начало периода раньше, чем конец");
        String msg = String.format("\"%s\" раньше, чем \"%s\"", exception.getEnd(), exception.getStart());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .id(UUID.randomUUID())
                        .message(msg)
                        .build());
    }

    @ExceptionHandler(DuplicatedRegistrationParamsException.class)
    public ResponseEntity<ErrorResponseDto> duplicatedRegistrationParamsExceptionHandler(DuplicatedRegistrationParamsException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .id(UUID.randomUUID())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundExceptionHandler(UsernameNotFoundException exception) {
        log.error("Human not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .id(UUID.randomUUID())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NoTickerInBaseException.class)
    public ResponseEntity<ErrorResponseDto> entityNotFoundExceptionHandler(NoTickerInBaseException exception) {
        log.error("Такого тикера нет в базе");
        String msg = String.format("Тикер \"%s\" не найден в базе", exception.getTicker());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .id(UUID.randomUUID())
                        .message(msg)
                        .build());
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorResponseDto> dateTimeExceptionHandler(DateTimeException exception) {
        log.error("Дата указана не верно");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .id(UUID.randomUUID())
                        .message(exception.getMessage())
                        .build());
    }

    /*@ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> globalExceptionHandler(Throwable exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .id(UUID.randomUUID())
                        .message("message: " + exception.getMessage())
                        .build());
    }*/

}
