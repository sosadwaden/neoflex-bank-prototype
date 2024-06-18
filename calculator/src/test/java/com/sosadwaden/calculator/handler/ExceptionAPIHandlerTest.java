package com.sosadwaden.calculator.handler;

import com.sosadwaden.calculator.dto.EmploymentDto;
import com.sosadwaden.calculator.dto.ScoringDataDto;
import com.sosadwaden.calculator.enums.EmploymentStatus;
import com.sosadwaden.calculator.enums.Gender;
import com.sosadwaden.calculator.enums.MaritalStatus;
import com.sosadwaden.calculator.enums.EmploymentPosition;
import com.sosadwaden.calculator.exception.ScoringFailureException;
import com.sosadwaden.calculator.exception.ValidationError;
import com.sosadwaden.calculator.exception.ValidationErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionAPIHandlerTest {

    private final ExceptionAPIHandler exceptionHandler = new ExceptionAPIHandler();

    @Test
    void handleValidationExceptionsTest() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(new FieldError("testDto", "fieldName", "Field is required")));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ValidationErrorResponse> responseEntity = exceptionHandler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getErrors().size());
        ValidationError error = responseEntity.getBody().getErrors().get(0);
        assertEquals("fieldName", error.getField());
        assertEquals("Field is required", error.getMessage());
    }

    @Test
    void handleScoringFailureExceptionTest() {
        ScoringDataDto scoringData = ScoringDataDto.builder()
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.UNEMPLOYED)
                        .employerINN("1234567890")
                        .salary(BigDecimal.valueOf(50000))
                        .employmentPosition(EmploymentPosition.MID_MANAGER)
                        .workExperienceTotal(24)
                        .workExperienceCurrent(10)
                        .build())
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(2000, 11, 21))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.of(2014, 11, 21))
                .passportIssueBranch("Moscow")
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(1)
                .accountNumber("1234567890")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

        ScoringFailureException exception = new ScoringFailureException("Ошибка скоринга: " + scoringData.toString());
        ResponseEntity<String> responseEntity = exceptionHandler.handleScoringFailureException(exception);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Ошибка скоринга"));
        assertTrue(responseEntity.getBody().contains(scoringData.toString()));
    }

}
