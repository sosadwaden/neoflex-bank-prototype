package com.sosadwaden.deal.service.impl;

import com.sosadwaden.deal.client.CalculatorClient;
import com.sosadwaden.deal.dto.*;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.entity.Credit;
import com.sosadwaden.deal.entity.Statement;
import com.sosadwaden.deal.entity.enums.ApplicationStatus;
import com.sosadwaden.deal.entity.enums.ChangeType;
import com.sosadwaden.deal.entity.enums.CreditStatus;
import com.sosadwaden.deal.entity.jsonb_entity.AppliedOffer;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import com.sosadwaden.deal.entity.jsonb_entity.StatusHistory;
import com.sosadwaden.deal.exception.StatementNotFoundException;
import com.sosadwaden.deal.mapperMapStruct.AppliedOfferMapper;
import com.sosadwaden.deal.mapperMapStruct.ClientMapper;
import com.sosadwaden.deal.mapperMapStruct.CreditMapper;
import com.sosadwaden.deal.repository.ClientRepository;
import com.sosadwaden.deal.repository.CreditRepository;
import com.sosadwaden.deal.repository.StatementRepository;
import com.sosadwaden.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;
    private final CalculatorClient calculatorClient;
    private final ClientMapper clientMapper;
    private final AppliedOfferMapper appliedOfferMapper;
    private final CreditMapper creditMapper;
    private static final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);

    @Override
    public List<LoanOfferDto> statement(LoanStatementRequestDto request) {
        logger.debug("Создание сущностей Client и Statement");

        Client client = clientMapper.loanStatementRequestDtoToClient(request);
        clientRepository.save(client);

        Statement statement = Statement.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .build();

        statement = statementRepository.save(statement);

        List<LoanOfferDto> responseBody = calculatorClient.sendPostRequestToCalculatorOffers(request);

        UUID statementId = statement.getStatementId();

        responseBody.forEach(offer -> offer.setStatementId(statementId));

        return responseBody;
    }

    @Override
    public void offerSelect(LoanOfferDto request) {
        Statement statement = statementRepository.findById(request.getStatementId())
                .orElseThrow(() -> new StatementNotFoundException("Statement с таким id не существует"));

        logger.debug("Было выбрано кредитное предложение: {}", request);

        AppliedOffer appliedOffer = appliedOfferMapper.loanOfferDtoToAppliedOffer(request);

        statement.getStatusHistory().add(StatusHistory.builder()
                .status(statement.getStatus())
                .time(statement.getCreationDate())
                .changeType(ChangeType.MANUAL)
                .build());

        statement.setAppliedOffer(appliedOffer);
        statement.setStatus(ApplicationStatus.APPROVED);

        statementRepository.save(statement);
    }

    @Override
    public void calculateByStatementId(FinishRegistrationRequestDto request,
                                       String statementId) {

        Statement statement = statementRepository.findById(UUID.fromString(statementId))
                .orElseThrow(() -> new StatementNotFoundException("Statement с таким id не существует"));

        Client client = statement.getClient();
        client.setGender(request.getGender());
        client.setMaritalStatus(request.getMaritalStatus());
        client.setDependentAmount(request.getDependentAmount());
        client.setAccountNumber(request.getAccountNumber());

        EmploymentDto employmentDto = request.getEmployment();

        Employment employment = Employment.builder()
                .status(employmentDto.getEmploymentStatus())
                .employerINN(employmentDto.getEmployerINN())
                .salary(employmentDto.getSalary())
                .position(employmentDto.getPosition())
                .workExperienceTotal(employmentDto.getWorkExperienceTotal())
                .workExperienceCurrent(employmentDto.getWorkExperienceCurrent())
                .build();

        client.setEmploymentId(employment);
        clientRepository.save(client);

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(statement.getAppliedOffer().getRequestedAmount())
                .term(statement.getAppliedOffer().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(client.getGender())
                .birthdate(client.getBirthdate())
                .passportSeries(client.getPassportId().getSeries())
                .passportNumber(client.getPassportId().getNumber())
                .passportIssueDate(request.getPassportIssueDate())
                .passportIssueBranch(request.getPassportIssueBranch())
                .maritalStatus(request.getMaritalStatus())
                .dependentAmount(request.getDependentAmount())
                .employment(employmentDto)
                .accountNumber(request.getAccountNumber())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .build();

        CreditDto creditDto = calculatorClient.sendPostRequestToCalculatorCals(scoringDataDto);

        Credit credit = creditMapper.creditDtoToCredit(creditDto);

        credit.setCreditStatus(CreditStatus.CALCULATED);
        credit.getStatements().add(statement);

        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.setCredit(credit);

        creditRepository.save(credit);
        statementRepository.save(statement);
    }
}
