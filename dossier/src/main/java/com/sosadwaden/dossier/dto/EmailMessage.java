package com.sosadwaden.dossier.dto;

import com.sosadwaden.dossier.enums.Topic;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailMessage {

    String address;
    Topic topic;
    Long statementId;
}