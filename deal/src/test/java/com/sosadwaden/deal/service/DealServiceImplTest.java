package com.sosadwaden.deal.service;

import com.sosadwaden.deal.client.CalculatorClient;
import com.sosadwaden.deal.dto.*;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.entity.Credit;
import com.sosadwaden.deal.entity.Statement;
import com.sosadwaden.deal.entity.enums.*;
import com.sosadwaden.deal.entity.jsonb_entity.AppliedOffer;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import com.sosadwaden.deal.entity.jsonb_entity.Passport;
import com.sosadwaden.deal.exception.StatementNotFoundException;
import com.sosadwaden.deal.mapperMapStruct.AppliedOfferMapper;
import com.sosadwaden.deal.mapperMapStruct.ClientMapper;
import com.sosadwaden.deal.mapperMapStruct.CreditMapper;
import com.sosadwaden.deal.repository.ClientRepository;
import com.sosadwaden.deal.repository.CreditRepository;
import com.sosadwaden.deal.repository.StatementRepository;
import com.sosadwaden.deal.service.impl.DealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class DealServiceImplTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private AppliedOfferMapper appliedOfferMapper;

    @Mock
    private CalculatorClient calculatorClient;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CreditMapper creditMapper;

    @InjectMocks
    private DealServiceImpl dealService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void statementTest() {
        LoanStatementRequestDto request = createLoanStatementRequest();
        Client client = createClient();
        Statement statement = createStatement();
        List<LoanOfferDto> responseBody = createLoanOfferDtoList();

        when(clientMapper.loanStatementRequestDtoToClient(request)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(statementRepository.save(any(Statement.class))).thenReturn(statement);
        when(calculatorClient.sendPostRequestToCalculatorOffers(request)).thenReturn(responseBody);

        List<LoanOfferDto> result = dealService.statement(request);

        verify(clientMapper, times(1)).loanStatementRequestDtoToClient(request);
        verify(clientRepository, times(1)).save(client);
        verify(statementRepository, times(1)).save(any(Statement.class));
        verify(calculatorClient, times(1)).sendPostRequestToCalculatorOffers(request);

        assert result != null;
        assert !result.isEmpty();
        for (LoanOfferDto offer : result) {
            assert offer.getStatementId() != null;
            assert offer.getStatementId().equals(statement.getStatementId());
        }
    }

    @Test
    public void offerSelectTest() {
        LoanOfferDto request = createLoanOfferDto();
        Statement statement = createStatement();
        AppliedOffer appliedOffer = createAppliedOffer();

        when(statementRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(statement));
        when(appliedOfferMapper.loanOfferDtoToAppliedOffer(request)).thenReturn(appliedOffer);

        dealService.offerSelect(request);

        verify(statementRepository, times(1)).findById(any(UUID.class));
        verify(appliedOfferMapper, times(1)).loanOfferDtoToAppliedOffer(request);
        verify(statementRepository, times(1)).save(statement);

        assert statement.getStatus() == ApplicationStatus.APPROVED;
        assert statement.getAppliedOffer() == appliedOffer;
        assert statement.getStatusHistory().size() == 1;
        assert statement.getStatusHistory().get(0).getStatus().equals("Текст");
        assert statement.getStatusHistory().get(0).getChangeType() == ChangeType.MANUAL;
        assert statement.getStatusHistory().get(0).getTime() != null;
    }

    @Test
    public void testCalculateByStatementId_success() {
        // Prepare test data
        FinishRegistrationRequestDto request = createFinishRegistrationRequestDto();
        String statementId = UUID.randomUUID().toString();
        Statement statement = createStatement();
        Client client = createClient();
        AppliedOffer appliedOffer = createAppliedOffer();
        statement.setClient(client);
        statement.setAppliedOffer(appliedOffer);

        EmploymentDto employmentDto = request.getEmployment();

        Employment employment = Employment.builder()
                .status(employmentDto.getEmploymentStatus())
                .employerINN(employmentDto.getEmployerINN())
                .salary(employmentDto.getSalary())
                .position(employmentDto.getPosition())
                .workExperienceTotal(employmentDto.getWorkExperienceTotal())
                .workExperienceCurrent(employmentDto.getWorkExperienceCurrent())
                .build();

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(appliedOffer.getRequestedAmount())
                .term(appliedOffer.getTerm())
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
                .isInsuranceEnabled(appliedOffer.getIsInsuranceEnabled())
                .isSalaryClient(appliedOffer.getIsSalaryClient())
                .build();

        CreditDto creditDto = createCreditDto();

        when(statementRepository.findById(any(UUID.class))).thenReturn(Optional.of(statement));
        when(calculatorClient.sendPostRequestToCalculatorCals(any(ScoringDataDto.class))).thenReturn(creditDto);
        when(creditMapper.creditDtoToCredit(any(CreditDto.class))).thenReturn(createCredit());

        dealService.calculateByStatementId(request, statementId);

        verify(clientRepository).save(any(Client.class));
        verify(creditRepository).save(any(Credit.class));
        verify(statementRepository).save(any(Statement.class));
    }

    @Test
    public void testCalculateByStatementId_statementNotFound() {
        FinishRegistrationRequestDto request = createFinishRegistrationRequestDto();
        String statementId = UUID.randomUUID().toString();

        when(statementRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());

        assertThrows(StatementNotFoundException.class, () -> {
            dealService.calculateByStatementId(request, statementId);
        });
    }

    private LoanStatementRequestDto createLoanStatementRequest() {
        return LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();
    }

    private Client createClient() {
        return Client.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportId(Passport.builder()
                        .series("1234")
                        .number("123456")
                        .build())
                .build();
    }

    private Statement createStatement() {
        return Statement.builder()
                .client(createClient())
                .status(ApplicationStatus.PREAPPROVAL)
                .statementId(UUID.randomUUID())
                .build();
    }

    private List<LoanOfferDto> createLoanOfferDtoList() {
        List<LoanOfferDto> offers = new ArrayList<>();
        LoanOfferDto offer1 = LoanOfferDto.builder()
                .requestedAmount(BigDecimal.valueOf(100000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(4578.43))
                .rate(BigDecimal.valueOf(0.03))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        LoanOfferDto offer2 = LoanOfferDto.builder()
                .requestedAmount(BigDecimal.valueOf(150000))
                .term(24)
                .monthlyPayment(BigDecimal.valueOf(6500.75))
                .rate(BigDecimal.valueOf(0.035))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();

        offers.add(offer1);
        offers.add(offer2);

        return offers;
    }

    private LoanOfferDto createLoanOfferDto() {
        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(100000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(4578.43))
                .rate(BigDecimal.valueOf(0.03))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }

    private AppliedOffer createAppliedOffer() {
        return AppliedOffer.builder()
                .requestedAmount(BigDecimal.valueOf(100000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(4578.43))
                .rate(BigDecimal.valueOf(0.03))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }

    private FinishRegistrationRequestDto createFinishRegistrationRequestDto() {
        return FinishRegistrationRequestDto.builder()
                .employment(createEmploymentDto())
                .passportIssueDate(LocalDate.now())
                .passportIssueBranch("Branch")
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(1)
                .accountNumber("1234567890")
                .build();
    }

    private EmploymentDto createEmploymentDto() {
        return EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("1234567890")
                .salary(BigDecimal.valueOf(50000))
                .position(EmploymentPosition.WORKER)
                .workExperienceTotal(5)
                .workExperienceCurrent(3)
                .build();
    }

    private CreditDto createCreditDto() {
        return CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .monthlyPayment(BigDecimal.valueOf(5000))
                .rate(BigDecimal.valueOf(0.03))
                .build();
    }

    private Credit createCredit() {
        return Credit.builder()
                .creditId(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100000))
                .monthlyPayment(BigDecimal.valueOf(5000))
                .rate(BigDecimal.valueOf(0.03))
                .creditStatus(CreditStatus.CALCULATED)
                .build();
    }
}
