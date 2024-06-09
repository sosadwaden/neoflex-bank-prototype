package com.sosadwaden.calculator.service.impl;

import com.sosadwaden.calculator.dto.*;
import com.sosadwaden.calculator.service.CalculatorService;
import com.sosadwaden.calculator.service.ScoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorServiceImpl.class);
    private final BigDecimal baseRate;
    private final ScoringService scoringService;

    @Autowired
    public CalculatorServiceImpl(@Value("${loan.baseRate}") BigDecimal baseRate, ScoringService scoringService) {
        this.baseRate = baseRate;
        this.scoringService = scoringService;
    }

    @Override
    public List<LoanOfferDto> generateOffers(LoanStatementRequestDto request) {
        logger.debug("Начало генерации List<LoanOfferDto> по запросу: {}", request);

        LoanOfferDto offer1 = createOffer(request, false, false);
        LoanOfferDto offer2 = createOffer(request, true, false);
        LoanOfferDto offer3 = createOffer(request, false, true);
        LoanOfferDto offer4 = createOffer(request, true, true);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(offer1);
        loanOfferDtoList.add(offer2);
        loanOfferDtoList.add(offer3);
        loanOfferDtoList.add(offer4);

        logger.debug("Сгенерированный List<LoanOfferDto>: {}", loanOfferDtoList);
        logger.info("Завершена генерация List<LoanOfferDto> по запросу: {}", request);

        return loanOfferDtoList;
    }

    @Override
    public CreditDto generateCreditDto(ScoringDataDto request) {
        logger.debug("Начало генерации CreditDto по запросу: {}", request);

        BigDecimal rate = scoringService.scoring(request);
        BigDecimal amount = request.getAmount();
        Integer term = request.getTerm();

        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, rate, term);
        BigDecimal psk = calculateTotalCost(monthlyPayment, term);
        List<PaymentScheduleElementDto> paymentSchedule = generateSchedule(amount, rate, term, monthlyPayment);

        CreditDto creditDto = CreditDto.builder()
                .amount(amount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(request.getIsInsuranceEnabled())
                .isSalaryClient(request.getIsSalaryClient())
                .paymentSchedule(paymentSchedule)
                .build();

        logger.debug("Сгенерированный CreditDto: {}", creditDto);
        logger.info("Завершена генерация CreditDto по запросу: {}", request);

        return creditDto;
    }

    /**
     * Используемая формула: P • ((r • (1 + r)^n) / ((1 + r)^n - 1))
     * P - сумма кредита
     * r - месячная процентная ставка
     * n - общее количество платежей
     * @param amount
     * @param rate
     * @param term
     * @return
     */
    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal power = BigDecimal.ONE.add(monthlyRate).pow(term);
        BigDecimal monthlyPayment =  amount.multiply(monthlyRate.multiply(power))
                .divide(power.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);

        logger.debug("Полученный monthlyPayment: {}", monthlyPayment);

        return monthlyPayment;
    }

    /**
     * Используемая формула: E • n
     * E - ежемесячный платёж
     * n - общее количество платежей
     * @param monthlyPayment
     * @param term
     * @return
     */
    private BigDecimal calculateTotalCost(BigDecimal monthlyPayment, Integer term) {
        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(term));
        logger.debug("Полученный psk: {}", psk);

        return psk;
    }

    private LoanOfferDto createOffer(LoanStatementRequestDto request, boolean isInsuranceEnabled, boolean isSalaryClient) {
        BigDecimal rate;

        if (isInsuranceEnabled && isSalaryClient) {
            rate = baseRate;
        } else if (isSalaryClient) {
            rate = baseRate.add(BigDecimal.valueOf(0.02));
        } else if (isInsuranceEnabled) {
            rate = baseRate.add(BigDecimal.valueOf(0.03));
        } else {
            rate = baseRate.add(BigDecimal.valueOf(0.05));
        }

        BigDecimal monthlyPayment = calculateMonthlyPayment(request.getAmount(), rate, request.getTerm());
        BigDecimal totalAmount = calculateTotalCost(monthlyPayment, request.getTerm());

        LoanOfferDto offer = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(request.getAmount())
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();

        return offer;
    }

    private List<PaymentScheduleElementDto> generateSchedule(BigDecimal amount, BigDecimal rate, Integer term, BigDecimal monthlyPayment) {
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

        BigDecimal remainingDebt = amount;
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        for (int i = 1; i <= term; i++) {
            BigDecimal interestPayment = remainingDebt.multiply(monthlyRate);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
            remainingDebt = remainingDebt.subtract(debtPayment);

            PaymentScheduleElementDto element = new PaymentScheduleElementDto();
            element.setNumber(i);
            element.setDate(LocalDate.now().plusMonths(i));
            element.setTotalPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
            element.setInterestPayment(interestPayment.setScale(2, RoundingMode.HALF_UP));
            element.setDebtPayment(debtPayment.setScale(2, RoundingMode.HALF_UP));
            element.setRemainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_UP));

            paymentSchedule.add(element);
        }

        logger.debug("Полученный List<PaymentScheduleElementDto>: {}", paymentSchedule);

        return paymentSchedule;
    }

}
