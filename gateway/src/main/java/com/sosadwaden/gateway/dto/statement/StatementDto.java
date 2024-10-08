package com.sosadwaden.gateway.dto.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO для представления заявки на кредит")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatementDto {

    UUID statementId;

    ClientDto client;

    CreditDto credit;

    String status;

    LocalDateTime creationDate;

    AppliedOfferDto appliedOffer;

    LocalDateTime signDate;

    String sesCode;

    List<StatusHistoryDto> statusHistory;
}
