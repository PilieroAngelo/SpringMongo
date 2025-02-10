package com.azienda.Exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hibernate.validator.internal.engine.messageinterpolation.el.RootResolver.FORMATTER;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    private String getCurrentFormattedDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATTER));
    }

    private ErrorDetails buildErrorDetails(String message, String description, String type) {
        return new ErrorDetails(getCurrentFormattedDate(), message, description, type);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> handleIdNotFoundException(IdNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    //public ResposteEntity<>
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                              WebRequest webRequest) {
        log.error("Handled Exception: MethodArgumentNotValidException - {}", exception.getMessage());
        ErrorDetails errorDetails = buildErrorDetails("ONE OR MORE FIELDS DO NOT RESPECT VALIDATION",
                webRequest.getDescription(false), "BAD REQUEST, ARGUMENT NOT VALID");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
