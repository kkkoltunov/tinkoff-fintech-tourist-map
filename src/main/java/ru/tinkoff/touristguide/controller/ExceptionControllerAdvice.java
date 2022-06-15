package ru.tinkoff.touristguide.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.touristguide.exception.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<ExceptionMessages> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new ExceptionMessages(List.of(ex.getMessage())));
    }

    @ExceptionHandler({SightNotFoundException.class})
    public ResponseEntity<ExceptionMessages> handleSightNotFoundException(SightNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new ExceptionMessages(List.of(ex.getMessage())));
    }

    @ExceptionHandler({CreateRouteException.class})
    public ResponseEntity<ExceptionMessages> handleCreateRouteException(CreateRouteException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionMessages(List.of(ex.getMessage())));
    }

    @ExceptionHandler({NotValidSightException.class})
    public ResponseEntity<ExceptionMessages> handleNotValidSightException(NotValidSightException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionMessages(List.of(ex.getMessage().split(":"))));
    }

    @ExceptionHandler({NotValidRouteException.class})
    public ResponseEntity<ExceptionMessages> handleNotValidRouteException(NotValidRouteException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionMessages(List.of(ex.getMessage().split(":"))));
    }
}
