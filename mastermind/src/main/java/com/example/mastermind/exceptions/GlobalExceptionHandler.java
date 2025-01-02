package com.example.mastermind.exceptions;

import com.example.mastermind.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A Global Exception handler for the application. This class handles exceptions and returns the error messages as a
 * response to the client
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameIdNotProvidedException.class)
    public ResponseEntity<ErrorResponse> handleGameIdNotProvidedException(GameIdNotProvidedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameNotFoundException(GameNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidDifficultyInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDifficultyInputException(InvalidDifficultyInputException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
