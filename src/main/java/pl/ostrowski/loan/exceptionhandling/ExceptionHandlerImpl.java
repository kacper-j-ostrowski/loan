package pl.ostrowski.loan.exceptionhandling;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerImpl {

    @RequestMapping(produces = "application/json")
    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleExceptions(Throwable ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error( "Error occured with message: {} and stack: {}. " +
                "Error unique id: {}", ex.getMessage(), ex.getStackTrace(), exceptionUUID);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage() + " Error unique id: " + exceptionUUID);
    }
}
