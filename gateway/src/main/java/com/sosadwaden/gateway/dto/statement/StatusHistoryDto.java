package com.sosadwaden.gateway.dto.statement;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusHistoryDto {

    String status;

    LocalDateTime time;

    String changeType;
}
