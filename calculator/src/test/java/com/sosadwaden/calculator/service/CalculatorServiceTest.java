package com.sosadwaden.calculator.service;

import com.sosadwaden.calculator.dto.*;
import com.sosadwaden.calculator.enums.EmploymentStatus;
import com.sosadwaden.calculator.enums.Gender;
import com.sosadwaden.calculator.enums.MaritalStatus;
import com.sosadwaden.calculator.enums.Position;
import com.sosadwaden.calculator.service.impl.CalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalculatorServiceTest {

    private CalculatorServiceImpl calculatorService;

    @Mock
    private ScoringService scoringService;

    @Value("${loan.baseRate}")
    private BigDecimal baseRate;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        calculatorService = new CalculatorServiceImpl(baseRate, scoringService);
    }

    @Test
    void generateOffersTest() throws NoSuchFieldException, IllegalAccessException {
        Field baseRateField = CalculatorServiceImpl.class.getDeclaredField("baseRate");
        baseRateField.setAccessible(true);
        BigDecimal baseRateValue = (BigDecimal) baseRateField.get(calculatorService);
        assertEquals(baseRate, baseRateValue);

        LoanStatementRequestDto request = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("myemail123@gmail.com")
                .birthdate(LocalDate.of(2000, 10, 10))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();

        List<LoanOfferDto> offers = calculatorService.generateOffers(request);

        assertNotNull(offers);
        assertEquals(4, offers.size());

        LoanOfferDto offer1 = offers.get(0);
        assertEquals(request.getAmount(), offer1.getRequestedAmount());
        assertEquals(request.getTerm(), offer1.getTerm());
    }

    @Test
    void generateCreditDtoTest() {
        ScoringDataDto request = createScoringDataDtoForTest(MaritalStatus.MARRIED,
                EmploymentStatus.EMPLOYED, BigDecimal.valueOf(60000),
                Position.MIDDLE_MANAGER, true, true);

        when(scoringService.scoring(any(ScoringDataDto.class))).thenReturn(BigDecimal.valueOf(0.15));

        CreditDto creditDto = calculatorService.generateCreditDto(request);

        assertNotNull(creditDto);
        assertNotNull(creditDto.getPaymentSchedule());
        assertEquals(request.getAmount(), creditDto.getAmount());
        assertEquals(request.getTerm(), creditDto.getTerm());
    }

    private ScoringDataDto createScoringDataDtoForTest(MaritalStatus maritalStatus,
                                                       EmploymentStatus employmentStatus,
                                                       BigDecimal salary,
                                                       Position position,
                                                       Boolean isInsuranceEnabled,
                                                       Boolean isSalaryClient) {
        return ScoringDataDto.builder()
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
                .maritalStatus(maritalStatus)
                .dependentAmount(0)
                .employment(EmploymentDto.builder()
                        .employmentStatus(employmentStatus)
                        .employerINN("1234567890")
                        .salary(salary)
                        .position(position)
                        .workExperienceTotal(24)
                        .workExperienceCurrent(24)
                        .build())
                .accountNumber("1234")
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }
}
