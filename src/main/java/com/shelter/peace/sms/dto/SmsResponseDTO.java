package com.shelter.peace.sms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsResponseDTO {
    String requestId;
    LocalDateTime requestTime;
    String statusCode;
    String statusName;
}
