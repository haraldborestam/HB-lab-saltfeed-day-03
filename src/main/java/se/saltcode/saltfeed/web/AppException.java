package se.saltcode.saltfeed.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.NoSuchElementException;

@ControllerAdvice
public class AppException {

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoFoundException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ex.getBindingResult().getFieldErrors().stream().map(ErrorResponse::toErrorMessage).toList();

        var errorResponse = new ErrorResponse(
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(ErrorResponse::toErrorMessage)
                        .toList()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorResponse> handleValidationException(IllegalArgumentException ex) {

        var errorResponse = new ErrorResponse(
                Arrays.asList(
                        ex.getMessage()
                )
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
