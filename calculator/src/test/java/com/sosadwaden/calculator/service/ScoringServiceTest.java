package com.sosadwaden.calculator.service;

import com.sosadwaden.calculator.dto.EmploymentDto;
import com.sosadwaden.calculator.dto.ScoringDataDto;
import com.sosadwaden.calculator.enums.EmploymentStatus;
import com.sosadwaden.calculator.enums.Gender;
import com.sosadwaden.calculator.enums.MaritalStatus;
import com.sosadwaden.calculator.enums.Position;
import com.sosadwaden.calculator.exception.ScoringFailureException;
import com.sosadwaden.calculator.service.impl.ScoringServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class ScoringServiceTest {

    @Autowired
    private ScoringServiceImpl scoringService;

    @Test
    void scoringTest() {
        ScoringDataDto request = createScoringDataDtoForTest(MaritalStatus.MARRIED,
                EmploymentStatus.EMPLOYED, BigDecimal.valueOf(60000), Position.MIDDLE_MANAGER,
                true, true);

        BigDecimal resultRate = scoringService.scoring(request);
        BigDecimal expectedRate = new BigDecimal("0.10");

        assertEquals(expectedRate, resultRate);
    }

    @Test
    void unemployedScoringTest() {
        ScoringDataDto request = createScoringDataDtoForTest(MaritalStatus.MARRIED,
                EmploymentStatus.UNEMPLOYED, BigDecimal.valueOf(60000), Position.MIDDLE_MANAGER,
                true, true);

        assertThrows(ScoringFailureException.class, () -> scoringService.scoring(request));
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
