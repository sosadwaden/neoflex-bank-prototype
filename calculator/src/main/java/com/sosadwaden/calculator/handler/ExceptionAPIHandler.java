package com.sosadwaden.calculator.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sosadwaden.calculator.exception.ScoringFailureException;
import com.sosadwaden.calculator.exception.ValidationError;
import com.sosadwaden.calculator.exception.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class ExceptionAPIHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAPIHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ValidationError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        logger.error("Ошибка валидации: {}", errors);
        return new ResponseEntity<>(ValidationErrorResponse.builder().errors(errors).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<List<ValidationError>> handleInvalidFormatException(InvalidFormatException exception) {
        String field = exception.getPath().get(0).getFieldName();
        String error = String.format("Неверное значение '%s' для поля '%s'. Допустимыми значениями являются: %s",
                exception.getValue(), field, getAllowedEnumValues(exception.getTargetType()));
        ValidationError errorDetails = new ValidationError(field, error);

        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScoringFailureException.class)
    public ResponseEntity<String> handleScoringFailureException(ScoringFailureException exception) {
        logger.warn("Скоринг неудался: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String getAllowedEnumValues(Class<?> enumClass) {
        Field[] enumFields = enumClass.getDeclaredFields();
        List<String> enumValues = Stream.of(enumFields)
                .filter(field -> field.isEnumConstant())
                .map(field -> field.getName())
                .collect(Collectors.toList());

        return String.join(", ", enumValues);
    }

}
