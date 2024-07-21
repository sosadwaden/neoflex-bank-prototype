package com.sosadwaden.statement.handler;

import com.sosadwaden.statement.exception.ValidationError;
import com.sosadwaden.statement.exception.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAPIHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAPIHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ValidationError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ValidationError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        logger.error("Ошибка валидации: {}", errors);
        logger.debug("Детали исключения: {}", exception);
        return new ResponseEntity<>(ValidationErrorResponse.builder().errors(errors).build(), HttpStatus.BAD_REQUEST);
    }
}
