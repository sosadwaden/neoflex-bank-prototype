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
import com.sosadwaden.deal.mapperMapStruct.CreditMapper;
import com.sosadwaden.deal.repository.ClientRepository;
import com.sosadwaden.deal.repository.CreditRepository;
import com.sosadwaden.deal.repository.StatementRepository;
import com.sosadwaden.deal.service.ClientMapper;
import com.sosadwaden.deal.service.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;
    private final ClientMapper clientMapper;
    private final CalculatorClient calculatorClient;
    private final AppliedOfferMapper appliedOfferMapper = AppliedOfferMapper.INSTANCE;
    private final CreditMapper creditMapper = CreditMapper.INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);

    public DealServiceImpl(ClientRepository clientRepository,
                           StatementRepository statementRepository,
                           CreditRepository creditRepository, ClientMapper clientMapper,
                           CalculatorClient calculatorClient) {
        this.clientRepository = clientRepository;
        this.statementRepository = statementRepository;
        this.creditRepository = creditRepository;
        this.clientMapper = clientMapper;
        this.calculatorClient = calculatorClient;
    }

    @Override
    public List<LoanOfferDto> statement(LoanStatementRequestDto request) {
        logger.debug("Создание сущностей Client и Statement");

        Client client = clientMapper.LoanStatementRequestDtoToClient(request);
        clientRepository.save(client);

        Statement statement = Statement.builder()
                .client(client)
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

        statement.setStatus(ApplicationStatus.APPROVED);
        statement.getStatusHistory().add(StatusHistory.builder()
                .status("Текст") // TODO не знаю какой текст
                .time(LocalDateTime.now())
                .changeType(ChangeType.MANUAL) // TODO не знаю какой ChangeType
                .build());
        statement.setAppliedOffer(appliedOffer);

        statementRepository.save(statement);
    }

    @Override
    public void calculateByStatementId(FinishRegistrationRequestDto request,
                                       String statementId) {

        Statement statement = statementRepository.findById(UUID.fromString(statementId))
                .orElseThrow(() -> new StatementNotFoundException("Statement с таким id не существует"));

        Client client = statement.getClient();

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
        statement.setStatus(ApplicationStatus.CREDIT_ISSUED);

        statementRepository.save(statement);
        creditRepository.save(credit);
    }
}
