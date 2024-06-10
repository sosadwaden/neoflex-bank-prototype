package com.sosadwaden.calculator.service.impl;

import com.sosadwaden.calculator.dto.ScoringDataDto;
import com.sosadwaden.calculator.enums.EmploymentStatus;
import com.sosadwaden.calculator.enums.Gender;
import com.sosadwaden.calculator.enums.MaritalStatus;
import com.sosadwaden.calculator.enums.Position;
import com.sosadwaden.calculator.exception.ScoringFailureException;
import com.sosadwaden.calculator.service.ScoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ScoringServiceImpl implements ScoringService {

    private static final Logger logger = LoggerFactory.getLogger(ScoringServiceImpl.class);
    private final BigDecimal baseRate;

    @Autowired
    public ScoringServiceImpl(@Value("${loan.baseRate}") BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    @Override
    public BigDecimal scoring(ScoringDataDto request) {
        logger.info("Начало скоринга для запроса: {}", request);

        BigDecimal adjustedRate = calculateAdjustedRate(request);
        BigDecimal resultRate = adjustedRate;

        EmploymentStatus employmentStatus = request.getEmployment().getEmploymentStatus();

        if (employmentStatus == EmploymentStatus.UNEMPLOYED) {
            throw new ScoringFailureException("Не даём кредит безработным");
        } else if (employmentStatus == EmploymentStatus.SELF_EMPLOYED) {
            resultRate = resultRate.add(new BigDecimal("0.01"));
        } else if (employmentStatus == EmploymentStatus.BUSINESS_OWNER) {
            resultRate = resultRate.add(new BigDecimal("0.02"));
        }

        Position position = request.getEmployment().getPosition();

        if (position == Position.MIDDLE_MANAGER) {
            resultRate = resultRate.subtract(new BigDecimal("0.02"));
        } else if (position == Position.SENIOR_MANAGER) {
            resultRate = resultRate.subtract(new BigDecimal("0.03"));
        }

        BigDecimal salary = request.getEmployment().getSalary();
        BigDecimal salary25months = salary.multiply(new BigDecimal("25"));

        if (request.getAmount().compareTo(salary25months) > 0) {
            throw new ScoringFailureException("Сумма кредита не должна превышать 25 ваших зарплат");
        }

        MaritalStatus maritalStatus = request.getMaritalStatus();

        if (maritalStatus == MaritalStatus.MARRIED) {
            resultRate = resultRate.subtract(new BigDecimal("0.03"));
        } else if (maritalStatus == MaritalStatus.DIVORCED) {
            resultRate = resultRate.add(new BigDecimal("0.01"));
        }

        LocalDate birthdate = request.getBirthdate();
        int age = Period.between(birthdate, LocalDate.now()).getYears();

        if (age < 20 || age > 65) {
            throw new ScoringFailureException("Чтобы получить кредит ваш возраст должен быть от 20 до 65 лет");
        }

        Gender gender = request.getGender();

        if ((gender == Gender.FEMALE && (age >= 32 && age <= 60)) ||
            (gender == Gender.MALE && (age >= 30 && age <= 55))) {
            resultRate = resultRate.subtract(new BigDecimal("0.03"));
        }

        Integer workExperienceCurrent = request.getEmployment().getWorkExperienceCurrent();
        Integer workExperienceTotal = request.getEmployment().getWorkExperienceTotal();

        if (workExperienceTotal < 6) {
            throw new ScoringFailureException("Недостаточно опыта работы");
        }

        if (workExperienceCurrent < 3) {
            throw new ScoringFailureException("Недостаточно опыта работы на текущем месте");
        }

        logger.info("Скоринг успешно завершён для запроса: {}", request);
        logger.info("Результирующая ставка: {}", resultRate);

        return resultRate;
    }

    private BigDecimal calculateAdjustedRate(ScoringDataDto request) {
        Boolean isInsuranceEnabled = request.getIsInsuranceEnabled();
        Boolean isSalaryClient = request.getIsSalaryClient();
        BigDecimal adjustedRate = baseRate;

        if (!isInsuranceEnabled && !isSalaryClient) {
            adjustedRate = adjustedRate.add(new BigDecimal("0.05"));
        } else if (!isInsuranceEnabled) {
            adjustedRate = adjustedRate.add(new BigDecimal("0.03"));
        } else if (!isSalaryClient) {
            adjustedRate = adjustedRate.add(new BigDecimal("0.02"));
        }

        return adjustedRate;
    }

}
