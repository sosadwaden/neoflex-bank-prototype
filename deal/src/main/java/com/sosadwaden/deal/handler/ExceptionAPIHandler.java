package com.sosadwaden.deal.handler;

import com.sosadwaden.deal.exception.StatementNotFoundException;
import com.sosadwaden.deal.exception.ValidationError;
import com.sosadwaden.deal.exception.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class ExceptionAPIHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAPIHandler.class);

    @ExceptionHandler(StatementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ValidationErrorResponse> handleStatementNotFoundException(StatementNotFoundException exception) {
        ValidationError error = ValidationError.builder()
                .field("statementId")
                .message(exception.getMessage())
                .build();

        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .errors(Collections.singletonList(error))
                .build();

        logger.error("Statement с таким id не найден: {}", errorResponse);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
