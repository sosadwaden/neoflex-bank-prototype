package com.sosadwaden.calculator.service;

import com.sosadwaden.calculator.dto.LoanOfferDto;
import com.sosadwaden.calculator.dto.LoanStatementRequestDto;
import com.sosadwaden.calculator.service.impl.CalculatorServiceImpl;
import com.sosadwaden.calculator.service.impl.ScoringServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

    @Mock
    private ScoringServiceImpl scoringService;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Задать значение baseRate через рефлексию
        injectBaseRate(calculatorService, new BigDecimal("0.05"));
    }

    private void injectBaseRate(CalculatorServiceImpl service, BigDecimal baseRate) {
        try {
            Field baseRateField = CalculatorServiceImpl.class.getDeclaredField("baseRate");
            baseRateField.setAccessible(true);
            baseRateField.set(service, baseRate);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generateOffersTest() {
        LoanStatementRequestDto request = createGoodLoanStatementRequest();

        List<LoanOfferDto> loanOfferDtoList = calculatorService.generateOffers(request);
        assertEquals(4, loanOfferDtoList.size());
        assertEquals(BigDecimal.valueOf(100_000), loanOfferDtoList.get(0).getRequestedAmount());
    }

    @Test
    void checkPrescoring() {
        assertThrows(MethodArgumentNotValidException.class, () -> {
            LoanStatementRequestDto request = createBadLoanStatementRequest();
            calculatorService.generateOffers(request);
        });
    }

    private LoanStatementRequestDto createGoodLoanStatementRequest() {
        return LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100_000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("testemail@gmail.com")
                .birthdate(LocalDate.of(2000, 10, 10))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();
    }

    private LoanStatementRequestDto createBadLoanStatementRequest() {
        return LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100_000))
                .term(12)
                .firstName("Кар")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("testemail@gmail.com")
                .birthdate(LocalDate.now())
                .passportSeries("Привет")
                .passportNumber("123456")
                .build();
    }
}
