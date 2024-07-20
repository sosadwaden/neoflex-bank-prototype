package com.sosadwaden.deal.dto.statement;

import com.sosadwaden.deal.entity.jsonb_entity.AppliedOffer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatementDto {

    UUID statementId;

    ClientDto client;

    UUID credit;

    String status;

    LocalDateTime creationDate;

    AppliedOffer appliedOffer;

    LocalDateTime signDate;

    String sesCode;

    List<StatusHistoryDto> statusHistory;
}
